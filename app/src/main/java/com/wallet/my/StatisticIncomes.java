package com.wallet.my;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.wallet.my.DB.IncomeDbHelper;

public class StatisticIncomes extends AppCompatActivity {

    TextView incomes;
    IncomeDbHelper incomeDbHelper;

    private void Init(){
        incomes = (TextView) findViewById(R.id.twStatisticIncomes);
        incomes.setMovementMethod(new ScrollingMovementMethod());
        incomeDbHelper = new IncomeDbHelper(this);
    }

    private void LoadIncomeStatistic()
    {
        incomes.setText(incomeDbHelper.getIncomesStatistic());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_incomes);
        this.Init();
        this.LoadIncomeStatistic();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.LoadIncomeStatistic();
    }
}
