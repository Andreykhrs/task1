package com.gridnine.testing.filter;

import com.gridnine.testing.filter.rules.Ruleset;
import com.gridnine.testing.model.Flight;

import java.util.List;

public class Filter {

    public static List<Flight> filter(List<Flight> flights, Ruleset ruleset) {
        return flights.stream().filter(ruleset::apply).toList();
    }
}
