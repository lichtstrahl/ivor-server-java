package root.id.fuzzy.rules;

import lombok.Data;
import root.id.fuzzy.Term;
import root.id.fuzzy.Variable;
import root.id.fuzzy.input.InputInstanceAccessor;

@Data
public class FuzzyRule implements Rule {
    private final Variable variable;
    private final Term value;

    public FuzzyRule(Variable variable, Term value) {
        this.variable = variable;
        this.value = value;
    }

    @Override
    public double apply(InputInstanceAccessor accessor) {
        return accessor.valueOf(variable, value);
    }

    public String toString() {
        return variable + " is " + value;
    }
}
