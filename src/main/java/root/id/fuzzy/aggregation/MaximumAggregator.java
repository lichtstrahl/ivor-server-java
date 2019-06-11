package root.id.fuzzy.aggregation;

import root.id.fuzzy.aggregation.norm.Norm;

public class MaximumAggregator implements Norm {
    public static final MaximumAggregator INSTANCE = new MaximumAggregator();

    @Override
    public Type type() {
        return Type.T_CONORM;
    }

    @Override
    public Norm duality() {
        return MinimumAggregator.INSTANCE;
    }


    /**
     * @return Максимальное значение в массиве
     */
    @Override
    public double apply(double... values) {
        return minmax(values)[1];
    }
}
