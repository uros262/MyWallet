package com.wallet.my.DB;

import android.provider.BaseColumns;

/**
 * Created by Uros on 26-Nov-15.
 */
public final class MyWalletDb {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public MyWalletDb() {}

    /* Inner class that defines the table contents */
    public static abstract class IncomeDB implements BaseColumns {
        public static final String TABLE_NAME = "Income";
        public static final String COLUMN_NAME_ID = "ID";
        public static final String COLUMN_NAME_AMOUNT = "Amount";
        public static final String COLUMN_NAME_TIME = "Time";
        public static final String COLUMN_NAME_SOURCE = "Source";
        public static final String COLUMN_NAME_TYPE = "Type";
    }

    /* Inner class that defines the table contents */
    public static abstract class ExpenseDB implements BaseColumns {
        public static final String TABLE_NAME = "Expense";
        public static final String COLUMN_NAME_ID = "ID";
        public static final String COLUMN_NAME_AMOUNT = "Amount";
        public static final String COLUMN_NAME_TIME = "Time";
        public static final String COLUMN_NAME_SPENT_ON = "SpentOn";
    }

    /* Inner class that defines the table contents */
    public static abstract class BalanceDB implements BaseColumns {
        public static final String TABLE_NAME = "Balance";
        public static final String COLUMN_NAME_ID = "ID";
        public static final String COLUMN_NAME_AMOUNT = "Amount";
        public static final String COLUMN_NAME_UPDATE_TIME = "UpdateTime";
    }

    /* Inner class that defines the table contents */
    public static abstract class InPocketDB implements BaseColumns {
        public static final String TABLE_NAME = "InPocket";
        public static final String COLUMN_NAME_ID = "ID";
        public static final String COLUMN_NAME_AMOUNT = "Amount";
        public static final String COLUMN_NAME_UPDATE_TIME = "UpdateTime";
    }

    /* Inner class that defines the table contents */
    public static abstract class OnCardDB implements BaseColumns {
        public static final String TABLE_NAME = "OnCard";
        public static final String COLUMN_NAME_ID = "ID";
        public static final String COLUMN_NAME_AMOUNT = "Amount";
        public static final String COLUMN_NAME_UPDATE_TIME = "UpdateTime";
    }

    /* Inner class that defines the table contents */
    public static abstract class CategoryDB implements BaseColumns {
        public static final String TABLE_NAME = "Category";
        public static final String COLUMN_NAME_ID = "ID";
        public static final String COLUMN_NAME_NAME = "Name";
    }

    /* Inner class that defines the table contents */
    public static abstract class SpentOnDB implements BaseColumns {
        public static final String TABLE_NAME = "SpentOn";
        public static final String COLUMN_NAME_ID = "ID";
        public static final String COLUMN_NAME_NAME = "Name";
        public static final String COLUMN_NAME_CATEGORY_NAME = "CategoryName";
        public static final String COLUMN_NAME_TIME = "Time";
    }

    /* Inner class that defines the table contents */
    public static abstract class LoanDB implements BaseColumns {
        public static final String TABLE_NAME = "Loan";
        public static final String COLUMN_NAME_ID = "ID";
        public static final String COLUMN_NAME_PERSON = "Person";
        public static final String COLUMN_NAME_AMOUNT = "Amount";
        public static final String COLUMN_NAME_TYPE = "Type";
        public static final String COLUMN_NAME_DEADLINE = "Deadline";
    }
}