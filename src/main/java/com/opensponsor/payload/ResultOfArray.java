package com.opensponsor.payload;

import java.util.List;

public class ResultOfArray {

    public int currentPageNumber;
    public int lastPageNumber;
    public int pageSize;
    public long totalRecords;
    public String message;
    public List<Object> records;
    public int code = 0;


    public static class Builder {
        public ResultOfArray build() {
            return new ResultOfArray(this);
        }

        public Builder data(List<Object> records) {
            this.records = records;
            return this;
        }

        public Builder code(int code) {
            this.code = code;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public int currentPageNumber;
        public int lastPageNumber;
        public int pageSize;
        public long totalRecords;
        public String message;
        public List<Object> records;
        public int code = 0;
    }

    public static ResultOfArray.Builder builder() {
        return new ResultOfArray.Builder();
    }

    private ResultOfArray(Builder builder) {
        currentPageNumber = builder.currentPageNumber;
        lastPageNumber = builder.lastPageNumber;
        pageSize = builder.pageSize;
        totalRecords = builder.totalRecords;
        message = builder.message;
        records = builder.records;
        code = builder.code;
    }
}
