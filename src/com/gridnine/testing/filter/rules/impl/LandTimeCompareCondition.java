package com.gridnine.testing.filter.rules.impl;

import com.gridnine.testing.enums.CompareOperator;
import com.gridnine.testing.enums.UnitsOfMeasurement;
import com.gridnine.testing.filter.rules.Condition;
import com.gridnine.testing.model.Flight;

import static com.gridnine.testing.util.CompareUtil.compare;
import static com.gridnine.testing.util.CompareUtil.getDifference;

/**
 * Условие для сравнения общего времени, проведенного на земле
 * */
public class LandTimeCompareCondition implements Condition {

    private final UnitsOfMeasurement measurement;

    private final long value;

    private final CompareOperator operator;

    private LandTimeCompareCondition(UnitsOfMeasurement measurement, long value, CompareOperator operator) {
        this.measurement = measurement;
        this.value = value;
        this.operator = operator;
    }

    @Override
    public boolean apply(Flight flight) {
        long sum = 0;
        for (int i = 0; i < flight.getSegments().size() - 1; i++) {
            var segment1 = flight.getSegments().get(i);
            var segment2 = flight.getSegments().get(i + 1);
            sum += getDifference(measurement, segment2.getDepartureDate(), segment1.getArrivalDate());
        }
        return compare(this.operator, sum, this.value);
    }

    public static LandTimeCompareConditionBuilder builder() {
        return new LandTimeCompareConditionBuilder();
    }

    public static class LandTimeCompareConditionBuilder {

        private UnitsOfMeasurement measurement;

        private long value;

        private CompareOperator operator;

        public LandTimeCompareCondition build() {
            return new LandTimeCompareCondition(measurement, value, operator);
        }

        public LandTimeCompareConditionBuilder measurement(UnitsOfMeasurement measurement) {
            this.measurement = measurement;
            return this;
        }

        public LandTimeCompareConditionBuilder setValue(long value) {
            this.value = value;
            return this;
        }

        public LandTimeCompareConditionBuilder setOperator(CompareOperator operator) {
            this.operator = operator;
            return this;
        }
    }
}
