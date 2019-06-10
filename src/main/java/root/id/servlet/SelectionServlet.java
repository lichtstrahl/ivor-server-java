package root.id.servlet;

import root.id.fuzzy.ControllerBuilder;
import root.id.fuzzy.FuzzyController;
import root.id.fuzzy.Term;
import root.id.fuzzy.Variable;
import root.id.fuzzy.defuzzify.BisectorDefuzzificator;
import root.id.fuzzy.function.TriangularFunction;
import root.id.fuzzy.input.InputInstance;

import javax.servlet.annotation.WebServlet;
import java.util.Map;

@WebServlet(name="SelectionServlet", value="/api/select")
public class SelectionServlet extends BaseServlet {

    public static void main(String[] args) {
       testIvController();
    }

/*
    private static void testController() {
        // Набор входных термов
        Term cold = Term.term("cold", 10, 40, 70);
        Term ok = Term.term("ok", 40, 70, 100);
        Term hot = Term.term("hot", 70, 100, 130);

        // Входная нечеткая перменная, принимает значения cold, ok, hot
        Variable room = Variable.input("room", cold, ok, hot).start(40).end(100);

        // Набор выходных термов
        Term low = Term.term("low", -50, 0, 50);
        Term medi = Term.term("medium", 0, 50, 100);
        Term high = Term.term("high", 50, 100, 150);

        // Выходная нечеткая переменная
        Variable delta = Variable.output("delta", low, medi, high).start(-50).end(150);

        // Контроллер
        FLC controller = ControllerBuilder.newBuilder()
                .when().var(room).is(cold).then().var(delta).is(high)
                .when().var(room).is(ok).then().var(delta).is(medi)
                .when().var(room).is(hot).then().var(delta).is(low)
                .defuzzifier(new Bisector())
                .create();

        // Это фактическое, четкое значение входной переменной
        InputInstance instance = new InputInstance().is(room, 60);
        System.out.println("Fuzzy = " + controller.applyFuzzy(instance));
        System.out.println("Fuzzy = " + controller.apply(instance));

        Map<Variable, Double> result = controller.apply(instance);
    }
*/


    private static void testIvController() {
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
    /*
    private static void test1() {
        // Функции принадлежности
        FuzzyFunction function = new TriangularFunction(0.0, 1.5, 6.0);
        FuzzyFunction notFunction = new ZadehNegation(function);

        double mu_x = function.apply(1.0);
        double not_mu_x = notFunction.apply(1.0);

        // Агрегация
        Aggregation owa = new OWA(0.5, 0.2, 0.2, 0.1);
        double aggr = owa.apply(0.8, 0.2, 0.7, 0.1);
    }
    */
}
