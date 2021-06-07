package com.example.theinventoryapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class DatabseActivity extends AppCompatActivity {
    private static DatabseActivity instance;

    int opened = 0;

    Bundle items_Consumables;
    Bundle items_Valuables;
    Bundle transactionHistory;

    Bundle items_Consumables_final = new Bundle();
    Bundle items_Valuables_final = new Bundle();
    Bundle transactionHistory_final = new Bundle();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database);

        instance = this;
        LoadData();

        Intent intent = new Intent(DatabseActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public static DatabseActivity getInstance() {
        return instance;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onResume(){
        super.onResume();
        opened++;
        if (opened >= 2) {
            items_Consumables = MainActivity.getInstance().items_Consumables;
            items_Valuables = MainActivity.getInstance().items_Valuables;
            transactionHistory = MainActivity.getInstance().transactionHistory;

            SaveData();
            finish();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void SaveData() {
        if (items_Consumables != null && items_Valuables != null && transactionHistory != null) {
            // Save the Consumables
            try {
                FileOutputStream fileout = openFileOutput("Consumables.txt", MODE_PRIVATE);
                BufferedOutputStream stream = new BufferedOutputStream(fileout);

                for (String key : items_Consumables.keySet()) {
                    stream.write(key.getBytes());
                    try {
                        stream.write(System.lineSeparator().getBytes());
                    } catch (IOException exc) {
                        exc.printStackTrace();
                    }
                    stream.write(String.valueOf(items_Consumables.get(key)).getBytes());
                    stream.write(System.lineSeparator().getBytes());
                }
                stream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            // Save The Valuables
            try {
                FileOutputStream fileout = openFileOutput("Valuables.txt", MODE_PRIVATE);
                BufferedOutputStream stream = new BufferedOutputStream(fileout);

                for (String key : items_Valuables.keySet()) {
                    stream.write(key.getBytes());
                    try {
                        stream.write(System.lineSeparator().getBytes());
                    } catch (IOException exc) {
                        exc.printStackTrace();
                    }
                    stream.write(String.valueOf(items_Valuables.get(key)).getBytes());
                    stream.write(System.lineSeparator().getBytes());
                }
                stream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            // Save the transaction history
            try {
                FileOutputStream fileout = openFileOutput("Transaction_History.txt", MODE_PRIVATE);
                BufferedOutputStream stream = new BufferedOutputStream(fileout);

                for (String key : transactionHistory.keySet()) {
                    stream.write(key.getBytes());
                    try {
                        stream.write(System.lineSeparator().getBytes());
                    } catch (IOException exc) {
                        exc.printStackTrace();
                    }
                    stream.write(String.valueOf(transactionHistory.get(key)).getBytes());
                    stream.write(System.lineSeparator().getBytes());
                }
                stream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void LoadData() {
        // Load the consumables
        try {
            FileInputStream fileIn = openFileInput("Consumables.txt");
            BufferedInputStream stream = new BufferedInputStream(fileIn);

            BufferedReader r = new BufferedReader(
                    new InputStreamReader(stream, StandardCharsets.UTF_8));

            for (String line = r.readLine(); line != null; line = r.readLine()) {
                String key=line;
                String keyValue = r.readLine();

                items_Consumables_final.putString(key, keyValue);
            }
            r.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Load the valuables
        try {
            FileInputStream fileIn = openFileInput("Valuables.txt");
            BufferedInputStream stream = new BufferedInputStream(fileIn);

            BufferedReader r = new BufferedReader(
                    new InputStreamReader(stream, StandardCharsets.UTF_8));

            for (String line = r.readLine(); line != null; line = r.readLine()) {
                String key=line;
                String keyValue = r.readLine();

                items_Valuables_final.putString(key, keyValue);
            }
            r.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Load the transaction history
        try {
            FileInputStream fileIn = openFileInput("Transaction_History.txt");
            BufferedInputStream stream = new BufferedInputStream(fileIn);

            BufferedReader r = new BufferedReader(
                    new InputStreamReader(stream, StandardCharsets.UTF_8));

            for (String line = r.readLine(); line != null; line = r.readLine()) {
                String key=line;
                String keyValue = r.readLine();

                transactionHistory_final.putString(key, keyValue);
            }
            r.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Detect if the home button is clicked
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onStart() {
        super.onStart();
        if (opened >= 2) {
            SaveData();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onUserLeaveHint() {
        SaveData();
        super.onUserLeaveHint();
    }
}


