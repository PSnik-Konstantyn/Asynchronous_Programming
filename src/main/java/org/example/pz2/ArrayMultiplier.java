package org.example.pz2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class ArrayMultiplier implements Callable<List<Integer>> {
    private final int[] array;
    private final int start;
    private final int end;
    private final int multiplier;

    public ArrayMultiplier(int[] array, int start, int end, int multiplier) {
        this.array = array;
        this.start = start;
        this.end = end;
        this.multiplier = multiplier;
    }

    @Override
    public List<Integer> call() {
        List<Integer> result = new ArrayList<>();
        for (int i = start; i < end; i++) {
            result.add(array[i] * multiplier);
        }
        return result;
    }
}