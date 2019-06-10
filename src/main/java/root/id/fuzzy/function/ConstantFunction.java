package root.id.fuzzy.function;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConstantFunction implements RepresentationFunction {
    private double constant;

    @Override
    public double calc(double x) {
        return constant;
    }

    public String toString() {
        return String.valueOf(constant);
    }
}
