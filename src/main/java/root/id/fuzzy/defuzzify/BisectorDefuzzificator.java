package root.id.fuzzy.defuzzify;

import root.id.fuzzy.Variable;
import root.id.fuzzy.function.RepresentationFunction;

public class BisectorDefuzzificator extends IterativeDefuzzificator {
    @Override
    public double apply(Variable var, RepresentationFunction function) {
        double min = var.min();
        double max = var.max();
        int samples = getCountIter();
        double dx = (max - min) / samples;

        int left = 0, right = 0;
        double leftA = 0, rightA = 0;
        double xleft = min, xright = max;
        while (samples-- > 0) {
            if (leftA <= rightA) {
                xleft = min + (left + 0.5) * dx;
                leftA += function.calc(xleft);
                left++;
            }
            else {
                xright = max - (right + 0.5) * dx;
                rightA += function.calc(xright);
                right++;
            }
        }

        // apply bisector
        return (leftA * xright + rightA * xleft) / (leftA + rightA);
    }
}
