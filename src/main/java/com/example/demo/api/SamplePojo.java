package com.example.demo.api;

public class SamplePojo {
    private String val;

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public SamplePojo(String val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return "SamplePojo{" +
                "val='" + val + '\'' +
                '}';
    }
}
