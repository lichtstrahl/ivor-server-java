package root.id.fuzzy.input;

import root.id.fuzzy.Term;
import root.id.fuzzy.Variable;
import root.id.fuzzy.aggregation.Aggregator;

public interface InputInstanceAccessor {
    // Текущая нечеткая "или" функция
    Aggregator orFunction();
    //
    Aggregator andFunction();
    // Сопоставление значение ("temp", "low") = 0.2
    double valueOf(Variable var, Term label);
}
