package com.mbakturk.exercise;

import java.util.Comparator;

public class OrderByDescSort implements Comparator<String[]> {

    private int columnId;

    public OrderByDescSort(int columnId) {
        this.columnId = columnId;
    }

    @Override
    public int compare(String[] column1, String[] column2) {
        return column2[this.columnId].compareTo(column1[this.columnId]);
    }
}
