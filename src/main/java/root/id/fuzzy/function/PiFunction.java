package root.id.fuzzy.function;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PiFunction implements RepresentationFunction, Symmetric {
    /**
     * a - левый край
     * r - центр
     * b - правый край
     * p - где принимает значение 0.5 слева
     * q - где принимает значение 0.5 справа
     */
    private double a,r,b,p,q;

    /**
     * @param center - Ось симметрии
     * @param r - разброс
     */
    public PiFunction(double center, double r) {
        this.a = center - r/2;
        this.r = center;
        this.b = center + r/2;
        this.p = (r+a)/2;
        this.q = (b+r)/2;
    }

    @Override
    public double calc(double x) {
        if (x <= a)
            return 0.0;
        else if (a < x && x <= p)
            return 2 * Math.pow((x-a) / (r-a), 2);
        else if (p < x && x <= r)
            return 1.0 - 2 * Math.pow((r - x) / (r - a), 2);
        else if (r < x && x <= q)
            return 1.0 - 2 * Math.pow((x - r)/(b - r), 2);
        else if (q < x && x <= b)
            return (2 * Math.pow((b - x) / (b - r), 2));
        else // (b < x)
            return 0.0;
    }

    @Override
    public double center() {
        return r;
    }

    @Override
    public String toString() {
        return String.format("Pi(%.2f, %.2f, %.2f)", a, r, b);
    }
}
