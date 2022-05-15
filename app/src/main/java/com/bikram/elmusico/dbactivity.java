package com.bikram.elmusico;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class dbactivity extends AppCompatActivity {
    Button sendnextpage,insert,delete;
    DBHandler dbHandler;
    EditText name,artist_name,url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbactivity);
        sendnextpage = findViewById(R.id.nextpage);
        insert = findViewById(R.id.insert);
        name = findViewById(R.id.name);
        artist_name = findViewById(R.id.artist_name);
        url = findViewById(R.id.url);
        delete = findViewById(R.id.delete);
        dbHandler = new DBHandler(this);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().equals("")||artist_name.getText().toString().equals("")||url.getText().toString().equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(dbactivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("Alert");
                    builder.setMessage("Empty fields please fill appropriate data");
                    builder.show();
                }else {
                    Boolean chckinsert = dbHandler.insertuserdata(name.getText().toString(), artist_name.getText().toString(), url.getText().toString());
                    if (chckinsert) {
                        Toast.makeText(dbactivity.this, "Success", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(dbactivity.this, "Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean chckdelete = dbHandler.deletedata(name.getText().toString());
                if(chckdelete){
                    Toast.makeText(dbactivity.this, "Success deleting", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(dbactivity.this, "Unsuccessful deleting", Toast.LENGTH_SHORT).show();
                }
            }
        });
        sendnextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(dbactivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}