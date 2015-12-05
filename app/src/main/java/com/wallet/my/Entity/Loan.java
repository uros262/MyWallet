package com.wallet.my.Entity;

import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by Uros i Vesko on 26-Nov-15.
 */
public class Loan {

    private int ID;
    private double Amount;
    private Timestamp Deadline;
    private String Person;
    private String Type; // give or take

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

    public Timestamp getDeadline() {
        return Deadline;
    }

    public void setDeadline() {
        Deadline = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
    }

    public void setDeadline(java.sql.Timestamp time) {
        Deadline = time;
    }

    public String getPerson() {
        return Person;
    }

    public void setPerson(String person) {
        Person = person;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
