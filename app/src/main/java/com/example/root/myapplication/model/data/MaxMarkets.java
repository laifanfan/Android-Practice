package com.example.root.myapplication.model.data;

import java.util.ArrayList;
import java.util.List;

public class MaxMarkets {
    private Market btctwd;
    private Market ethtwd;
    private Market mithtwd;

    public double getBtcTwdLast() {
        return btctwd.getLast();
    }

    public double getBtcTwdVol() {
        return btctwd.getVol();
    }

    public double getBtcTwcHigh() {
        return btctwd.getHigh();
    }

    public double getBtcTwcLow() {
        return btctwd.getLow();
    }

    public double getEthTwdLast() {
        return ethtwd.getLast();
    }

    public double getEthTwdVol() {
        return ethtwd.getVol();
    }

    public double getEthTwcHigh() {
        return ethtwd.getHigh();
    }

    public double getEthTwcLow() {
        return ethtwd.getLow();
    }

    public double getMithTwdLast() {
        return mithtwd.getLast();
    }

    public double getMithTwdVol() {
        return mithtwd.getVol();
    }

    public double getMithTwcHigh() {
        return mithtwd.getHigh();
    }

    public double getMithTwcLow() {
        return mithtwd.getLow();
    }

    public List<Market> getMarketsArray() {
        List<Market> marketList = new ArrayList<>();

        btctwd.setCurrencyBtc();
        ethtwd.setCurrencyEth();
        mithtwd.setCurrencyMith();
        marketList.add(btctwd);
        marketList.add(ethtwd);
        marketList.add(mithtwd);

        return marketList;
    }
}
