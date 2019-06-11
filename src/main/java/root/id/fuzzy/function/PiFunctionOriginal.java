package root.id.fuzzy.function;

public class PiFunctionOriginal implements RepresentationFunction, Symmetric {

    public final double a, r, b, m, p, q;
    // private double a, r, b, m, p, q;

    public PiFunctionOriginal(double center, double r, double m) {
        this.a = center-r/2;
        this.r = center;
        this.b = center+r/2;
        this.m = m;
        this.p = (center + a) / 2.0;
        this.q = (b + center) / 2.0;
    }

    @Override
    public double calc(double x) {
        if (x <= a)
            return 0.0;
        else if (a < x && x <= p)
            return Math.pow(2, m - 1) * Math.pow((x-a) / (r-a), m);
        else if (p < x && x <= r)
            return 1.0 - (Math.pow(2, m - 1) * Math.pow((r - x) / (r - a), m));
        else if (r < x && x <= q)
            return 1.0 - (Math.pow(2, m - 1) * Math.pow((x - r)/(b - r), m));
        else if (q < x && x <= b)
            return (Math.pow(2, m - 1) * Math.pow((b - x) / (b - r), m));
        else // (b < x)
            return 0.0;
    }

    @Override
    public String toString() {
        return String.format("Pi(%.2f, %.2f, %.2f)", a, r, b);
    }

    @Override
    public double center() {
        return r;
    }
}