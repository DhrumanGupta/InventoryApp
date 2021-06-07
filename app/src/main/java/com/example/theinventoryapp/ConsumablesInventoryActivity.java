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

public class ConsumablesInventoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consumables_inventory);

        configureBackButton();
        configureAddButton();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Bundle consumableItems = MainActivity.getInstance().getConsumables(true);

        ArrayList<String> items_Consumables = new ArrayList<String>();
        for (String key : consumableItems.keySet()) {
            items_Consumables.add(consumableItems.get(key) + " -- " + key);
        }

        Collections.sort(items_Consumables);

        ListView consumablesListView = findViewById(R.id.consumablesList);
        ArrayAdapter<String> consumablesAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, items_Consumables) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                view.setBackgroundColor(0x65000000);
                return view;
            }
        };
        consumablesListView.setAdapter(consumablesAdapter);
    }

    private void configureBackButton() {
        Button backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void configureAddButton() {
        Button addBtn = findViewById(R.id.addItemBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addItemActivity = new Intent(ConsumablesInventoryActivity.this, AddItemsAcitivity.class);
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
