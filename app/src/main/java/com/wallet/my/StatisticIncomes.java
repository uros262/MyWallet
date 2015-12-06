package com.wallet.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wallet.my.DB.IncomeDbHelper;

public class StatisticIncomes extends AppCompatActivity implements View.OnClickListener {

    TextView incomes;
    Button mainStatistic;
    Button expenses;
    IncomeDbHelper incomeDbHelper;

    private void Init(){
        incomes = (TextView) findViewById(R.id.twStatisticIncomes);

        mainStatistic = (Button) findViewById(R.id.btnStatisticMain);
        mainStatistic.setOnClickListener(this);

        expenses = (Button) findViewById(R.id.btnStatisticExpenses);
        expenses.setOnClickListener(this);

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

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnStatisticMain:
                intent = new Intent(StatisticIncomes.this, StatisticMain.class);
                startActivity(intent);
                break;
            case R.id.btnStatisticExpenses:
                intent = new Intent(StatisticIncomes.this, StatisticExpenses.class);
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
