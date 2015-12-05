package com.wallet.my.DB;
import com.wallet.my.DB.MyWalletDb.*;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Uros on 26-Nov-15.
 */
public class MyWalletDbHelper extends SQLiteOpenHelper {

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String DOUBLE_TYPE = " REAL";
    private static final String DATETIME_TYPE = " DATETIME";
    private static final String COMMA_SEP = ",";

    // region Table Income

    // Create Income table
    private static final String SQL_CREATE_INCOME =
            "CREATE TABLE " + IncomeDB.TABLE_NAME + " (" +
                    IncomeDB.COLUMN_NAME_ID + INTEGER_TYPE + " PRIMARY KEY," +
                    IncomeDB.COLUMN_NAME_AMOUNT + DOUBLE_TYPE + COMMA_SEP +
                    IncomeDB.COLUMN_NAME_SOURCE + TEXT_TYPE + COMMA_SEP +
                    IncomeDB.COLUMN_NAME_TYPE + TEXT_TYPE + COMMA_SEP +
                    IncomeDB.COLUMN_NAME_TIME + DATETIME_TYPE +
            " );";

    // Delete Income table
    private static final String SQL_DELETE_INCOME =
            "DROP TABLE IF EXISTS " + IncomeDB.TABLE_NAME;

    // endregion

    // region Table Expense

    // Create Expense table
    private static final String SQL_CREATE_EXPENSE =
            "CREATE TABLE " + ExpenseDB.TABLE_NAME + " (" +
                    ExpenseDB.COLUMN_NAME_ID + INTEGER_TYPE + " PRIMARY KEY," +
                    ExpenseDB.COLUMN_NAME_AMOUNT + DOUBLE_TYPE + COMMA_SEP +
                    ExpenseDB.COLUMN_NAME_SPENT_ON + TEXT_TYPE + COMMA_SEP +
                    ExpenseDB.COLUMN_NAME_TIME + DATETIME_TYPE +
                    " );";

    // Delete Expense table
    private static final String SQL_DELETE_EXPENSE =
            "DROP TABLE IF EXISTS " + ExpenseDB.TABLE_NAME;

    // endregion

    // region Table Balance

    // Create Balance table
    private static final String SQL_CREATE_BALANCE =
            "CREATE TABLE " + BalanceDB.TABLE_NAME + " (" +
                    BalanceDB.COLUMN_NAME_ID + INTEGER_TYPE + " PRIMARY KEY," +
                    BalanceDB.COLUMN_NAME_AMOUNT + DOUBLE_TYPE + COMMA_SEP +
                    BalanceDB.COLUMN_NAME_UPDATE_TIME + DATETIME_TYPE +
                    " );";

    // Delete Balance table
    private static final String SQL_DELETE_BALANCE =
            "DROP TABLE IF EXISTS " + BalanceDB.TABLE_NAME;

    // endregion

    // region Table InPocket

    // Create InPocket table
    private static final String SQL_CREATE_IN_POCKET =
            "CREATE TABLE " + InPocketDB.TABLE_NAME + " (" +
                    InPocketDB.COLUMN_NAME_ID + INTEGER_TYPE + " PRIMARY KEY," +
                    InPocketDB.COLUMN_NAME_AMOUNT + DOUBLE_TYPE + COMMA_SEP +
                    InPocketDB.COLUMN_NAME_UPDATE_TIME + DATETIME_TYPE +
                    " );";

    // Delete InPocket table
    private static final String SQL_DELETE_IN_POCKET =
            "DROP TABLE IF EXISTS " + InPocketDB.TABLE_NAME;

    // endregion

    // region Table Category

    // Create Category table
    private static final String SQL_CREATE_CATEGORY =
            "CREATE TABLE " + CategoryDB.TABLE_NAME + " (" +
                    CategoryDB.COLUMN_NAME_ID + INTEGER_TYPE + " PRIMARY KEY," +
                    CategoryDB.COLUMN_NAME_NAME + TEXT_TYPE +
                    " );";

    // Delete Category table
    private static final String SQL_DELETE_CATEGORY =
            "DROP TABLE IF EXISTS " + CategoryDB.TABLE_NAME;

    // endregion

    // region Table SpentOn

    // Create SpentOn table
    private static final String SQL_CREATE_SPENT_ON =
            "CREATE TABLE " + SpentOnDB.TABLE_NAME + " (" +
                    SpentOnDB.COLUMN_NAME_ID + INTEGER_TYPE + " PRIMARY KEY," +
                    SpentOnDB.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    SpentOnDB.COLUMN_NAME_CATEGORY_NAME + TEXT_TYPE + COMMA_SEP +
                    SpentOnDB.COLUMN_NAME_TIME + DATETIME_TYPE +
                    " );";

    // Delete SpentOn table
    private static final String SQL_DELETE_SPENT_ON =
            "DROP TABLE IF EXISTS " + SpentOnDB.TABLE_NAME;

    // endregion

    // region Table Loan

    // Create Loan table
    private static final String SQL_CREATE_LOAN =
            "CREATE TABLE " + LoanDB.TABLE_NAME + " (" +
                    LoanDB.COLUMN_NAME_ID + INTEGER_TYPE + " PRIMARY KEY," +
                    LoanDB.COLUMN_NAME_PERSON + TEXT_TYPE + COMMA_SEP +
                    LoanDB.COLUMN_NAME_TYPE + TEXT_TYPE + COMMA_SEP +
                    LoanDB.COLUMN_NAME_AMOUNT + DOUBLE_TYPE + COMMA_SEP +
                    LoanDB.COLUMN_NAME_DEADLINE + DATETIME_TYPE +
                    " );";

    // Delete Loan table
    private static final String SQL_DELETE_LOAN =
            "DROP TABLE IF EXISTS " + LoanDB.TABLE_NAME;

    // endregion

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MyWallet.db";

    public MyWalletDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_INCOME);
        db.execSQL(SQL_CREATE_EXPENSE);
        db.execSQL(SQL_CREATE_BALANCE);
        db.execSQL(SQL_CREATE_IN_POCKET);
        db.execSQL(SQL_CREATE_CATEGORY);
        db.execSQL(SQL_CREATE_SPENT_ON);
        db.execSQL(SQL_CREATE_LOAN);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_INCOME);
        db.execSQL(SQL_DELETE_EXPENSE);
        db.execSQL(SQL_DELETE_BALANCE);
        db.execSQL(SQL_DELETE_IN_POCKET);
        db.execSQL(SQL_DELETE_CATEGORY);
        db.execSQL(SQL_DELETE_SPENT_ON);
        db.execSQL(SQL_DELETE_LOAN);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}