package com.example.theinventoryapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import android.widget.Toast;
import java.util.ArrayList;

public class GiveItemsActivity extends AppCompatActivity {
    Bundle valuableItems;
    Bundle consumableItems;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Spinner nameSpinner;
    Spinner qtySpinner;
    ArrayList<String> items_Valuables;
    ArrayList<String> items_Consumables;
    ArrayList<String> selectedItemQtyArray;
    String selectedItem;
    boolean isValuable;
    int selectedItemQty;

    EditText giveToName;
    String personName;

    String ItemQty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.give_items_activity);

        valuableItems = MainActivity.getInstance().getValuables(false);
        consumableItems = MainActivity.getInstance().getConsumables(false);

        ConfigureBackButton();
        UpdateNameSpinner();
        UpdateQuantitySpinner();
        ConfigureSubmitBtn();
    }

    private void ConfigureBackButton() {
        Button backBtn = findViewById(R.id.backBtn3);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void UpdateNameSpinner() {
        items_Valuables = new ArrayList<String>();
        items_Consumables = new ArrayList<String>();
        radioGroup = findViewById(R.id.radioGroup2);


        // Add the names of valuable Items to the array list
        items_Valuables.addAll(valuableItems.keySet());

        // Add the names of consumable Items to the array list
        items_Consumables.addAll(consumableItems.keySet());

        nameSpinner = findViewById(R.id.nameSpinner);

        nameSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);

                if(radioId == -1)
                {
                    Toast.makeText(getApplicationContext(), "Please select the item type", Toast.LENGTH_SHORT).show();
                }

                if (radioButton == findViewById(R.id.valuableCheckbox2)) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(GiveItemsActivity.this, android.R.layout.simple_spinner_item, items_Valuables);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    nameSpinner.setAdapter(adapter);
                    isValuable = true;
                }
                else if (radioButton == findViewById(R.id.consumableCheckbox2)) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(GiveItemsActivity.this, android.R.layout.simple_spinner_item, items_Consumables);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    nameSpinner.setAdapter(adapter);
                    isValuable = false;
                }
                return v.performClick();
            }
        });

        nameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem =  nameSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void UpdateQuantitySpinner() {
        qtySpinner = findViewById(R.id.qtySpinner);

        qtySpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                selectedItemQtyArray = new ArrayList<String>();
                if(selectedItem != null){
                    if (isValuable) {
                        selectedItemQty = Integer.parseInt(valuableItems.getString(selectedItem));
                    } if (!isValuable) {
                        selectedItemQty = Integer.parseInt(consumableItems.getString(selectedItem));
                    }
                    for (int i = 1; i <= selectedItemQty; i++){
                        Log.i("Current Item Quantity", String.valueOf(i));
                        selectedItemQtyArray.add(String.valueOf(i));
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(GiveItemsActivity.this, android.R.layout.simple_spinner_item, selectedItemQtyArray);
                    qtySpinner.setAdapter(adapter);
                } else {
                    Toast.makeText(getApplicationContext(), "Please select the item name", Toast.LENGTH_SHORT).show();
                }
                return v.performClick();
            }
        });

        qtySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ItemQty = qtySpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void ConfigureSubmitBtn() {
        Button submitBtn = findViewById(R.id.giveItemBtn);
        giveToName = findViewById(R.id.giveToName);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                personName = giveToName.getText().toString();


                if (AllFieldsAreFilled()) {
                    MainActivity.getInstance().giveItem(selectedItem, isValuable, ItemQty, personName);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private boolean AllFieldsAreFilled() {
        if (selectedItem != null && !personName.isEmpty()) {
            return true;
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onUserLeaveHint() {
        DatabseActivity.getInstance().SaveData();
        super.onUserLeaveHint();
    }
}
