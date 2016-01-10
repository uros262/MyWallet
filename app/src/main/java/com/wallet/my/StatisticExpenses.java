package com.wallet.my;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wallet.my.DB.ExpenseDbHelper;
import com.wallet.my.Entity.SearchFilter;

public class StatisticExpenses extends AppCompatActivity implements View.OnClickListener{

    TextView expenses;
    Button searchBtn;
    EditText textToSearch;
    EditText day;
    EditText month;
    EditText year;
    ExpenseDbHelper expensesDbHelper;

    private void Init(){
        expenses = (TextView) findViewById(R.id.twStatisticExpenses);
        expenses.setMovementMethod(new ScrollingMovementMethod());
        searchBtn = (Button) findViewById(R.id.btnSearchExpenses);
        searchBtn.setOnClickListener(this);
        textToSearch = (EditText) findViewById(R.id.etSearchExpenses);
        day = (EditText) findViewById(R.id.etDay);
        month = (EditText) findViewById(R.id.etMonth);
        year = (EditText) findViewById(R.id.etYear);
        expensesDbHelper = new ExpenseDbHelper(this);
    }

    private void LoadExpenseStatistic()
    {
        expenses.setText(expensesDbHelper.getTotalExpenses()
            + "\n\n========================\n\n" +
            expensesDbHelper.getExpenseStatistic()
        );
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
        if(view.getId() == R.id.btnSearchExpenses) {

            // region Validation

            if(textToSearch.getText().toString().equals("") &&
                day.getText().toString().equals("") &&
                month.getText().toString().equals("") &&
                year.getText().toString().equals("") ){
                Toast.makeText(StatisticExpenses.this,"All " +
                        getString(R.string.validation_empty), Toast.LENGTH_SHORT).show();
                return;
            }

            // endregion

            // Set Filter
            SearchFilter sf = new SearchFilter();
            sf.setDay(day.getText().toString());
            sf.setMonth(month.getText().toString());
            sf.setYear(year.getText().toString());
            sf.setWord(textToSearch.getText().toString());

            expenses.setText(expensesDbHelper.searchExpenses(sf));
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
