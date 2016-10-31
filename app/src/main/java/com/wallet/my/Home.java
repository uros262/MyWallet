package com.wallet.my;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wallet.my.DB.BalanceDbHelper;
import com.wallet.my.DB.InPocketDbHelper;
import com.wallet.my.DB.OnCardDbHelper;

import java.text.DecimalFormat;

public class Home extends AppCompatActivity implements View.OnClickListener {

    LinearLayout layoutInPocket;
    TextView inPocket;
    InPocketDbHelper inPocketDbHelper;

    LinearLayout layoutEditInPocket;
    EditText editInPocket;
    Button btnInPocketChange;

    LinearLayout layoutOnCard;
    TextView onCard;
    OnCardDbHelper onCardDbHelper;

    LinearLayout layoutEditOnCard;
    EditText editOnCard;
    Button btnOnCardChange;

    TextView balance;
    Button incomeBtn;
    Button expenseBtn;
    Button statisticBtn;
    BalanceDbHelper balanceDbHelper;

    private void Init() {
        layoutInPocket = (LinearLayout) findViewById(R.id.llhInPocket);
        inPocket = (TextView) findViewById(R.id.twInPocketAmount);
        inPocketDbHelper = new InPocketDbHelper(this);
        inPocket.setText(String.valueOf(inPocketDbHelper.getInPocketBalance()));
        inPocket.setOnClickListener(this);

        layoutEditInPocket = (LinearLayout) findViewById(R.id.llhInPocketChange);
        editInPocket = (EditText) findViewById(R.id.etInPocket);
        btnInPocketChange = (Button) findViewById(R.id.btnInPocket);
        btnInPocketChange.setOnClickListener(this);

        layoutOnCard = (LinearLayout) findViewById(R.id.llhOnCard);
        onCard = (TextView) findViewById(R.id.twOnCardAmount);
        onCardDbHelper = new OnCardDbHelper(this);
        onCard.setText(String.valueOf(onCardDbHelper.getOnCardBalance()));
        onCard.setOnClickListener(this);

        layoutEditOnCard = (LinearLayout) findViewById(R.id.llhOnCardChange);
        editOnCard = (EditText) findViewById(R.id.etOnCard);
        btnOnCardChange = (Button) findViewById(R.id.btnOnCard);
        btnOnCardChange.setOnClickListener(this);

        balance = (TextView) findViewById(R.id.twBalanceAmount);
        balanceDbHelper = new BalanceDbHelper(this);
        balance.setText(String.valueOf(balanceDbHelper.getLatestBalance()));

        incomeBtn = (Button) findViewById(R.id.btnIncome);
        incomeBtn.setOnClickListener(this);

        expenseBtn = (Button) findViewById(R.id.btnExpense);
        expenseBtn.setOnClickListener(this);

        statisticBtn = (Button) findViewById(R.id.btnStatistic);
        statisticBtn.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.Init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        balance.setText(String.valueOf(balanceDbHelper.getLatestBalance()));
        inPocket.setText(String.valueOf(inPocketDbHelper.getInPocketBalance()));
        onCard.setText(String.valueOf(onCardDbHelper.getOnCardBalance()));
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.twInPocketAmount:
                layoutInPocket.setVisibility(View.GONE);
                editInPocket.setText(inPocket.getText());
                layoutEditInPocket.setVisibility(View.VISIBLE);
                break;
            case R.id.btnInPocket:
                layoutEditInPocket.setVisibility(View.GONE);
                if(editInPocket.getText().toString().equals(""))
                {
                    inPocket.setText("0.00");
                }
                else
                {
                    DecimalFormat df = new DecimalFormat("0.00");
                    String newInPocketValue = df.format(Double.parseDouble(editInPocket.getText().toString()));
                    inPocket.setText(newInPocketValue);
                    inPocketDbHelper.setInPocketBalance(Double.parseDouble(newInPocketValue));
                }
                layoutInPocket.setVisibility(View.VISIBLE);
                break;
            case R.id.twOnCardAmount:
                layoutOnCard.setVisibility(View.GONE);
                editOnCard.setText(onCard.getText());
                layoutEditOnCard.setVisibility(View.VISIBLE);
                break;
            case R.id.btnOnCard:
                layoutEditOnCard.setVisibility(View.GONE);
                if(editOnCard.getText().toString().equals(""))
                {
                    onCard.setText("0.00");
                }
                else
                {
                    DecimalFormat df = new DecimalFormat("0.00");
                    String newOnCardValue = df.format(Double.parseDouble(editOnCard.getText().toString()));
                    onCard.setText(newOnCardValue);
                    onCardDbHelper.setOnCardBalance(Double.parseDouble(newOnCardValue));
                }
                layoutOnCard.setVisibility(View.VISIBLE);
                break;
            case R.id.btnIncome:
                intent = new Intent(Home.this, AddIncome.class);
                startActivity(intent);
                break;
            case R.id.btnExpense:
                intent = new Intent(Home.this, AddExpense.class);
                startActivity(intent);
                break;
            case R.id.btnStatistic:
                intent = new Intent(Home.this, StatisticMain.class);
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
