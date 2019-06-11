package root.id.fuzzy.aggregation.norm;

import root.id.fuzzy.aggregation.Aggregator;

public interface Norm extends Aggregator {
    enum Type {
        T_NORM("⊗"),
        T_CONORM("⊕"),
        UNKNOWN("?");

        private String eval;
        private Type(String eval) {
            this.eval = eval;
        }
        public String toString() {
            return eval;
        }
    }

    Type type();

    Norm duality();
}
