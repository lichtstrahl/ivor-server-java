package root.id.fuzzy.function;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrapezoidalFunction implements RepresentationFunction, IntervalAware, Symmetric {
    /**
     * a - f(x)=0 left
     * b - f(x)=1 left
     * c - f(x)=1 right
     * d - f(x)=0 right
     */
    private double a,b,c,d;

    @Override
    public Interval interval() {
        return Interval.create(a,b);
    }


    @Override
    public double calc(double x) {
        if (x < a)
            return 0.0;
        else if (a <= x && x < b)
            return (x - a) / (b - a);
        else if (b <= x && x <= c)
            return 1.0;
        else if (c < x && x <= d)
            return (d - x) / (d - c);
        else
            return 0.0;
    }

    @Override
    public double center() {
        return b + ((c-b)/2.0);
    }

    public String toString() {
        return "i/¯\\i(" + a + ", " + b + ", " + c + ", " + d + ")";
    }
}
