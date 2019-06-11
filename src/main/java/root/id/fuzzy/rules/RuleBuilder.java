package root.id.fuzzy.rules;

import root.id.fuzzy.controller.ControllerBuilder;
import root.id.fuzzy.Term;
import root.id.fuzzy.Variable;

public class RuleBuilder {
    private enum PendingType {
        AND,
        OR;
    }

    private ControllerBuilder controllerBuilder;

    public RuleBuilder(ControllerBuilder controllerBuilder) {
        this.controllerBuilder = controllerBuilder;
    }

    public TermSelector var(Variable var) {
        return new TermSelector(null, var);
    }

    public class AfterRuleBuilder {

        private AfterRuleBuilder previous;
        private Rule expression;
        private PendingType type = null;

        public AfterRuleBuilder(AfterRuleBuilder previous, Rule expression) {
            this.previous = previous;
            this.expression = expression;
        }

        public VarSelector or() {
            this.type = PendingType.OR;
            return new VarSelector(this);
        }

        public VarSelector and() {
            this.type = PendingType.AND;
            return new VarSelector(this);
        }

        public ThenVarSelector then() {

            AfterRuleBuilder prev = previous;
            Rule current = expression;

            while (prev != null) {
                current = (prev.type == PendingType.AND) ?
                        RuleFactory.and(current, prev.expression) :
                        RuleFactory.or(current, prev.expression);

                prev = prev.previous;
            }

            return new ThenVarSelector(current);
        }
    }

    public class VarSelector {

        private AfterRuleBuilder previous;

        public VarSelector(AfterRuleBuilder previous) {
            this.previous = previous;
        }

        public TermSelector var(Variable var) {
            return new TermSelector(previous, var);
        }
    }

    public class TermSelector {

        private Variable var;
        private AfterRuleBuilder previous;

        public TermSelector(AfterRuleBuilder previous, Variable var) {
            this.previous = previous;
            this.var = var;
        }

        public AfterRuleBuilder isNot(Term term) {
            return new AfterRuleBuilder(previous, RuleFactory.is(var, Hedge.NOT, term));
        }

        public AfterRuleBuilder is(Term term) {
            return new AfterRuleBuilder(previous, RuleFactory.is(var, term));
        }

        public AfterRuleBuilder is(Hedge hedge, Term term) {
            return new AfterRuleBuilder(previous, RuleFactory.is(var, hedge, term));
        }
    }

    public class ThenVarSelector {

        private Rule expression;

        public ThenVarSelector(Rule expression) {
            this.expression = expression;
        }

        public FinalTermSelector var(Variable var) {
            return new FinalTermSelector(expression, var);
        }
    }

    public class FinalTermSelector {

        private Rule expression;
        private Variable var;

        public FinalTermSelector(Rule expression, Variable var) {
            this.expression = expression;
            this.var = var;
        }

        public ControllerBuilder is(Term term) {
            // build the rule
            controllerBuilder.addRule(new IfThenRule(expression, RuleFactory.is(var, term)));
            return controllerBuilder;
        }
    }

}
