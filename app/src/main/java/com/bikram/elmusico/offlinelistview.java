package com.bikram.elmusico;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class offlinelistview extends AppCompatActivity {
    static final int REQUEST_MP3_OPEN = 1;
    ArrayList<Music> offlinelist = new ArrayList<>();
    EditText filedesc, name, artist_name;
    Button next, insert, view, delete;
    Uri imageuri;
    RecyclerView recyclerView;
    MediaPlayer mediaPlayer;
    DBHandleroffline dbHandleroffline = new DBHandleroffline(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audiofile);
        filedesc = findViewById(R.id.uri);
        next = findViewById(R.id.nextpageoff);
        insert = findViewById(R.id.insertoff);
        name = findViewById(R.id.nameoff);
        artist_name = findViewById(R.id.artist_nameoff);
        view = findViewById(R.id.view_dataoff);
        delete = findViewById(R.id.deleteoff);
        filedesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("audio/mpeg");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, REQUEST_MP3_OPEN);
            }
        });
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filedesc.setText(String.valueOf(imageuri));
                if (name.getText().toString().equals("") || artist_name.getText().toString().equals("") || filedesc.getText().toString().equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(offlinelistview.this);
                    builder.setCancelable(true);
                    builder.setTitle("Alert");
                    builder.setMessage("Empty fields please fill appropriate data");
                    builder.show();
                } else {
                    boolean chckinsert = dbHandleroffline.insertData(name.getText().toString(), artist_name.getText().toString(), filedesc.getText().toString());
                    if (chckinsert) {
                        Toast.makeText(offlinelistview.this, "Success", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(offlinelistview.this, "Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = dbHandleroffline.getData();
                if (res.getCount() == 0) {
                    Toast.makeText(offlinelistview.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuilder buffer = new StringBuilder();
                while (res.moveToNext()) {
                    buffer.append("Name :").append(res.getString(0)).append("\n");
                    buffer.append("Artist Name :").append(res.getString(1)).append("\n");
                    buffer.append("Location :").append(res.getString(2)).append("\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(offlinelistview.this);
                builder.setCancelable(true);
                builder.setTitle("Musics:");
                builder.setMessage(buffer.toString());
                builder.show();

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(offlinelistview.this);
                    builder.setCancelable(true);
                    builder.setTitle("Alert");
                    builder.setMessage("Empty fields please fill appropriate data");
                    builder.show();
                } else {
                    boolean chckdelete = dbHandleroffline.deleteData(name.getText().toString());
                    if (chckdelete) {
                        Toast.makeText(offlinelistview.this, "Success deleting", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(offlinelistview.this, "Unsuccessful deleting", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor res = dbHandleroffline.getData();
                if(res.getCount()==0){
                    Toast.makeText(offlinelistview.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                while(res.moveToNext()){
                    offlinelist.add(new Music(res.getString(0),res.getString(1),Uri.parse(res.getString(2))));
                }
                setView(offlinelist);
            }
        });
    }

    private void setView(ArrayList<Music>m) {
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(offlinelistview.this));
        Adapter ad = new Adapter(offlinelistview.this,m);
        recyclerView.setAdapter(ad);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_MP3_OPEN && resultCode == RESULT_OK) {
            imageuri = data.getData();
        }
    }
}
