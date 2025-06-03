package com.example.multicurrencytracker;

import java.math.BigDecimal;

public class Asset {
    private String name;
    private String currency;
    private BigDecimal balance;
    private int icon;

    public Asset(String name, String currency, BigDecimal balance, int icon) {
        this.name = name;
        this.currency = currency;
        this.balance = balance;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public int getIcon() {
        return icon;
    }
}