package root.id.fuzzy;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Variable {
    private String name;
    private boolean input = true;
    private double start = 0.0;
    private double end = 1.0;
    private List<Term> terms = new LinkedList<>();

    /**
     * Создание входной переменной
     * @param name - Имя
     * @param terms - Значения
     */
    public static Variable input(String name, Term ... terms) {
        Variable var = new Variable();
        var.name = name;
        var.input = true;
        if (terms != null)
            var.terms = Arrays.asList(terms);
        return var;
    }

    public static Variable output(String name, Term ... terms) {
        Variable var = new Variable();
        var.name = name;
        var.input = false;
        if (terms != null)
            var.terms = Arrays.asList(terms);
        return var;
    }

    public boolean isInput() {
        return input;
    }

    public Variable start(double start) {
        this.start = start;
        return this;
    }

    public Variable end(double end) {
        this.end = end;
        return this;
    }

    public Variable terms(Term ... terms) {
        this.terms = Arrays.asList(terms);
        return this;
    }

    public double min() {
        return start;
    }

    public double max() {
        return end;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable = (Variable) o;
        if (input != variable.input) return false;
        if (!name.equals(variable.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (input ? 1 : 0);
        return result;
    }
}
