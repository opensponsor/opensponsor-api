package com.opensponsor.payload;

import java.util.List;

public class ResultOfArray<E> {

    int currentPageNumber;
    int lastPageNumber;
    int pageSize;
    long totalRecords;
    String message;
    List<E> records;

    public ResultOfArray(List<E> records) {
        this.records = records;
    }
}
