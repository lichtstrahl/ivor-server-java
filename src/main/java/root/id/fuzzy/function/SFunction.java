package root.id.fuzzy.function;

import lombok.AllArgsConstructor;
import lombok.Data;
import root.id.fuzzy.Interval;

@Data
public class SFunction implements RepresentationFunction, Symmetric, IntervalAware {
    private double a;
    private double b;
    private double center;

    public SFunction(double a, double b) {
        this.a = Math.min(a,b);
        this.b = Math.max(a,b);
        this.center = this.a + ((this.b-this.a)/2.0);
    }

    @Override
    public Interval interval() {
        return new Interval(false, a, Double.POSITIVE_INFINITY, true);
    }

    @Override
    public double calc(double x) {
        if (x <= a) {
            return 0;
        } else if (x < center) {
            return 2.0 * Math.pow(((x - a) / (b - a)), 2);
        } else if (x <= b) {
            return 1.0 - (2.0 * Math.pow(((b - x) / (b - a)), 2));
        } else {
            return 1.0;
        }
    }

    @Override
    public double center() {
        return center;
    }

    public String toString() {
        return "S(x, " + a + ", " + b + ", " + center + ")";
    }
}
