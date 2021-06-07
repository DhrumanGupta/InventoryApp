package com.example.theinventoryapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class TransactionHistoryActivity extends AppCompatActivity {
    String selectedItemx;
    String selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_history);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Bundle transactionHistory = MainActivity.getInstance().getTransactionHistory();

        ArrayList<String> transactionHistoryArray = new ArrayList<String>();
        for (String key : transactionHistory.keySet()) {
            transactionHistoryArray.add(key);
        }

        ListView consumablesListView = findViewById(R.id.consumablesList);
        ArrayAdapter<String> consumablesAdapter = new ArrayAdapter<String>(this, R.layout.mytextview, transactionHistoryArray);
        consumablesListView.setAdapter(consumablesAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onUserLeaveHint() {
        DatabseActivity.getInstance().SaveData();
        super.onUserLeaveHint();
    }
}
