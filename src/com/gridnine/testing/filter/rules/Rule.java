package com.gridnine.testing.filter.rules;

import com.gridnine.testing.model.Flight;

/**
 * Правило
 * */
public interface Rule {

    boolean apply(Flight flight);
}
