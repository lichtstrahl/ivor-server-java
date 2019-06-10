package root.id.fuzzy.rules;

import lombok.Data;

@Data
public class IfThenRule {
    private final Rule rule;
    private final FuzzyRule then;

    public IfThenRule(Rule rule, FuzzyRule then) {
        this.rule = rule;
        this.then = then;
    }

    public String toString() {
        return "If " + rule + " Then " + then;
    }
}
