package com.opensponsor.payload;


public class ResultOfObject {
    public final Object size;
    public final String name;

    public static class Builder {
        public ResultOfObject build() {
            return new ResultOfObject(this);
        }

        public Builder size(Object size) {
            this.size = size;
            return this;
        }

        // you can set defaults for these here
        private Object size;
        private String name;
    }

    public static Builder builder() {
        return new Builder();
    }

    private ResultOfObject(Builder builder) {
        size = builder.size;
        name = builder.name;
    }
}
