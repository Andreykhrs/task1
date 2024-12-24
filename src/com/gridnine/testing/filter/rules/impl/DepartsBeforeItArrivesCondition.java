package com.gridnine.testing.filter.rules.impl;

import com.gridnine.testing.filter.rules.Condition;
import com.gridnine.testing.model.Flight;

/**
 * Условие, если перелет имеет сегмент, у которого время прибытия меньше времени вылета
 * */
public class DepartsBeforeItArrivesCondition implements Condition {

    @Override
    public boolean apply(Flight flight) {
        return flight.getSegments().stream().anyMatch(segment -> segment.getArrivalDate().isBefore(segment.getDepartureDate()));
    }

    public static ContainsInvalidSegmentConditionBuilder builder() {
        return new ContainsInvalidSegmentConditionBuilder();
    }

    public static class ContainsInvalidSegmentConditionBuilder {

        public DepartsBeforeItArrivesCondition build() {
            return new DepartsBeforeItArrivesCondition();
        }
    }
}
