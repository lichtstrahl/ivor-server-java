package root.id.fuzzy.function;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Треугольная функция принадлежности.
 * T-тип
 */
@Data
@AllArgsConstructor
public class TriangularFunction implements IntervalAware, Symmetric, RepresentationFunction {
    private double a;
    private double b;
    private double c;

    @Override
    public Interval interval() {
        return Interval.create(a,c);
    }

    @Override
    public double calc(double x) {
        if (a < x && x < b) {   // Если в первой половине
            return (x-a)/(b-a);
        } else if (Math.abs(x-b)<EPS) { // Если четко по середине
            return 1.0;
        } else if (b < x && x < c) {
            return (c-x)/(c-b);
        } else {    // Если не попадает в треугольник
            return 0.0;
        }
    }

    @Override
    public double center() {
        return b;
    }

    @Override
    public String toString() {
        return String.format("\u25b3(%f, %f, %f)", a, b, c);
    }
}
