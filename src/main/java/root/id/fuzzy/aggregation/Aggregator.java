package root.id.fuzzy.aggregation;

import java.io.Serializable;

public interface Aggregator extends Serializable {
    double apply(double ... values);

    /**
     * @return Максимальное и минимальное значение в массив
     */
    default double[] minmax(double[] values) {
        if (values == null || values.length == 0)
            return new double[] { Double.NaN, Double.NaN };
        double min = values[0], max = values[0];
        for (int i = 1; i < values.length; i++) {
            if (values[i] < min)
                min = values[i];
            if (values[i] > max)
                max = values[i];
        }
        return new double[]{ min, max };
    }
}
