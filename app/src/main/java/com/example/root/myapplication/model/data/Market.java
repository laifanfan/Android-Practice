package com.example.root.myapplication.model.data;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Date;

public class Market {
    enum Currency {
        BTC, ETH, MITH
    }

    private Currency currency;
    private long at;
    private double buy;
    private double sell;
    private double open;
    private double high;
    private double low;
    private double last;
    private double vol;

    public void setCurrencyBtc() {
        currency = Currency.BTC;
    }

    public void setCurrencyEth() {
        currency = Currency.ETH;
    }

    public void setCurrencyMith() {
        currency = Currency.MITH;
    }

    public boolean isBtc() {
        return currency == Currency.BTC;
    }

    public boolean isEth() {
        return currency == Currency.ETH;
    }

    public boolean isMith() {
        return currency == Currency.MITH;
    }

    public long getTime() {
        return at;
    }

    public double getBuy() {
        return buy;
    }

    public double getSell() {
        return sell;
    }

    public double getOpen() {
        return open;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getLast() {
        return last;
    }

    public double getVol() {
        return vol;
    }

}
