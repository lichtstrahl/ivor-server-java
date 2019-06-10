package root.id.fuzzy;

import lombok.AllArgsConstructor;
import lombok.Data;
import root.id.fuzzy.function.RepresentationFunction;

@AllArgsConstructor
@Data
public class Term {
    private String name;
    private RepresentationFunction function;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Term term = (Term) o;
        if (!name.equals(term.name)) return false;
        return true;
}

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
