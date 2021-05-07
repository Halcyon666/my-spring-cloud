package com.whalefall.domain;

/**
 * @Author: WhaleFall541
 * @Date: 2020/9/9 22:29
 */
public class Info {
    private String bankType;
    private String tradeCode;

    @Override
    public String toString() {
        return "Info{" +
                "bankType='" + bankType + '\'' +
                ", tradeCode='" + tradeCode + '\'' +
                '}';
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getTradeCode() {
        return tradeCode;
    }

    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode;
    }
}
