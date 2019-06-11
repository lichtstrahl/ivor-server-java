package root.id.fuzzy.aggregation.norm;

public class AlgebraicSumNorm extends AbstractNorm {
    public static final AlgebraicSumNorm INSTANCE = new AlgebraicSumNorm();

    public AlgebraicSumNorm() {
        super(Type.T_CONORM, AlgebraicProductNorm.INSTANCE);
    }

    @Override
    public double apply(double... values) {
        double s = 1.0;

        for (double v : values)
            s *= (1.0-v);

        return 1.0-s;
    }

    public String toString() {
        return "SUM";
    }

}
