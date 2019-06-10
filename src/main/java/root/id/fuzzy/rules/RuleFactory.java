package root.id.fuzzy.rules;

import root.id.fuzzy.Term;
import root.id.fuzzy.Variable;
import root.id.fuzzy.aggregation.Aggregator;

public class RuleFactory {

    public static Rule and(Rule ... rules) {
        return new AndRule(rules);
    }

    public static Rule or(Rule ... rules) {
        return new OrRule(rules);
    }

    public static Rule other(Aggregator aggregator, Rule ... rules) {
        return new OtherRule(aggregator, rules);
    }

    public static FuzzyRule is (Variable var, Term value) {
        return new FuzzyRule(var, value);
    }

    public static Rule is (Variable var, Hedge hedge, Term value) {
        return new HedgedRule(is(var, value), hedge);
    }
}
