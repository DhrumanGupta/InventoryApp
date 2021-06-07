package com.example.theinventoryapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class ValuablesInventoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valuables_inventory);

        configureBackButton();
        configureAddButton();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Bundle valuableItems = MainActivity.getInstance().getValuables(true);

        ArrayList<String> items_Valuables = new ArrayList<String>();
        for (String key : valuableItems.keySet()) {
            items_Valuables.add(valuableItems.get(key) + " -- " + key);
        }

        Collections.sort(items_Valuables);

        ListView ValuablesListView = findViewById(R.id.valuablesList);
        ArrayAdapter<String> valuablesAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, items_Valuables) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                view.setBackgroundColor(0x65000000);
                return view;
            }
        };
        ValuablesListView.setAdapter(valuablesAdapter);
    }

    private void configureBackButton() {
        Button backBtn = findViewById(R.id.backBtn2);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void configureAddButton() {
        Button addBtn = findViewById(R.id.addItemBtn2);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addItemActivity = new Intent(ValuablesInventoryActivity.this, AddItemsAcitivity.class);
                startActivity(addItemActivity);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onUserLeaveHint() {
        DatabseActivity.getInstance().SaveData();
        super.onUserLeaveHint();
    }
}
