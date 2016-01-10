package com.wallet.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wallet.my.DB.BalanceDbHelper;

public class StatisticMain extends AppCompatActivity implements View.OnClickListener {

    TextView balance;
    Button incomes;
    Button expenses;
    BalanceDbHelper balanceDbHelper;

    private void Init(){
        balance = (TextView) findViewById(R.id.twStatisticBalance);
        balance.setMovementMethod(new ScrollingMovementMethod());

        incomes = (Button) findViewById(R.id.btnStatisticIncomes);
        incomes.setOnClickListener(this);

        expenses = (Button) findViewById(R.id.btnStatisticExpenses);
        expenses.setOnClickListener(this);

        balanceDbHelper = new BalanceDbHelper(this);
    }

    private void LoadMainBalanceStatistic()
    {
        balance.setText(balanceDbHelper.getBalanceStatistic());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_main);
        this.Init();
        this.LoadMainBalanceStatistic();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.LoadMainBalanceStatistic();
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnStatisticIncomes:
                intent = new Intent(StatisticMain.this, StatisticIncomes.class);
                startActivity(intent);
                break;
            case R.id.btnStatisticExpenses:
                intent = new Intent(StatisticMain.this, StatisticExpenses.class);
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
