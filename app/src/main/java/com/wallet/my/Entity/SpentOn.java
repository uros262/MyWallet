package com.wallet.my.Entity;

import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by Uros i Vesko on 26-Nov-15.
 */
public class SpentOn {

    private int ID;
    private String Name;
    private String CategoryName;
    private Timestamp Time;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
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
}
