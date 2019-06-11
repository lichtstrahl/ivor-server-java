package root.id.fuzzy.function;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * Интервал
 */
@Data
@AllArgsConstructor
public class Interval {
    private boolean leftInclusive = true;
    private double min;
    private double max;
    private boolean rightInclusive = true;

    public static Interval create(double start, double end) {
        return new Interval(true, start, end, true);
    }
}
