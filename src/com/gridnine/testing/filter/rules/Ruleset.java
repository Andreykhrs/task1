package com.gridnine.testing.filter.rules;

import com.gridnine.testing.enums.LogicalOperator;
import com.gridnine.testing.model.Flight;

import java.util.Arrays;

/**
 * Набор правил, объединенных логической операцией
 * */
public class Ruleset implements Rule {

    /**
     * Отрицание результата
     * */
    private final boolean negative;

    /**
     * Логический оператор
     * */
    private final LogicalOperator logicalOperator;

    /**
     * Набор правил или условий
     * */
    private final Rule[] operands;

    private Ruleset(boolean negative, LogicalOperator logicalOperator, Rule... operands) {
        this.negative = negative;
        this.logicalOperator = logicalOperator;
        this.operands = operands;
    }

    public boolean apply(Flight flight) {
        boolean result = switch (this.logicalOperator) {
            case AND -> Arrays.stream(operands).allMatch(operand -> operand.apply(flight));
            case OR -> Arrays.stream(operands).anyMatch(operand -> operand.apply(flight));
        };
        return this.negative != result;
    }

    public static RulesetBuilder builder() {
        return new RulesetBuilder();
    }

    public static class RulesetBuilder {

        private boolean negative = Boolean.FALSE;

        private LogicalOperator logicalOperator = LogicalOperator.AND;

        private Rule[] operands;

        public RulesetBuilder() {}

        public Ruleset build() {
            return new Ruleset(negative, logicalOperator, operands);
        }

        public RulesetBuilder negative(boolean negative) {
            this.negative = negative;
            return this;
        }

        public RulesetBuilder logicalOperator(LogicalOperator logicalOperator) {
            this.logicalOperator = logicalOperator;
            return this;
        }

        public RulesetBuilder operands(Rule... operands) {
            this.operands = operands;
            return this;
        }
    }
}
