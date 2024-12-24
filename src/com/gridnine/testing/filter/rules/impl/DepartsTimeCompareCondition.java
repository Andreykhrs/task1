package com.gridnine.testing.filter.rules.impl;

import com.gridnine.testing.enums.CompareOperator;
import com.gridnine.testing.filter.rules.Condition;
import com.gridnine.testing.model.Flight;

import java.time.LocalDateTime;

import static com.gridnine.testing.util.CompareUtil.compare;

/**
 * Условие для сравнения времени вылета
 * */
public class DepartsTimeCompareCondition implements Condition {

    private final CompareOperator operator;

    private final LocalDateTime value;

    private DepartsTimeCompareCondition(CompareOperator operator, LocalDateTime value) {
        this.operator = operator;
        this.value = value;
    }

    @Override
    public boolean apply(Flight flight) {
        return flight.getSegments().stream().anyMatch(segment -> compare(this.operator, segment.getDepartureDate(), this.value));
    }

    public static DepartsTimeCompareConditionBuilder builder() {
        return new DepartsTimeCompareConditionBuilder();
    }

    public static class DepartsTimeCompareConditionBuilder {
        private CompareOperator operator;
        private LocalDateTime value;

        private DepartsTimeCompareConditionBuilder() {}

        public DepartsTimeCompareConditionBuilder operator(CompareOperator operator) {
            this.operator = operator;
            return this;
        }

        public DepartsTimeCompareConditionBuilder value(LocalDateTime value) {
            this.value = value;
            return this;
        }

        public DepartsTimeCompareCondition build() {
            return new DepartsTimeCompareCondition(operator, value);
        }
    }
}
