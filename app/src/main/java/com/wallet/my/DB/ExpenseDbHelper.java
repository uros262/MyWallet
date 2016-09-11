package com.wallet.my.DB;

import android.content.ContentValues;
import com.wallet.my.DB.MyWalletDb.ExpenseDB;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.util.Log;

import com.wallet.my.Entity.Expense;
import com.wallet.my.Entity.InPocket;
import com.wallet.my.Entity.SearchFilter;
import com.wallet.my.Helpers.DateHelper;
import com.wallet.my.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

/**
 * Created by Uros on 28-Nov-15.
 */
public class ExpenseDbHelper extends MyWalletDbHelper{

    private Context context;
    public ExpenseDbHelper(Context context) {
        super(context);
        this.context = context;
    }

    // Add income
    public long addExpense(Expense expense)
    {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(ExpenseDB.COLUMN_NAME_AMOUNT, expense.getAmount());
        values.put(ExpenseDB.COLUMN_NAME_SPENT_ON, expense.getSpentOn());
        values.put(ExpenseDB.COLUMN_NAME_TIME, expense.getTime().toString());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId =
                db.insert(
                        ExpenseDB.TABLE_NAME,
                        null,
                        values);
        db.close();

        if(newRowId > 0){
            InPocketDbHelper inPocketDbHelper = new InPocketDbHelper(this.context);
            inPocketDbHelper.decrementInPocketBalance(expense);

            BalanceDbHelper balanceDbHelper = new BalanceDbHelper(this.context);
            return balanceDbHelper.decrementBalance(expense);
        }
        else
        {
            return newRowId;
        }
    }

    public String getExpenseStatistic() {

        String result = "";
        double totalSum = 0.00;
        DateHelper dateHelper = new DateHelper();

        String query = "SELECT * FROM " + ExpenseDB.TABLE_NAME
                + " ORDER BY " + ExpenseDB.COLUMN_NAME_ID + " DESC LIMIT 50";

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast())
        {
            if(cursor.getString(cursor.getColumnIndex(ExpenseDB.COLUMN_NAME_ID)) != null) {
                totalSum += cursor.getDouble(cursor.getColumnIndex(ExpenseDB.COLUMN_NAME_AMOUNT));
                result += cursor.getString(cursor.getColumnIndex(ExpenseDB.COLUMN_NAME_AMOUNT));
                result += " € | ";
                result += cursor.getString(cursor.getColumnIndex(ExpenseDB.COLUMN_NAME_SPENT_ON));
                result += " | ";
                result += dateHelper.getFormatedDate(cursor.getString(cursor.getColumnIndex(ExpenseDB.COLUMN_NAME_TIME)));
                result += "\n";
            }
            cursor.moveToNext();
        }

        result += "========================\n";
        DecimalFormat df = new DecimalFormat("#.00");
        result += "Total spent: " + df.format(totalSum) + " €";

        cursor.close();

        return result;
    }

    public String getTotalExpenses() {

        String result = "";
        double totalSum = 0.00;

        String query = "SELECT * FROM " + ExpenseDB.TABLE_NAME;

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast())
        {
            if(cursor.getString(cursor.getColumnIndex(ExpenseDB.COLUMN_NAME_ID)) != null) {
                totalSum += cursor.getDouble(cursor.getColumnIndex(ExpenseDB.COLUMN_NAME_AMOUNT));
            }
            cursor.moveToNext();
        }

        DecimalFormat df = new DecimalFormat("#.00");
        result += "Total spent: " + df.format(totalSum) + " €";

        cursor.close();

        return result;
    }

    public String searchExpenses(SearchFilter filter) {

        String result = "";
        double totalSum = 0.00;
        DateHelper dateHelper = new DateHelper();

        String query = "SELECT * FROM " + ExpenseDB.TABLE_NAME + " WHERE ";

        if(!filter.getWord().equals(""))
        {
            query += ExpenseDB.COLUMN_NAME_SPENT_ON + " LIKE \'%" + filter.getWord() + "%\'";
            if(
                !filter.getDay().equals("") ||
                !filter.getMonth().equals("") ||
                !filter.getYear().equals("")
                    )query += " AND ";
        }
        if(!filter.getDay().equals(""))
        {
            query += "strftime('%d', " + ExpenseDB.COLUMN_NAME_TIME + ") = \'" + filter.getDay() + "\'";
            if(
                !filter.getMonth().equals("") ||
                !filter.getYear().equals("")
                    )query += " AND ";
        }
        if(!filter.getMonth().equals(""))
        {
            query += "strftime('%m', " + ExpenseDB.COLUMN_NAME_TIME + ") = \'" + filter.getMonth() + "\'";
            if(
                !filter.getYear().equals("")
                    )query += " AND ";
        }
        if(!filter.getYear().equals(""))
        {
            query += "strftime('%Y', " + ExpenseDB.COLUMN_NAME_TIME + ") = \'" + filter.getYear() + "\'";
        }

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast())
        {
            if(cursor.getString(cursor.getColumnIndex(ExpenseDB.COLUMN_NAME_ID)) != null) {
                totalSum += cursor.getDouble(cursor.getColumnIndex(ExpenseDB.COLUMN_NAME_AMOUNT));
                result += cursor.getString(cursor.getColumnIndex(ExpenseDB.COLUMN_NAME_AMOUNT));
                result += " € | ";
                result += cursor.getString(cursor.getColumnIndex(ExpenseDB.COLUMN_NAME_SPENT_ON));
                result += " | ";
                result += dateHelper.getFormatedDate(cursor.getString(cursor.getColumnIndex(ExpenseDB.COLUMN_NAME_TIME)));
                result += "\n";
            }
            cursor.moveToNext();
        }

        result += "========================\n";
        DecimalFormat df = new DecimalFormat("#.00");
        result += "Total spent: " + df.format(totalSum) + " €";

        cursor.close();

        return result;
    }

    public JSONArray getAllData()
    {
        String query = "SELECT  * FROM " + ExpenseDB.TABLE_NAME ;
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        DateHelper dateHelper = new DateHelper();
        JSONArray allData = new JSONArray();

        while (!cursor.isAfterLast()) {

            JSONObject rowObject = new JSONObject();

            if(cursor.getString(cursor.getColumnIndex(ExpenseDB.COLUMN_NAME_ID)) != null) {
                try {
                    rowObject.put(
                            cursor.getColumnName(cursor.getColumnIndex(ExpenseDB.COLUMN_NAME_ID)),
                            cursor.getInt(cursor.getColumnIndex(ExpenseDB.COLUMN_NAME_ID))
                    );
                    rowObject.put(
                            cursor.getColumnName(cursor.getColumnIndex(ExpenseDB.COLUMN_NAME_AMOUNT)),
                            cursor.getDouble(cursor.getColumnIndex(ExpenseDB.COLUMN_NAME_AMOUNT))
                    );
                    rowObject.put(
                            cursor.getColumnName(cursor.getColumnIndex(ExpenseDB.COLUMN_NAME_SPENT_ON)),
                            cursor.getString(cursor.getColumnIndex(ExpenseDB.COLUMN_NAME_SPENT_ON))
                    );
                    rowObject.put(
                            cursor.getColumnName(cursor.getColumnIndex(ExpenseDB.COLUMN_NAME_TIME)),
                            dateHelper.getFormatedDateTime(cursor.getString(cursor.getColumnIndex(ExpenseDB.COLUMN_NAME_TIME)))
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

    public void insertBackup(JSONArray backupExpense) throws JSONException {

        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        DateHelper dateHelper = new DateHelper();

        for (int i = 0; i < backupExpense.length(); i++) {
            JSONObject row = backupExpense.getJSONObject(i);

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(
                    ExpenseDB.COLUMN_NAME_ID,
                    row.getString(ExpenseDB.COLUMN_NAME_ID)
            );
            values.put(
                    ExpenseDB.COLUMN_NAME_AMOUNT,
                    row.getString(ExpenseDB.COLUMN_NAME_AMOUNT)
            );
            values.put(
                    ExpenseDB.COLUMN_NAME_TIME,
                    dateHelper.getTimestampForDatabase(row.getString(ExpenseDB.COLUMN_NAME_TIME))
            );
            values.put(
                    ExpenseDB.COLUMN_NAME_SPENT_ON,
                    row.getString(ExpenseDB.COLUMN_NAME_SPENT_ON)
            );

            db.insert(
                    ExpenseDB.TABLE_NAME,
                    null,
                    values);
        }

        db.close();
    }
}
