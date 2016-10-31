package com.wallet.my;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.wallet.my.DB.ExpenseDbHelper;
import com.wallet.my.Entity.Expense;

public class AddExpense extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    EditText price;
    EditText payFor;
    RadioGroup type;
    String paymentType;
    Button addExpense;
    ExpenseDbHelper expenseDbHelper;

    private void Init(){
        price = (EditText) findViewById(R.id.etExpensePrice);
        payFor = (EditText) findViewById(R.id.etPayFor);

        paymentType = "unknown";
        type = (RadioGroup) findViewById(R.id.rgPaymentType);
        type.setOnCheckedChangeListener(this);

        addExpense = (Button) findViewById(R.id.btnExpensePay);
        addExpense.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        this.Init();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnExpensePay) {

            // region Validation

            if(payFor.getText().toString().equals("")){
                Toast.makeText(AddExpense.this, getString(R.string.pay_for) + " " +
                        getString(R.string.validation_empty), Toast.LENGTH_SHORT).show();
                return;
            }
            if(price.getText().toString().equals("")){
                Toast.makeText(AddExpense.this, getString(R.string.price) + " " +
                        getString(R.string.validation_empty), Toast.LENGTH_SHORT).show();
                return;
            }
            if(paymentType.equals("unknown")){
                Toast.makeText(AddExpense.this, getString(R.string.payment_type) + " " +
                        getString(R.string.validation_selected), Toast.LENGTH_SHORT).show();
                return;
            }

            // endregion

            expenseDbHelper = new ExpenseDbHelper(this);
            Expense expense = new Expense();
            expense.setTime();
            expense.setSpentOn(payFor.getText().toString());
            expense.setPaymentType(paymentType);

            double cost = 0.00;
            cost = Double.parseDouble(price.getText().toString());
            expense.setAmount(cost);

            long lastId = expenseDbHelper.addExpense(expense);
            if (lastId > 0) {
                Toast.makeText(AddExpense.this, getString(R.string.expense_added), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AddExpense.this, getString(R.string.expense_not_added), Toast.LENGTH_LONG).show();
            }
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

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rbCache:
                paymentType = "cache";
                break;
            case R.id.rbCard:
                paymentType = "card";
                break;
        }
    }
}
