package com.wallet.my.DB;

import android.content.ContentValues;
import com.wallet.my.DB.MyWalletDb.OnCardDB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wallet.my.Entity.Expense;
import com.wallet.my.Helpers.DateHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Calendar;

/**
 * Created by Uros on 28-Nov-15.
 */
public class OnCardDbHelper extends MyWalletDbHelper{

    public OnCardDbHelper(Context context) {
        super(context);
    }

    public double getOnCardBalance() {

        double result = 0.00;
        String query = "SELECT * FROM " + OnCardDB.TABLE_NAME +
                " WHERE ID = (SELECT MAX(ID) FROM " + OnCardDB.TABLE_NAME + ")";

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        if(!cursor.isAfterLast() && cursor.getString(cursor.getColumnIndex(OnCardDB.COLUMN_NAME_ID)) != null) {
            result = cursor.getDouble(cursor.getColumnIndex(OnCardDB.COLUMN_NAME_AMOUNT));
        }
        cursor.close();

        DecimalFormat df = new DecimalFormat("#.00");
        return Double.valueOf(df.format(result));
    }

    // Add balance
    public long setOnCardBalance(double income)
    {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(OnCardDB.COLUMN_NAME_AMOUNT, income);
        values.put(OnCardDB.COLUMN_NAME_UPDATE_TIME, String.valueOf(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime())));

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId =
                db.insert(
                        OnCardDB.TABLE_NAME,
                        null,
                        values);
        db.close();

        return newRowId;
    }

    public long decrementOnCardBalance(Expense expense) {

        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // get latest balance
        double currentBalance = getOnCardBalance();
        double newBalance = currentBalance - expense.getAmount();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(OnCardDB.COLUMN_NAME_AMOUNT, newBalance);
        values.put(OnCardDB.COLUMN_NAME_UPDATE_TIME, String.valueOf(expense.getTime()));

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId =
                db.insert(
                        OnCardDB.TABLE_NAME,
                        null,
                        values);
        db.close();

        return newRowId;
    }

    public JSONArray getAllData()
    {
        String query = "SELECT  * FROM " + OnCardDB.TABLE_NAME ;
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        DateHelper dateHelper = new DateHelper();
        JSONArray allData = new JSONArray();

        while (!cursor.isAfterLast()) {

            JSONObject rowObject = new JSONObject();

            if(cursor.getString(cursor.getColumnIndex(OnCardDB.COLUMN_NAME_ID)) != null) {
                try {
                    rowObject.put(
                            cursor.getColumnName(cursor.getColumnIndex(OnCardDB.COLUMN_NAME_ID)),
                            cursor.getInt(cursor.getColumnIndex(OnCardDB.COLUMN_NAME_ID))
                    );
                    rowObject.put(
                            cursor.getColumnName(cursor.getColumnIndex(OnCardDB.COLUMN_NAME_AMOUNT)),
                            cursor.getDouble(cursor.getColumnIndex(OnCardDB.COLUMN_NAME_AMOUNT))
                    );
                    rowObject.put(
                            cursor.getColumnName(cursor.getColumnIndex(OnCardDB.COLUMN_NAME_UPDATE_TIME)),
                            dateHelper.getFormatedDateTime(cursor.getString(cursor.getColumnIndex(OnCardDB.COLUMN_NAME_UPDATE_TIME)))
                    );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            allData.put(rowObject);
            cursor.moveToNext();
        }

        cursor.close();
        return allData;
    }

    public void insertBackup(JSONArray backupInPocket) throws JSONException {

        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        DateHelper dateHelper = new DateHelper();

        for(int i = 0; i<backupInPocket.length(); i++)
        {
            JSONObject row = backupInPocket.getJSONObject(i);

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(
                    OnCardDB.COLUMN_NAME_ID,
                    row.getString(OnCardDB.COLUMN_NAME_ID)
            );
            values.put(
                    OnCardDB.COLUMN_NAME_AMOUNT,
                    row.getString(OnCardDB.COLUMN_NAME_AMOUNT)
            );
            values.put(
                    OnCardDB.COLUMN_NAME_UPDATE_TIME,
                    dateHelper.getTimestampForDatabase(row.getString(OnCardDB.COLUMN_NAME_UPDATE_TIME))
            );

            db.insert(
                    OnCardDB.TABLE_NAME,
                    null,
                    values);
        }

        db.close();
    }
}
