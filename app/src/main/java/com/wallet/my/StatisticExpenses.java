package com.wallet.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wallet.my.DB.ExpenseDbHelper;

public class StatisticExpenses extends AppCompatActivity implements View.OnClickListener {

    TextView expenses;
    Button mainStatistic;
    Button incomes;
    ExpenseDbHelper expensesDbHelper;

    private void Init(){
        expenses = (TextView) findViewById(R.id.twStatisticExpenses);

        mainStatistic = (Button) findViewById(R.id.btnStatisticMain);
        mainStatistic.setOnClickListener(this);

        incomes = (Button) findViewById(R.id.btnStatisticIncomes);
        incomes.setOnClickListener(this);

        expensesDbHelper = new ExpenseDbHelper(this);
    }

    private void LoadExpenseStatistic()
    {
        expenses.setText(expensesDbHelper.getExpenseStatistic());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_expenses);
        this.Init();
        this.LoadExpenseStatistic();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.LoadExpenseStatistic();
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnStatisticMain:
                intent = new Intent(StatisticExpenses.this, StatisticMain.class);
                startActivity(intent);
                break;
            case R.id.btnStatisticIncomes:
                intent = new Intent(StatisticExpenses.this, StatisticIncomes.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
