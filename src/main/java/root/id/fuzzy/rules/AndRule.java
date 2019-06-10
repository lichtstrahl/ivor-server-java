package root.id.fuzzy.rules;

import root.id.fuzzy.input.InputInstanceAccessor;

import java.util.Arrays;

public class AndRule implements Rule{
    private Rule[] inners;

    public AndRule(Rule[] inners) {
        this.inners = inners;
    }

    @Override
    public double apply(InputInstanceAccessor accessor) {
        double[] res = new double[inners.length];
        for (int i = 0; i < inners.length; i++)
            res[i] = inners[i].apply(accessor);
        return accessor.andFunction().apply(res);
    }

    public String toString() {
        return "and(" + Arrays.asList(inners) + ")";
    }
}
