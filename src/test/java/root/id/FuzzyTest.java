package root.id;

import org.junit.Test;
import root.id.fuzzy.controller.ControllerBuilder;
import root.id.fuzzy.controller.FuzzyController;
import root.id.fuzzy.Term;
import root.id.fuzzy.Variable;
import root.id.fuzzy.aggregation.MaximumAggregator;
import root.id.fuzzy.aggregation.MinimumAggregator;
import root.id.fuzzy.defuzzify.BisectorDefuzzificator;
import root.id.fuzzy.function.*;
import root.id.fuzzy.input.InputInstance;

public class FuzzyTest {

    /**
     * Процент ошибок увеличился  отношение было w1<w2
     * Значит w1 нужно увеличить. На сколько?
     */
    @Test
    public void testUpW1() {
        Term A1 = new Term("высокий", new SFunction(1, 5));
        Term A2 = new Term("нормальый", new PiFunction(2, 3));
        Term A3 = new Term("низкий", new ReverseSFunction(4, 0.5));
        Variable weight1 = Variable.input("вес power", A1, A2, A3).start(0).end(10);

        Term B1 = new Term("большая", new SFunction(0.2, 0.5));
        Term B2 = new Term("средняя", new PiFunction(0.30, 0.3));
        Term B3 = new Term("маленькая", new ReverseSFunction(0.5, 0.1));
        Variable delta = Variable.output("прибавка", B1,B2,B3).start(0).end(1);


        FuzzyController controller = ControllerBuilder.newBuilder()
                .when().var(weight1).is(A1).then().var(delta).is(B3)
                .when().var(weight1).is(A2).then().var(delta).is(B2)
                .when().var(weight1).is(A3).then().var(delta).is(B1)
                .activationFunction(MinimumAggregator.INSTANCE)
                .accumulationFunction(MaximumAggregator.INSTANCE)
                .defuzzifier(BisectorDefuzzificator.create())
                .create();

        for (int i = 1; i <= 50; i++) {
            double w = 0.2*i;
            double a1 = A1.getFunction().calc(w);
            double a2 = A2.getFunction().calc(w);
            double a3 = A3.getFunction().calc(w);

            InputInstance input = new InputInstance().is(weight1, w);
            double d = controller.apply(input).get(delta);

            double b1 = B1.getFunction().calc(d);
            double b2 = B2.getFunction().calc(d);
            double b3 = B3.getFunction().calc(d);
//            System.out.println(String.format("По предпосылкам: %5.2f, %5.2f, %5.2f  (%5.2f)", a1, a2, a3, w));
//            System.out.println(String.format("Изменения по результатам: %5.2f, %5.2f, %5.2f  (%5.2f)", b1, b2, b3, d));
            System.out.println(String.format("Вес, прибавка: %5.2f %5.2f", w, d));
        }

    }


    @Test
    public void testIvController() {
        Term cold = new Term("cold", new TriangularFunction(10, 40, 70));
        Term ok = new Term("ok", new TriangularFunction(40, 70, 100));
        Term hot = new Term("hot", new TriangularFunction(70, 100, 130));

        Variable room = Variable.input("room", cold, ok, hot).start(40).end(100);


        Term low = new Term("low", new TriangularFunction(-50, 0, 50));
        Term medi = new Term("medium", new TriangularFunction(0, 50, 100));
        Term high = new Term("high", new TriangularFunction(50, 100, 150));

        Variable delta = Variable.output("delta", low, medi, high).start(-50).end(150);

        FuzzyController controller = ControllerBuilder.newBuilder()
                .when().var(room).is(cold).then().var(delta).is(high)
                .when().var(room).is(ok).then().var(delta).is(medi)
                .when().var(room).is(hot).then().var(delta).is(low)
                .defuzzifier(new BisectorDefuzzificator())
                .create();

        InputInstance input = new InputInstance().is(room, 60);
        System.out.println("Fuzzy: " + controller.applyFuzzy(input));
        System.out.println("Crisp: " + controller.apply(input));
    }
}
