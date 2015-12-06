package com.wallet.my.DB;

import android.content.ContentValues;
import com.wallet.my.DB.MyWalletDb.ExpenseDB;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wallet.my.Entity.Expense;
import com.wallet.my.Entity.InPocket;
import com.wallet.my.R;

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
        String query = "SELECT * FROM " + ExpenseDB.TABLE_NAME;

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast())
        {
            if(cursor.getString(cursor.getColumnIndex(ExpenseDB.COLUMN_NAME_ID)) != null) {
                result += cursor.getString(cursor.getColumnIndex(ExpenseDB.COLUMN_NAME_AMOUNT));
                result += " € | ";
                result += cursor.getString(cursor.getColumnIndex(ExpenseDB.COLUMN_NAME_SPENT_ON));
                result += "\n";
            }
            cursor.moveToNext();
        }

        cursor.close();

        return result;
    }
}
