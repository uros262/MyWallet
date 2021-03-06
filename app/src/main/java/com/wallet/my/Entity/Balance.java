package com.wallet.my.Entity;

import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by Uros on 26-Nov-15.
 */
public class Balance {

    private int ID;
    private double Amount;
    private Timestamp UpdateTime;

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

    public Timestamp getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime() {
        UpdateTime = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
    }

    public void setUpdateTime(java.sql.Timestamp time) {
        UpdateTime = time;
    }
}
