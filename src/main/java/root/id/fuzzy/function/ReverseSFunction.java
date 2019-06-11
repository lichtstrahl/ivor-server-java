package root.id.fuzzy.function;

public class ReverseSFunction implements RepresentationFunction, Symmetric, IntervalAware {
    private double a;
    private double b;
    private double center;

    public ReverseSFunction(double a, double b) {
        this.a = Math.max(a,b);
        this.b = Math.min(a,b);
        this.center = (a+b)/2;
    }

    @Override
    public Interval interval() {
        return new Interval(true, Double.NEGATIVE_INFINITY, a, false);
    }

    @Override
    public double calc(double x) {
        double s = 0.0;
        if (x >= a) {
            s = 0;
        } else if (x > center) {
            s = 2.0 * Math.pow(((x - a) / (b - a)), 2);
        } else if (x >= b) {
            s = 1.0 - (2.0 * Math.pow(((x - b) / (b - a)), 2));
        } else {
            s = 1.0;
        }
        return s;
    }

    @Override
    public double center() {
        return center;
    }

    public String toString() {
        return "ReverseS(x, " + a + ", " + b + ", " + center + ")";
    }
}
