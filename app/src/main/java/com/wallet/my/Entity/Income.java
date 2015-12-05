package com.wallet.my.Entity;

import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by Uros i Vesko on 26-Nov-15.
 */
public class Income {

    private int ID;
    private double Amount;
    private Timestamp Time;
    private String Source;
    private String Type;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    public java.sql.Timestamp getTime() {
        return Time;
    }

    public void setTime() {
        Time = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
    }

    public void setTime(java.sql.Timestamp time) {
        Time = time;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
