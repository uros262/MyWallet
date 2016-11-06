package com.wallet.my;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.wallet.my.DB.IncomeDbHelper;
import com.wallet.my.Entity.Income;

public class AddIncome extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    EditText amount;
    EditText source;
    RadioGroup type;
    String incomeType;
    Button addIncome;
    IncomeDbHelper incomeDbHelper;

    private void Init(){
        amount = (EditText) findViewById(R.id.etAmount);
        source = (EditText) findViewById(R.id.etSource);

        incomeType = "unknown";
        type = (RadioGroup) findViewById(R.id.rgIncomeType);
        type.setOnCheckedChangeListener(this);

        addIncome = (Button) findViewById(R.id.btnAddIncome);
        addIncome.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income);
        this.Init();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnAddIncome) {

            // region Validation

            if(source.getText().toString().equals("")){
                Toast.makeText(AddIncome.this, getString(R.string.source) + " " +
                        getString(R.string.validation_empty), Toast.LENGTH_SHORT).show();
                return;
            }
            if(amount.getText().toString().equals("")){
                Toast.makeText(AddIncome.this, getString(R.string.amount) + " " +
                        getString(R.string.validation_empty), Toast.LENGTH_SHORT).show();
                return;
            }
            if(incomeType.equals("unknown")){
                Toast.makeText(AddIncome.this, getString(R.string.type_of_income) + " " +
                        getString(R.string.validation_selected), Toast.LENGTH_SHORT).show();
                return;
            }

            // endregion

            incomeDbHelper = new IncomeDbHelper(this);
            Income income = new Income();

            income.setTime();

            income.setSource(source.getText().toString());
            income.setAmount(Double.parseDouble(amount.getText().toString()));
            income.setType(incomeType);

            long lastId = incomeDbHelper.addIncome(income);
            if (lastId > 0) {
                Toast.makeText(AddIncome.this, getString(R.string.income_added), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AddIncome.this, getString(R.string.income_not_added), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rbEarn:
                incomeType = "earned";
                break;
            case R.id.rbGift:
                incomeType = "gift";
                break;
        }
    }
}
