package com.bikram.elmusico;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class offlinelistview extends AppCompatActivity {
    static final int REQUEST_MP3_OPEN = 1;
    ArrayList<Music> m = new ArrayList<>();
    EditText filedesc;
    Button next;
    Uri imageuri;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audiofile);
        filedesc = findViewById(R.id.uri);
        next= findViewById(R.id.nextpage);
        filedesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("audio/mpeg");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, REQUEST_MP3_OPEN);
            }

        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer = MediaPlayer.create(offlinelistview.this,imageuri);
                filedesc.setText(String.valueOf(imageuri));
                mediaPlayer.start();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_MP3_OPEN && resultCode == RESULT_OK) {
             imageuri = data.getData();
        }
    }
}
