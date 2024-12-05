/*
 * This source file was generated by the Gradle 'init' task
 */
package org.example;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

class TupleTest {
    @Test
    void test_pointWhenLastElementIsOne() {
        float[] point = { 0, 0, 0, 1 };
        assertTrue(Tuple.isPoint(point));
        assertFalse(Tuple.isVector(point));
    }

    @Test
    void test_vectorWhenLastElementIsZero() {
        float[] point = { 0, 0, 0, 0 };
        assertFalse(Tuple.isPoint(point));
        assertTrue(Tuple.isVector(point));
    }

    @Test
    void test_createPoint() {
        assertTrue(Tuple.isPoint(Tuple.point(1, 2, 3)));
    }

    @Test
    void test_createVector() {
        assertTrue(Tuple.isVector(Tuple.vector(1, 2, 3)));
    }
}
