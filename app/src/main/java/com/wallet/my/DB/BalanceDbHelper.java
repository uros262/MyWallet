package com.wallet.my.DB;

import android.content.ContentValues;
import com.wallet.my.DB.MyWalletDb.BalanceDB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wallet.my.Entity.Expense;
import com.wallet.my.Entity.Income;
import com.wallet.my.Helpers.DateHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

/**
 * Created by Uros on 27-Nov-15.
 */
public class BalanceDbHelper extends MyWalletDbHelper{

    public BalanceDbHelper(Context context) {
        super(context);
    }

    public double getLatestBalance() {

        double result = 0.00;
        String query = "SELECT * FROM " + BalanceDB.TABLE_NAME +
                " WHERE ID = (SELECT MAX(ID) FROM " + BalanceDB.TABLE_NAME + ")";

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        if(!cursor.isAfterLast() && cursor.getString(cursor.getColumnIndex(BalanceDB.COLUMN_NAME_ID)) != null) {
            result = cursor.getDouble(cursor.getColumnIndex(BalanceDB.COLUMN_NAME_AMOUNT));
        }
        cursor.close();

        DecimalFormat df = new DecimalFormat("#.00");
        return Double.valueOf(df.format(result));
    }

    // Add balance
    public long incrementBalance(Income income)
    {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // get latest balance
        double currentBalance = getLatestBalance();
        double newBalance = currentBalance + income.getAmount();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(BalanceDB.COLUMN_NAME_AMOUNT, newBalance);
        values.put(BalanceDB.COLUMN_NAME_UPDATE_TIME, String.valueOf(income.getTime()));

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId =
                db.insert(
                        BalanceDB.TABLE_NAME,
                        null,
                        values);
        db.close();

        return newRowId;
    }

    public long decrementBalance(Expense expense) {

        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // get latest balance
        double currentBalance = getLatestBalance();
        double newBalance = currentBalance - expense.getAmount();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(BalanceDB.COLUMN_NAME_AMOUNT, newBalance);
        values.put(BalanceDB.COLUMN_NAME_UPDATE_TIME, String.valueOf(expense.getTime()));

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId =
                db.insert(
                        BalanceDB.TABLE_NAME,
                        null,
                        values);
        db.close();

        return newRowId;
    }

    public String getBalanceStatistic() {

        String result = "";
        DateHelper dateHelper = new DateHelper();

        String query = "SELECT * FROM " + BalanceDB.TABLE_NAME;

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast())
        {
            if(cursor.getString(cursor.getColumnIndex(BalanceDB.COLUMN_NAME_ID)) != null) {
                result += cursor.getString(cursor.getColumnIndex(BalanceDB.COLUMN_NAME_AMOUNT));
                result += " € | ";
                result += dateHelper.getFormatedDateTime(cursor.getString(cursor.getColumnIndex(BalanceDB.COLUMN_NAME_UPDATE_TIME)));
                result += "\n";
            }
            cursor.moveToNext();
        }

        cursor.close();

        return result;
    }

    public JSONArray getAllData()
    {
        String query = "SELECT  * FROM " + BalanceDB.TABLE_NAME ;
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        DateHelper dateHelper = new DateHelper();
        JSONArray allData = new JSONArray();

        while (!cursor.isAfterLast()) {

            JSONObject rowObject = new JSONObject();

            if(cursor.getString(cursor.getColumnIndex(BalanceDB.COLUMN_NAME_ID)) != null) {
                try {
                    rowObject.put(
                            cursor.getColumnName(cursor.getColumnIndex(BalanceDB.COLUMN_NAME_ID)),
                            cursor.getInt(cursor.getColumnIndex(BalanceDB.COLUMN_NAME_ID))
                    );
                    rowObject.put(
                            cursor.getColumnName(cursor.getColumnIndex(BalanceDB.COLUMN_NAME_AMOUNT)),
                            cursor.getDouble(cursor.getColumnIndex(BalanceDB.COLUMN_NAME_AMOUNT))
                    );
                    rowObject.put(
                            cursor.getColumnName(cursor.getColumnIndex(BalanceDB.COLUMN_NAME_UPDATE_TIME)),
                            dateHelper.getFormatedDateTime(cursor.getString(cursor.getColumnIndex(BalanceDB.COLUMN_NAME_UPDATE_TIME)))
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

    public void insertBackup(JSONArray backupBalance) throws JSONException {

        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        DateHelper dateHelper = new DateHelper();

        for(int i = 0; i<backupBalance.length(); i++)
        {
            JSONObject row = backupBalance.getJSONObject(i);

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(
                    BalanceDB.COLUMN_NAME_ID,
                    row.getString(BalanceDB.COLUMN_NAME_ID)
            );
            values.put(
                    BalanceDB.COLUMN_NAME_AMOUNT,
                    row.getString(BalanceDB.COLUMN_NAME_AMOUNT)
            );
            values.put(
                    BalanceDB.COLUMN_NAME_UPDATE_TIME,
                    dateHelper.getTimestampForDatabase(row.getString(BalanceDB.COLUMN_NAME_UPDATE_TIME))
            );

            db.insert(
                    BalanceDB.TABLE_NAME,
                    null,
                    values);
        }

        db.close();
    }
}
