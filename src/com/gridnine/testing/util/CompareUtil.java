package com.gridnine.testing.util;

import com.gridnine.testing.enums.CompareOperator;
import com.gridnine.testing.enums.UnitsOfMeasurement;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

import static com.gridnine.testing.enums.CompareOperator.*;
import static com.gridnine.testing.enums.UnitsOfMeasurement.*;

public class CompareUtil {

    private static final Map<CompareOperator, BiFunction<LocalDateTime, LocalDateTime, Boolean>> compareLocalDateTimeMap = Map.of(
            LESS, LocalDateTime::isBefore,
            MORE, LocalDateTime::isAfter,
            EQUAL, LocalDateTime::isEqual,
            NOT_EQUAL, (left, right) -> !left.isEqual(right),
            MORE_OR_EQUAL, (left, right) -> left.isEqual(right) || left.isAfter(right),
            LESS_OR_EQUAL, (left, right) -> left.isEqual(right) || left.isBefore(right)
    );

    private static final Map<CompareOperator, BiFunction<Long, Long, Boolean>> compareLongMap = Map.of(
            LESS, (left, right) -> left < right,
            MORE, (left, right) -> left > right,
            EQUAL, Objects::equals,
            NOT_EQUAL, (left, right) -> !Objects.equals(left, right),
            MORE_OR_EQUAL, (left, right) -> left >= right,
            LESS_OR_EQUAL, (left, right) -> left <= right
    );

    private static final Map<UnitsOfMeasurement, BiFunction<LocalDateTime, LocalDateTime, Long>> getLocalDateTimeDifferenceMap = Map.of(
            MINUTES, ChronoUnit.MINUTES::between,
            HOURS, ChronoUnit.HOURS::between,
            DAYS, ChronoUnit.DAYS::between
    );

    public static boolean compare(CompareOperator operator, LocalDateTime left, LocalDateTime right) {
        if (!compareLocalDateTimeMap.containsKey(operator)) throw new IllegalArgumentException();
        return compareLocalDateTimeMap.get(operator).apply(left, right);
    }

    public static boolean compare(CompareOperator operator, Long left, Long right) {
        if (!compareLongMap.containsKey(operator)) throw new IllegalArgumentException();
        return compareLongMap.get(operator).apply(left, right);
    }

    public static long getDifference(UnitsOfMeasurement measurement, LocalDateTime left, LocalDateTime right) {
        if (!getLocalDateTimeDifferenceMap.containsKey(measurement)) throw new IllegalArgumentException();
        return getLocalDateTimeDifferenceMap.get(measurement).apply(right, left);
    }
}
