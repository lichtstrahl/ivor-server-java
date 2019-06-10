package root.id.fuzzy.rules;

import root.id.fuzzy.input.InputInstanceAccessor;

import java.util.Arrays;

public class OrRule implements Rule {
    private Rule[] inners;

    public OrRule(Rule[] rules) {
        this.inners = rules;
    }

    @Override
    public double apply(InputInstanceAccessor accessor) {
        double[] res = new double[inners.length];

        for (int i = 0; i < inners.length; i++)
            res[i] = inners[i].apply(accessor);
        return accessor.orFunction().apply(res);
    }

    public String toString() {
        return "or(" + Arrays.asList(inners) + ")";
    }

}
