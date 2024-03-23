package edu.java.core.util;

import java.util.List;

public interface DifferenceComparator <T> {
    List<String> getDifference(T before, T next) throws IllegalStateException;
}
