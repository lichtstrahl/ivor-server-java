package root.id.fuzzy.controller;

import lombok.AllArgsConstructor;
import root.id.fuzzy.Term;
import root.id.fuzzy.Variable;
import root.id.fuzzy.aggregation.Aggregator;
import root.id.fuzzy.defuzzify.Defuzzificator;
import root.id.fuzzy.function.CompositeFunction;
import root.id.fuzzy.function.ConstantFunction;
import root.id.fuzzy.function.RepresentationFunction;
import root.id.fuzzy.input.InputInstance;
import root.id.fuzzy.input.InputInstanceAccessor;
import root.id.fuzzy.rules.IfThenRule;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class FuzzyController {
    private Aggregator tnorm;
    private Aggregator tconorm;
    private Aggregator activationNorm;
    private Aggregator accumulationTconorm;
    private Defuzzificator defuzzificator;
    private List<IfThenRule> rules;


    /**
     * Приведение к четкости
     */
    public Map<Variable, Double> apply(final InputInstance input) {
        Map<Variable, RepresentationFunction> fuzzyResult = applyFuzzy(input);

        Map<Variable, Double> res = new LinkedHashMap<>();

        for (Map.Entry<Variable, RepresentationFunction> entry : fuzzyResult.entrySet()) {
            double value = defuzzificator.apply(entry.getKey(), entry.getValue());
            res.put(entry.getKey(), value);
        }

        return res;
    }

    /**
     * Алгоритм нечеткого вывода
     */
    public Map<Variable, RepresentationFunction> applyFuzzy(final InputInstance input) {
        InputInstanceAccessor accessor = new InputInstanceAccessor() {
            @Override
            public Aggregator orFunction() {
                return FuzzyController.this.tconorm;
            }

            @Override
            public Aggregator andFunction() {
                return FuzzyController.this.tnorm;
            }

            @Override
            public double valueOf(Variable var, Term label) {
                Double v = input.get(var);
                if (v == null)
                    return 0.0;
                else
                    return label.getFunction().calc(v);
            }
        };

        Map<Variable, List<RepresentationFunction>> varOutput = new LinkedHashMap<>();

        // Цикл по каждому правилу
        for (IfThenRule rule : rules) {
            // Применяем правило (находим уровень отсечения)
            double amputation = rule.getRule().apply(accessor);

            if (amputation > 0.0) {
                // Использование арегации
                if (!varOutput.containsKey(rule.getThen().getVariable()))
                    varOutput.put(rule.getThen().getVariable(), new LinkedList<>());

                // Добавка граничных множеств в выход
                RepresentationFunction composite = new CompositeFunction(
                        activationNorm,
                        rule.getThen().getValue().getFunction(),
                        new ConstantFunction(amputation)
                );
                varOutput.get(rule.getThen().getVariable())
                        .add(composite);
            }
        }

        // Соединение выходов
        Map<Variable, RepresentationFunction> result = new LinkedHashMap<>();

        for (Map.Entry<Variable, List<RepresentationFunction>> entry : varOutput.entrySet()) {
            RepresentationFunction func = new CompositeFunction(
                    accumulationTconorm,
                    entry.getValue().toArray(new RepresentationFunction[entry.getValue().size()])
            );
            result.put(entry.getKey(), func);
        }

        return result;
    }
}
