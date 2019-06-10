package root.id.fuzzy.defuzzify;

import root.id.fuzzy.Variable;
import root.id.fuzzy.function.RepresentationFunction;

public interface Defuzzificator {
    double apply(Variable var, RepresentationFunction function);
}
