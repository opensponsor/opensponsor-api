package com.opensponsor.payload;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

public class ResultOfArray<T> {
    @Schema(required = true)
    public String message = "ok";

    @Schema(required = true)
    public Long code = 200L;
    public List<T> records = new ArrayList<>();

    public ResultOfArray(List<T> list) {
        this.records = list;
    }

    public ResultOfArray() {}

    public ResultOfArray<T> code(long code) {
        this.code = code;
        return this;
    }

    public ResultOfArray<T> message(String message) {
        this.message = message;
        return this;
    }
}
