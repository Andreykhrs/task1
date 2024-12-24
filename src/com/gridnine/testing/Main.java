package com.gridnine.testing;

import com.gridnine.testing.filter.rules.Ruleset;
import com.gridnine.testing.filter.rules.impl.DepartsBeforeItArrivesCondition;
import com.gridnine.testing.filter.rules.impl.DepartsTimeCompareCondition;
import com.gridnine.testing.filter.rules.impl.LandTimeCompareCondition;

import static com.gridnine.testing.enums.CompareOperator.*;
import static com.gridnine.testing.enums.LogicalOperator.AND;
import static com.gridnine.testing.enums.UnitsOfMeasurement.HOURS;
import static com.gridnine.testing.factory.FlightBuilder.createFlights;
import static com.gridnine.testing.filter.Filter.filter;
import static java.time.LocalDateTime.now;

public class Main {

    public static void main(String[] args) {
        var flights = createFlights();

        System.out.println("Исходный набор перелетов");
        System.out.println(flights);

        System.out.println();
        System.out.println("Перелеты, у которых время вылета после текущего момента");
        System.out.println(filter(flights, Ruleset.builder()
                .negative(true)
                .operands(DepartsTimeCompareCondition.builder()
                        .operator(LESS)
                        .value(now())
                        .build())
                .build()));

        System.out.println();
        System.out.println("Перелеты, у которых нет сегментов с датой прилета меньше даты вылета");
        System.out.println(filter(flights, Ruleset.builder()
                .negative(true)
                .operands(DepartsBeforeItArrivesCondition.builder().build())
                .build()));

        System.out.println();
        System.out.println("Перелеты, где общее время на земле не превышает два часа");
        System.out.println(filter(flights, Ruleset.builder()
                .operands(LandTimeCompareCondition.builder()
                        .measurement(HOURS)
                        .setOperator(LESS_OR_EQUAL)
                        .setValue(2)
                        .build())
                .build()));

        System.out.println();
        System.out.println("Все условия сразу");
        System.out.println(filter(flights, Ruleset.builder()
                .logicalOperator(AND)
                .operands(
                        Ruleset.builder()
                                .negative(true)
                                .operands(DepartsTimeCompareCondition.builder()
                                        .operator(LESS)
                                        .value(now())
                                        .build())
                                .build(),
                        Ruleset.builder()
                                .negative(true)
                                .operands(DepartsBeforeItArrivesCondition.builder().build())
                                .build(),
                        LandTimeCompareCondition.builder()
                                .measurement(HOURS)
                                .setOperator(LESS_OR_EQUAL)
                                .setValue(2)
                                .build()
                )
                .build()));
    }
}
