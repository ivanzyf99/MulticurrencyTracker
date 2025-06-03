package com.example.multicurrencytracker;

import java.math.BigDecimal;

public class Transaction {
    public static final int TYPE_EXPENSE = 0;
    public static final int TYPE_INCOME = 1;
    public static final int TYPE_INTERNAL = 2;

    private String title;
    private String account;
    private BigDecimal amount;
    private String currency;
    private String date;
    private int type;
    private String note;

    public Transaction(String title, String account, BigDecimal amount,
                       String currency, String date, int type, String note) {
        this.title = title;
        this.account = account;
        this.amount = amount;
        this.currency = currency;
        this.date = date;
        this.type = type;
        this.note = note;
    }

    // Getters
    public String getTitle() { return title; }
    public String getAccount() { return account; }
    public BigDecimal getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public String getDate() { return date; }
    public int getType() { return type; }
    public String getNote() { return note; }
}