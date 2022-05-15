package com.bikram.elmusico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class dbactivity extends AppCompatActivity {
    Button sendnextpage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbactivity);
        sendnextpage = findViewById(R.id.nextpage);
        sendnextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(dbactivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}