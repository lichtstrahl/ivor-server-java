package root.id.fuzzy.input;

import root.id.fuzzy.Variable;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Последовательность ввода для контроллера
 */
public class InputInstance {
    private Map<Variable, Double> values = new LinkedHashMap<>();

    public static InputInstance wrap(Map<Variable, Double> map) {
        InputInstance instance = new InputInstance();
        instance.values = map;
        return instance;
    }

    public InputInstance is(Variable var, double value) {
        values.put(var, value);
        return this;
    }

    public Double get(Variable var) {
        return values.get(var);
    }
}
