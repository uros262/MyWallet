package com.wallet.my.DB;

import android.content.ContentValues;

import com.wallet.my.AddIncome;
import com.wallet.my.DB.MyWalletDb.IncomeDB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wallet.my.Entity.Balance;
import com.wallet.my.Entity.Income;

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

    public String getAllIncomes() {

        String result = "";
        String query = "SELECT * FROM " + IncomeDB.TABLE_NAME;

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            if(cursor.getString(cursor.getColumnIndex(IncomeDB.COLUMN_NAME_ID)) != null){
                result += cursor.getString(cursor.getColumnIndex(IncomeDB.COLUMN_NAME_ID));
                result += ". ";
                result += cursor.getString(cursor.getColumnIndex(IncomeDB.COLUMN_NAME_SOURCE));
                result += ": ";
                result += cursor.getString(cursor.getColumnIndex(IncomeDB.COLUMN_NAME_AMOUNT));
                result += " € at ";
                result += cursor.getString(cursor.getColumnIndex(IncomeDB.COLUMN_NAME_TIME));
                result += ";\n";
            }
            cursor.moveToNext();
        }

        cursor.close();

        return result;
    }
}
