package root.id.fuzzy.function;

import java.io.Serializable;

/**
 * Функция принадлежности
 */
public interface RepresentationFunction extends Serializable {
    double EPS = 1e-3;
    double calc(double x);
}
