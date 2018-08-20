package com.example.root.myapplication;

public class ResponseObj {
    private String[] error;
    private Result result;

    private class Result {
        private CurrencyPair XETHZUSD;
    }

    private class CurrencyPair {
        private String[] a;
        private String[] b;
        private String[] c;
        private String[] v;
        private String[] p;
        private int[] t;
        private String[] l;
        private String[] h;
        private String o;
    }

    public String[] getA() {
        return result.XETHZUSD.a;
    }

    public String[] getB() {
        return result.XETHZUSD.b;
    }

    public String[] getC() {
        return result.XETHZUSD.c;
    }

    public String[] getV() {
        return result.XETHZUSD.v;
    }

}
