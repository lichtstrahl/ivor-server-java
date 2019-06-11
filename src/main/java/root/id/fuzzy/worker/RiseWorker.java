package root.id.fuzzy.worker;

import root.id.fuzzy.Term;
import root.id.fuzzy.Variable;
import root.id.fuzzy.aggregation.MaximumAggregator;
import root.id.fuzzy.aggregation.MinimumAggregator;
import root.id.fuzzy.controller.ControllerBuilder;
import root.id.fuzzy.controller.FuzzyController;
import root.id.fuzzy.defuzzify.BisectorDefuzzificator;
import root.id.fuzzy.function.PiFunction;
import root.id.fuzzy.function.ReverseSFunction;
import root.id.fuzzy.function.SFunction;
import root.id.fuzzy.input.InputInstance;

/**
 * Обработчик повышений. Выводит с помощью нечеткого вывода на сколько следует повысить коэффициент
 *
 */
public class RiseWorker {
    private static final double LOW_POWER = 0.5;
    private static final double MIDDLE_POWER = 2;
    private static final double HIGH_POWER = 5;
    private static final double MINIMAL_DELTA_POWER = 0.1;
    private static final double MIDDLE_DELTA_POWER = 0.3;
    private static final double EXTRA_DELTA_POWER = 0.5;

    private static final double LOW_CORRECT = 0.5;
    private static final double MIDDLE_CORRECT = 2;
    private static final double HIGH_CORRECT = 5;
    private static final double MINIMAL_DELTA_CORRECT = 0.1;
    private static final double MIDDLE_DELTA_CORRECT = 0.3;
    private static final double EXTRA_DELTA_CORRECT = 0.5;

    /**
     * @param currentWeight Теущее значение значимости
     * @return Величина, на которую следует изменить текущую значимость
     */
    public static double changePowerWeight(double currentWeight) {
        Term A1 = new Term("высокий", new SFunction(1, HIGH_POWER));
        Term A2 = new Term("нормальый", new PiFunction(MIDDLE_POWER, 3));
        Term A3 = new Term("низкий", new ReverseSFunction(4, LOW_POWER));
        Variable w1 = Variable.input("вес power", A1, A2, A3).start(0).end(10);

        Term B1 = new Term("большая", new SFunction(0.2, EXTRA_DELTA_POWER));
        Term B2 = new Term("средняя", new PiFunction(MIDDLE_DELTA_POWER, 0.3));
        Term B3 = new Term("маленькая", new ReverseSFunction(0.5, MINIMAL_DELTA_POWER));
        Variable delta = Variable.output("прибавка", B1, B2, B3).start(0).end(1);


        FuzzyController controller = ControllerBuilder.newBuilder()
                .when().var(w1).is(A1).then().var(delta).is(B3)
                .when().var(w1).is(A2).then().var(delta).is(B2)
                .when().var(w1).is(A3).then().var(delta).is(B1)
                .activationFunction(MinimumAggregator.INSTANCE)
                .accumulationFunction(MaximumAggregator.INSTANCE)
                .defuzzifier(BisectorDefuzzificator.create())
                .create();


        InputInstance input = new InputInstance().is(w1, currentWeight);
        return controller.apply(input).get(delta);
    }

    public static double changeCorrectWeight(double currentWeight) {
        Term A1 = new Term("высокий", new SFunction(1, HIGH_CORRECT));
        Term A2 = new Term("нормальый", new PiFunction(MIDDLE_CORRECT, 3));
        Term A3 = new Term("низкий", new ReverseSFunction(4, LOW_CORRECT));
        Variable w2 = Variable.input("вес power", A1, A2, A3).start(0).end(10);

        Term B1 = new Term("большая", new SFunction(0.2, EXTRA_DELTA_CORRECT));
        Term B2 = new Term("средняя", new PiFunction(MIDDLE_DELTA_CORRECT, 0.3));
        Term B3 = new Term("маленькая", new ReverseSFunction(0.5, MINIMAL_DELTA_CORRECT));
        Variable delta = Variable.output("прибавка", B1, B2, B3).start(0).end(1);


        FuzzyController controller = ControllerBuilder.newBuilder()
                .when().var(w2).is(A1).then().var(delta).is(B3)
                .when().var(w2).is(A2).then().var(delta).is(B2)
                .when().var(w2).is(A3).then().var(delta).is(B1)
                .activationFunction(MinimumAggregator.INSTANCE)
                .accumulationFunction(MaximumAggregator.INSTANCE)
                .defuzzifier(BisectorDefuzzificator.create())
                .create();


        InputInstance input = new InputInstance().is(w2, currentWeight);
        return controller.apply(input).get(delta);
    }
}
