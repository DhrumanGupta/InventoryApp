package com.example.theinventoryapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static MainActivity Instance;

    Bundle nullValue = new Bundle();

    Bundle transactionHistory;
    Bundle items_Consumables;
    Bundle items_Valuables;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);
        transactionHistory = DatabseActivity.getInstance().transactionHistory_final;
        items_Consumables = DatabseActivity.getInstance().items_Consumables_final;
        items_Valuables = DatabseActivity.getInstance().items_Valuables_final;

        configureConsumableButton();
        configureValuableButton();
        configureAddButton();
        configureGiveButton();
        configureHistoryBtn();
        ConfigureSaveBtn();

        nullValue.putString("Hey looks like you are new here! Go to 'Add an Item' to get started! ", " ");

        Instance = this;
    }

    public static MainActivity getInstance(){
        return Instance;
    }

    private void ConfigureSaveBtn() {
        Button consumablesBtn = findViewById(R.id.SaveBtn);
        consumablesBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void configureConsumableButton() {
        Button consumablesBtn = findViewById(R.id.viewConsumablesBtn);
        consumablesBtn.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
               Intent consumablesActivity = new Intent(MainActivity.this, ConsumablesInventoryActivity.class);
               startActivity(consumablesActivity);
           }
        });
    }

    private void configureValuableButton() {
        Button valuablesBtn = findViewById(R.id.viewValuablesBtn);
        valuablesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent valuablesActivity = new Intent(MainActivity.this, ValuablesInventoryActivity.class);
                startActivity(valuablesActivity);
            }
        });
    }

    private void configureAddButton() {
        Button addBtn = findViewById(R.id.addItemBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addItemActivity = new Intent(MainActivity.this, AddItemsAcitivity.class);
                startActivity(addItemActivity);
            }
        });
    }

    private void configureGiveButton() {
        Button giveBtn = findViewById(R.id.giveItemBtn);
        giveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giveItemActivity = new Intent(MainActivity.this, GiveItemsActivity.class);
                startActivity(giveItemActivity);
            }
        });
    }

    private void configureHistoryBtn() {
        Button btn = findViewById(R.id.viewTransactionHistory);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(MainActivity.this, TransactionHistoryActivity.class);
                Intent intent = new Intent(MainActivity.this, TransactionHistoryActivity.class);
                startActivity(intent);
            }
        });
    }

    public void updateConsumableBundle(String itemName, int quantity){
        String value = items_Consumables.getString(itemName);
        if (value == null){
            items_Consumables.putString(itemName, String.valueOf(quantity));
        } else {
            int oldQuantity = Integer.parseInt(items_Consumables.getString(itemName));
            int newQuantity = quantity + oldQuantity;
            String newQuantityS = Integer.toString(newQuantity);
            items_Consumables.putString(itemName, newQuantityS);
        }
        if (Integer.valueOf(items_Consumables.getString(itemName)) == 0) {
            items_Consumables.remove(itemName);
        }
    }

    public void updateValuableBundle(String itemName, int quantity){
        String value = items_Valuables.getString(itemName);
        if (value == null){
            items_Valuables.putString(itemName, String.valueOf(quantity));
        } else {
            int oldQuantity = Integer.parseInt(items_Valuables.getString(itemName));
            int newQuantity = quantity + oldQuantity;
            String newQuantityS = Integer.toString(newQuantity);
            items_Valuables.putString(itemName, newQuantityS);
        }
        if (Integer.valueOf(items_Valuables.getString(itemName)) == 0) {
            items_Valuables.remove(itemName);
        }
    }

    public void addItem(String eitemName, boolean isValuable, String quantity){
        String itemNamex = eitemName.toLowerCase();
        String itemName = itemNamex.substring(0, 1).toUpperCase() + itemNamex.substring(1);

        if (!isValuable){
            updateConsumableBundle(itemName, Integer.parseInt(quantity));
        } else {
            updateValuableBundle(itemName, Integer.parseInt(quantity));
        }
    }

    private void addToHistory(String itemName, int quantity, String valuableOrConsumable, String personName) {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c);

        transactionHistory.putString(formattedDate + " : " + personName + " --> " + valuableOrConsumable + " : " + quantity + " " + itemName, " ");
    }

    public void giveItem(String eitemName, boolean isValuable, String quantity, String name){
        String itemNamex = eitemName.toLowerCase();
        String itemName = itemNamex.substring(0, 1).toUpperCase() + itemNamex.substring(1);
        String personNamex = name.toLowerCase();
        String personName = personNamex.substring(0, 1).toUpperCase() + personNamex.substring(1);

        if (isValuable){
            updateConsumableBundle(itemName, Integer.parseInt(quantity) * -1);
            addToHistory(itemName, Integer.parseInt(quantity), "Valuable", personName);
        } else {
            updateValuableBundle(itemName, Integer.parseInt(quantity) * -1);
            addToHistory(itemName, Integer.parseInt(quantity), "Consumable", personName);
        }
    }

    public Bundle getConsumables(boolean calledFromInventory) {
        if (items_Consumables.size() == 0 && calledFromInventory) {
            return nullValue;
        }
        return items_Consumables;
    }



    public Bundle getValuables(boolean calledFromInventory) {
        if (items_Valuables.size() == 0 && calledFromInventory) {
            return nullValue;
        }
        return items_Valuables;
    }

    public Bundle getTransactionHistory() {
        return transactionHistory;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onUserLeaveHint() {
        DatabseActivity.getInstance().SaveData();
        super.onUserLeaveHint();
    }
}
