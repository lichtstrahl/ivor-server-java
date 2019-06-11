package root.id.fuzzy.aggregation.norm;

public class AlgebraicProductNorm extends AbstractNorm {
    public static final AlgebraicProductNorm INSTANCE = new AlgebraicProductNorm();

    public AlgebraicProductNorm() {
        super(Type.T_NORM, AlgebraicSumNorm.INSTANCE);
    }

    @Override
    public double apply(double... values) {
        if (values.length < 1)
            return 0.0;

        double r = values[0];
        for (int i = 1; i < values.length; i++)
            r *= values[i];
        return r;
    }

    public String toString() {
        return "PROD";
    }
}
