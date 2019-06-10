package root.id.fuzzy.rules;

import root.id.fuzzy.aggregation.Aggregator;
import root.id.fuzzy.input.InputInstanceAccessor;

public class OtherRule implements Rule {
    private Aggregator aggregation;
    private Rule[] inners;

    public OtherRule(Aggregator aggregation, Rule[] inners) {
        this.aggregation = aggregation;
        this.inners = inners;
    }

    @Override
    public double apply(InputInstanceAccessor accessor) {
        double[] res = new double[inners.length];
        for (int i = 0; i < inners.length; i++)
            res[i] = inners[i].apply(accessor);
        return aggregation.apply(res);
    }
}
