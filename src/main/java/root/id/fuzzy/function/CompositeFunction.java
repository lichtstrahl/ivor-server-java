package root.id.fuzzy.function;

import lombok.AllArgsConstructor;
import lombok.Data;
import root.id.fuzzy.aggregation.Aggregator;

import java.util.Arrays;

/**
 * Объединение нескольких функций. Для возможности "логического вывода"
 */
@Data
public class CompositeFunction implements RepresentationFunction {
    private final RepresentationFunction[] inner;
    private final Aggregator aggregator;

    public CompositeFunction(Aggregator aggregation, RepresentationFunction ... inners) {
        this.aggregator = aggregation;
        this.inner = inners;
    }

    @Override
    public double calc(double x) {
        // Находим значения
        double[] values = new double[inner.length];
        for (int i = 0; i < values.length; i++)
            values[i] = inner[i].calc(x);
        // Соединение
        return aggregator.apply(values);
    }

    @Override
    public String toString() {
        return aggregator + "(" + Arrays.asList(inner) + ")";
    }

}
