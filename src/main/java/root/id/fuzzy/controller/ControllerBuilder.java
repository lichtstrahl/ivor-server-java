package root.id.fuzzy.controller;

import root.id.fuzzy.aggregation.Aggregator;
import root.id.fuzzy.aggregation.MaximumAggregator;
import root.id.fuzzy.aggregation.MinimumAggregator;
import root.id.fuzzy.defuzzify.BisectorDefuzzificator;
import root.id.fuzzy.defuzzify.Defuzzificator;
import root.id.fuzzy.rules.IfThenRule;
import root.id.fuzzy.rules.RuleBuilder;

import java.util.ArrayList;
import java.util.List;

public class ControllerBuilder {
    public static ControllerBuilder newBuilder() {
        return new ControllerBuilder();
    }

    private Aggregator tnorm = MinimumAggregator.INSTANCE;
    private Aggregator tconorm = MaximumAggregator.INSTANCE;
    private Aggregator activationNorm = MinimumAggregator.INSTANCE;
    private Aggregator accumulationTconorm = MaximumAggregator.INSTANCE;
    private Defuzzificator defuzzifier = new BisectorDefuzzificator();

    private List<IfThenRule> rules = new ArrayList();

    private ControllerBuilder() {
    }

    public List<IfThenRule> getRules() {
        return rules;
    }

    public ControllerBuilder andFunction(Aggregator aggregation) {
        this.tnorm = aggregation;
        return this;
    }

    public ControllerBuilder orFunction(Aggregator aggregation) {
        this.tconorm = aggregation;
        return this;
    }

    public ControllerBuilder activationFunction(Aggregator aggregation) {
        this.activationNorm = aggregation;
        return this;
    }

    public ControllerBuilder accumulationFunction(Aggregator aggregation) {
        this.accumulationTconorm = aggregation;
        return this;
    }

    public ControllerBuilder defuzzifier(Defuzzificator defuzzifier) {
        this.defuzzifier = defuzzifier;
        return this;
    }

    public RuleBuilder when() {
        return new RuleBuilder(this);
    }

    public ControllerBuilder addRule(IfThenRule rule) {
        this.rules.add(rule);
        return this;
    }

    public FuzzyController create() {
        return new FuzzyController(tnorm, tconorm,
                activationNorm,
                accumulationTconorm,
                defuzzifier,
                rules);
    }

}
