package com.wallet.my.DB;

import android.content.ContentValues;

import com.wallet.my.AddIncome;
import com.wallet.my.DB.MyWalletDb.IncomeDB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wallet.my.Entity.Balance;
import com.wallet.my.Entity.Income;
import com.wallet.my.Helpers.DateHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Uros on 26-Nov-15.
 */
public class IncomeDbHelper extends MyWalletDbHelper{

    private Context context;
    public IncomeDbHelper(Context context) {
        super(context);
        this.context = context;
    }

    // Add income
    public long addIncome(Income income)
    {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(IncomeDB.COLUMN_NAME_AMOUNT, income.getAmount());
        values.put(IncomeDB.COLUMN_NAME_SOURCE, income.getSource());
        values.put(IncomeDB.COLUMN_NAME_TYPE, income.getType());
        values.put(IncomeDB.COLUMN_NAME_TIME, income.getTime().toString());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId =
                db.insert(
                        IncomeDB.TABLE_NAME,
                        null,
                        values);
        db.close();

        if(newRowId > 0){
            BalanceDbHelper balanceDbHelper = new BalanceDbHelper(this.context);
            return balanceDbHelper.incrementBalance(income);
        }
        else
        {
            return newRowId;
        }
    }

    public String getIncomesStatistic() {

        String result = "";
        double totalSum = 0.00;
        DateHelper dateHelper = new DateHelper();

        String query = "SELECT * FROM " + IncomeDB.TABLE_NAME;

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            if(cursor.getString(cursor.getColumnIndex(IncomeDB.COLUMN_NAME_ID)) != null){
                totalSum += cursor.getDouble(cursor.getColumnIndex(IncomeDB.COLUMN_NAME_AMOUNT));
                result += cursor.getString(cursor.getColumnIndex(IncomeDB.COLUMN_NAME_ID));
                result += ". ";
                result += cursor.getString(cursor.getColumnIndex(IncomeDB.COLUMN_NAME_SOURCE));
                result += ": ";
                result += cursor.getString(cursor.getColumnIndex(IncomeDB.COLUMN_NAME_AMOUNT));
                result += " € | ";
                result += dateHelper.getFormatedDate(cursor.getString(cursor.getColumnIndex(IncomeDB.COLUMN_NAME_TIME)));
                result += ";\n";
            }
            cursor.moveToNext();
        }

        result += "========================\n";
        DecimalFormat df = new DecimalFormat("#.00");
        result += "Total earned: " + df.format(totalSum) + " €";

        cursor.close();

        return result;
    }

    public JSONArray getAllData()
    {
        String query = "SELECT  * FROM " + IncomeDB.TABLE_NAME ;
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        DateHelper dateHelper = new DateHelper();
        JSONArray allData = new JSONArray();

        while (!cursor.isAfterLast()) {

            JSONObject rowObject = new JSONObject();

            if(cursor.getString(cursor.getColumnIndex(IncomeDB.COLUMN_NAME_ID)) != null) {
                try {
                    rowObject.put(
                            cursor.getColumnName(cursor.getColumnIndex(IncomeDB.COLUMN_NAME_ID)),
                            cursor.getInt(cursor.getColumnIndex(IncomeDB.COLUMN_NAME_ID))
                    );
                    rowObject.put(
                            cursor.getColumnName(cursor.getColumnIndex(IncomeDB.COLUMN_NAME_AMOUNT)),
                            cursor.getDouble(cursor.getColumnIndex(IncomeDB.COLUMN_NAME_AMOUNT))
                    );
                    rowObject.put(
                            cursor.getColumnName(cursor.getColumnIndex(IncomeDB.COLUMN_NAME_SOURCE)),
                            cursor.getString(cursor.getColumnIndex(IncomeDB.COLUMN_NAME_SOURCE))
                    );
                    rowObject.put(
                            cursor.getColumnName(cursor.getColumnIndex(IncomeDB.COLUMN_NAME_TYPE)),
                            cursor.getString(cursor.getColumnIndex(IncomeDB.COLUMN_NAME_TYPE))
                    );
                    rowObject.put(
                            cursor.getColumnName(cursor.getColumnIndex(IncomeDB.COLUMN_NAME_TIME)),
                            dateHelper.getFormatedDateTime(cursor.getString(cursor.getColumnIndex(IncomeDB.COLUMN_NAME_TIME)))
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

    public void insertBackup(JSONArray backupIncome) throws JSONException {

        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        DateHelper dateHelper = new DateHelper();

        for (int i = 0; i < backupIncome.length(); i++) {
            JSONObject row = backupIncome.getJSONObject(i);

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(
                    IncomeDB.COLUMN_NAME_ID,
                    row.getString(IncomeDB.COLUMN_NAME_ID)
            );
            values.put(
                    IncomeDB.COLUMN_NAME_AMOUNT,
                    row.getString(IncomeDB.COLUMN_NAME_AMOUNT)
            );
            values.put(
                    IncomeDB.COLUMN_NAME_TIME,
                    dateHelper.getTimestampForDatabase(row.getString(IncomeDB.COLUMN_NAME_TIME))
            );
            values.put(
                    IncomeDB.COLUMN_NAME_SOURCE,
                    row.getString(IncomeDB.COLUMN_NAME_SOURCE)
            );
            values.put(
                    IncomeDB.COLUMN_NAME_TYPE,
                    row.getString(IncomeDB.COLUMN_NAME_TYPE)
            );

            db.insert(
                    IncomeDB.TABLE_NAME,
                    null,
                    values);
        }

        db.close();
    }
}
