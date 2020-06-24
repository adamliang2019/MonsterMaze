package com.company.model;

import java.util.Map;
import java.util.HashMap;

/***
 * Direction enum represents a direction a character can move in the maze
 * each direction has a corresponding integer and the Direction can be generated
 * from that integer (UI takes inputs as integers)
 */

public enum Direction {
    LEFT(0), RIGHT(2), UP(1), DOWN(3);

    private static final Map<Integer, Direction> BY_INT = new HashMap<>();

    static {
        for (Direction d: values()) {
            BY_INT.put(d.integer, d);
        }
    }

    public final int integer;

    Direction(int integer) {
        this.integer = integer;
    }

    public static Direction valueOfInteger(int integer) {
        return BY_INT.get(integer);
    }
}
