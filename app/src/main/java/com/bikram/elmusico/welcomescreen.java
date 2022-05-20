package com.bikram.elmusico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class welcomescreen extends AppCompatActivity {
    Button online,offline;
    ToggleButton toggleButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomescreen);
        toggleButton = findViewById(R.id.toggleButton);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(welcomescreen.this, "online", Toast.LENGTH_SHORT).show();
                    if(isNetworkAvailable()){
                        Intent intent = new Intent(welcomescreen.this,dbactivity.class);
                        Toast.makeText(welcomescreen.this, "internet available", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(welcomescreen.this, "Check internet connection", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(welcomescreen.this, "Offline", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(welcomescreen.this,offlinelistview.class);
                    startActivity(intent);
                }
            }
        });
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) welcomescreen.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}