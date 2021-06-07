package com.example.theinventoryapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AddItemsAcitivity extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioButton;
    EditText newItem;
    EditText newQuantity;
    Boolean isValuable = false;
    String newItemName;
    String newItemQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_items);

        configureSubmitButton();
        configureBackButton();
    }

    private void configureSubmitButton() {
        Button submit_addBtn = findViewById(R.id.sumbit_addItemBtn);
        radioGroup = findViewById(R.id.radioGroup);
        newItem = findViewById(R.id.itemName);
        newQuantity = findViewById(R.id.itemQuantity);

        submit_addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radioId = radioGroup.getCheckedRadioButtonId();

                radioButton = findViewById(radioId);
                newItemName = newItem.getText().toString();
                newItemQuantity = newQuantity.getText().toString().replaceAll("[^0-9]", "");

                if (radioId == -1 || newItemName.isEmpty() || newItemQuantity.isEmpty()) {
                    FillAllFieldsPrompt();
                    return;
                }

                if (radioButton == findViewById(R.id.valuableCheckbox)) {
                    isValuable = true;
                } else if (radioButton == findViewById(R.id.consumableCheckbox)) {
                    isValuable = false;
                }

                MainActivity.getInstance().addItem(newItemName, isValuable, newItemQuantity);
                finish();
            }
        });
    }

    private void FillAllFieldsPrompt() {
        Toast.makeText(getApplicationContext(), "Please Fill in all the fields", Toast.LENGTH_SHORT).show();
    }

    private void configureBackButton() {
        Button backBtn = findViewById(R.id.backBtn4);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onUserLeaveHint() {
        if (DatabseActivity.getInstance().opened == 2) {
            DatabseActivity.getInstance().SaveData();
        }
        super.onUserLeaveHint();
    }
}

