package com.bikram.elmusico;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<Music> m = new ArrayList<>();
    RecyclerView recyclerView;
    Adapter ad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        m.add(new Music("Blinding lights","The Weeknd","https://themamaship.com/music/Catalog/The%20Weeknd%20-%20Blinding%20Lights.mp3"));
//        m.add(new Music("Cant feel my face","The Weeknd","https://themamaship.com/music/Catalog/Cant%20Feel%20My%20Face%20-%20The%20Weeknd.mp3"));
//        m.add(new Music("Viva la vida",
//                "Cold play","https://themamaship.com/music/Catalog/Coldplay%20-%20Viva%20La%20Vida.mp3"));
        //m.add(new Music("Love the way u lie","eminem","https://themamaship.com/music/Catalog/Love%20The%20Way%20You%20Lie%20-%20Rihanna.mp3"));
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ad = new Adapter(this,m);
        recyclerView.setAdapter(ad);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL));
    }
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        m.clear();
        ad.notifyDataSetChanged();
    }
}