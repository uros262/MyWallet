package com.wallet.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wallet.my.DB.BalanceDbHelper;
import com.wallet.my.DB.ExpenseDbHelper;
import com.wallet.my.DB.InPocketDbHelper;
import com.wallet.my.DB.IncomeDbHelper;
import com.wallet.my.DB.MyWalletDb;
import com.wallet.my.DB.OnCardDbHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import cz.msebera.android.httpclient.Header;

public class StatisticMain extends AppCompatActivity implements View.OnClickListener {

    Button incomes;
    Button expenses;
    BalanceDbHelper balanceDbHelper;
    InPocketDbHelper inPocketDbHelper;
    OnCardDbHelper onCardDbHelper;
    IncomeDbHelper incomeDbHelper;
    ExpenseDbHelper expenseDbHelper;

    private void Init(){

        incomes = (Button) findViewById(R.id.btnStatisticIncomes);
        incomes.setOnClickListener(this);

        expenses = (Button) findViewById(R.id.btnStatisticExpenses);
        expenses.setOnClickListener(this);

        balanceDbHelper = new BalanceDbHelper(this);
        inPocketDbHelper = new InPocketDbHelper(this);
        onCardDbHelper = new OnCardDbHelper(this);
        incomeDbHelper = new IncomeDbHelper(this);
        expenseDbHelper = new ExpenseDbHelper(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_main);
        this.Init();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        switch (id) {
            case R.id.mBackupDatabase:
                JSONObject allData = new JSONObject();

                JSONArray allBalanceData  = balanceDbHelper.getAllData();
                JSONArray allInPocketData = inPocketDbHelper.getAllData();
                JSONArray allOnCardData   = onCardDbHelper.getAllData();
                JSONArray allIncomeData   = incomeDbHelper.getAllData();
                JSONArray allExpenseData  = expenseDbHelper.getAllData();

                try {
                    allData.put(MyWalletDb.BalanceDB.TABLE_NAME, allBalanceData);
                    allData.put(MyWalletDb.InPocketDB.TABLE_NAME, allInPocketData);
                    allData.put(MyWalletDb.OnCardDB.TABLE_NAME, allOnCardData);
                    allData.put(MyWalletDb.IncomeDB.TABLE_NAME, allIncomeData);
                    allData.put(MyWalletDb.ExpenseDB.TABLE_NAME, allExpenseData);

                    //Create AsycHttpClient object
                    AsyncHttpClient client = new AsyncHttpClient();
                    RequestParams params = new RequestParams();
                    params.put("bidon", allData);
                    client.post("http://192.168.1.66/insertuser.php", params, new AsyncHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            Toast.makeText(getApplicationContext(), new String(responseBody), Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                catch(Exception ex)
                {
//                    ex.getMessage();
                }
                break;
            case R.id.mRestoreDatabase:

                try {

                    //Create AsycHttpClient object
                    AsyncHttpClient client = new AsyncHttpClient();
                    //http://loopj.com/android-async-http/
                    client.get("http://192.168.1.66/insertuser.php?giveMeBackup=true", new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            // If the response is JSONObject instead of expected JSONArray
                            // Log.d("Rezultat", response.toString());
                            // https://developer.android.com/reference/org/json/JSONObject.html
                            try {
                                JSONArray backupBalance = response.getJSONArray(MyWalletDb.BalanceDB.TABLE_NAME);
                                JSONArray backupInPocket = response.getJSONArray(MyWalletDb.InPocketDB.TABLE_NAME);
                                JSONArray backupOnCard = response.getJSONArray(MyWalletDb.OnCardDB.TABLE_NAME);
                                JSONArray backupIncome = response.getJSONArray(MyWalletDb.IncomeDB.TABLE_NAME);
                                JSONArray backupExpense = response.getJSONArray(MyWalletDb.ExpenseDB.TABLE_NAME);

                                balanceDbHelper.insertBackup(backupBalance);
                                inPocketDbHelper.insertBackup(backupInPocket);
                                onCardDbHelper.insertBackup(backupOnCard);
                                incomeDbHelper.insertBackup(backupIncome);
                                expenseDbHelper.insertBackup(backupExpense);

                                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                            Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_LONG).show();
                        }

                    });
                }
                catch(Exception ex)
                {
//                    ex.getMessage();
                }

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
