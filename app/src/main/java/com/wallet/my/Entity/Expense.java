package com.wallet.my.Entity;

import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by Uros on 26-Nov-15.
 */
public class Expense {

    private int ID;
    private double Amount;
    private Timestamp Time;
    private String SpentOn;

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

    public Timestamp getTime() {
        return Time;
    }

    public void setTime() {
        Time = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
    }

    public void setTime(java.sql.Timestamp time) {
        Time = time;
    }

    public String getSpentOn() {
        return SpentOn;
    }

    public void setSpentOn(String spentOn) {
        SpentOn = spentOn;
    }
}
