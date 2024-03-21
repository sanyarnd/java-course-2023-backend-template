package edu.java.core;

import edu.java.core.model.GithubPersistenceData;
import edu.java.core.util.ReflectionComparator;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class ReflectionComparatorTest {
    @Test
    void compareTwoDifferentObjects() throws IllegalAccessException {
        var data1 = new GithubPersistenceData(1, 2, null, 4, 5);
        var data2 = new GithubPersistenceData(1, 2, 1, 4, 5);
        assertEquals(1, ReflectionComparator.getDifference(data1, data2).size());
    }

    @Test
    void compareTwoSimilarObjects() throws IllegalAccessException {
        var data1 = new GithubPersistenceData(1, 2, 1, 4, 5);
        var data2 = new GithubPersistenceData(1, 2, 1, 4, 5);
        assertEquals(0, ReflectionComparator.getDifference(data1, data2).size());
    }

    @Test
    void compareObjectWithSelf() throws IllegalAccessException {
        var data1 = new GithubPersistenceData(1, 2, 1, 4, 5);
        assertEquals(0, ReflectionComparator.getDifference(data1, data1).size());
    }
}
