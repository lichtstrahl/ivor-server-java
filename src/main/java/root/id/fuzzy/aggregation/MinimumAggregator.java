package root.id.fuzzy.aggregation;

public class MinimumAggregator implements Norm {
    public static final MinimumAggregator INSTANCE = new MinimumAggregator();

    @Override
    public Type type() {
        return Type.T_NORM;
    }

    @Override
    public Norm duality() {
        return MaximumAggregator.INSTANCE;
    }

    @Override
    public double apply(double... values) {
        return minmax(values)[0];
    }
}
