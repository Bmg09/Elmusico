package com.bikram.elmusico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Music_Activity extends AppCompatActivity {
    TextView music, maxdur, currduration;
    static int pos1 = 0;
    String url, nexturl;
    String song_name;
    private SeekBar seekBar;
    private MediaPlayer mediaPlayer;
    ImageView play, replay, applelogo, previoustrack, nexttrack;
    ArrayList<Music> m = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        m.add(new Music("Blinding lights","The Weeknd","https://themamaship.com/music/Catalog/The%20Weeknd%20-%20Blinding%20Lights.mp3"));
        m.add(new Music("Cant feel my face","The Weeknd","https://themamaship.com/music/Catalog/Cant%20Feel%20My%20Face%20-%20The%20Weeknd.mp3"));
        m.add(new Music("Viva la vida", "Cold play","https://themamaship.com/music/Catalog/Coldplay%20-%20Viva%20La%20Vida.mp3"));
        m.add(new Music("Love the way u lie","eminem","https://themamaship.com/music/Catalog/Love%20The%20Way%20You%20Lie%20-%20Rihanna.mp3"));
        music = findViewById(R.id.music_name);
        seekBar = findViewById(R.id.seekBar);
        play = findViewById(R.id.play);
        replay = findViewById(R.id.replay);
        maxdur = findViewById(R.id.maxduration);
        applelogo = findViewById(R.id.imageView2);
        currduration = findViewById(R.id.currentDuration);
        previoustrack = findViewById(R.id.previous);
        nexttrack = findViewById(R.id.next);
        currduration.setText("0:00:00");
        Intent intent = getIntent();
        url = intent.getStringExtra(Adapter.EXTRA_URL);
        song_name = intent.getStringExtra(Adapter.EXTRA_SONG);
        pos1 = intent.getIntExtra(Adapter.EXTRA_POS,0);
        music.setText(song_name);
        initMedia(url);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mediaPlayer.isPlaying()) {
                    initializeSeekbar();
                    mediaPlayer.start();
                    applelogo.startAnimation(AnimationUtils.loadAnimation(Music_Activity.this, R.anim.rotateimg));
                    play.setImageResource(R.drawable.ic_baseline_pause_24);
                } else {
                    mediaPlayer.pause();
                    applelogo.clearAnimation();
                    play.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                }
            }
        });
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    mediaPlayer.seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Music_Activity.this, "5 seconds delay pls wait", Toast.LENGTH_SHORT).show();
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.reset();
                    play.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    initMedia(url);
                    delay(3);
                    mediaPlayer.start();
                    play.setImageResource(R.drawable.ic_baseline_pause_24);
                } else {
                    mediaPlayer.reset();
                    initMedia(url);
                    //Toast.makeText(Music_Activity.this, "5 seconds delay pls wait", Toast.LENGTH_SHORT).show();
                    delay(3);
                    play.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                }

            }
        });
        //Log.d("Outside", String.valueOf(pos1));
        nexttrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.reset();
                    play.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    pos1++;
                    if(pos1<m.size()){
                        initMedia(m.get(pos1).url);
                        delay(3);
                        music.setText(m.get(pos1).m_name);
                        initializeSeekbar();
                        mediaPlayer.start();
                        applelogo.startAnimation(AnimationUtils.loadAnimation(Music_Activity.this, R.anim.rotateimg));
                        play.setImageResource(R.drawable.ic_baseline_pause_24);
                    }
                    else{
                        Toast.makeText(Music_Activity.this, "End of list", Toast.LENGTH_SHORT).show();
                        Music_Activity.this.finish();
                    }
                }
                else{
                    mediaPlayer.reset();
                    play.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    pos1++;
                    if(pos1<m.size()){
                        initMedia(m.get(pos1).url);
                        delay(3);
                        music.setText(m.get(pos1).m_name);
                        initializeSeekbar();
                        mediaPlayer.start();
                        applelogo.startAnimation(AnimationUtils.loadAnimation(Music_Activity.this, R.anim.rotateimg));
                        play.setImageResource(R.drawable.ic_baseline_pause_24);
                    }
                    else{
                        Toast.makeText(Music_Activity.this, "End of list", Toast.LENGTH_SHORT).show();
                        Music_Activity.this.finish();
                    }
                }

            }
        });
        Log.d("Outside", String.valueOf(pos1));
        previoustrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()) {
                    mediaPlayer.reset();
                    play.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    pos1--;
                    if (pos1 > -1) {
                        initMedia(m.get(pos1).url);
                        delay(3);
                        initializeSeekbar();
                        mediaPlayer.start();
                        applelogo.startAnimation(AnimationUtils.loadAnimation(Music_Activity.this, R.anim.rotateimg));
                        music.setText(m.get(pos1).m_name);
                        play.setImageResource(R.drawable.ic_baseline_pause_24);
                    } else {
                        Toast.makeText(Music_Activity.this, "End of list", Toast.LENGTH_SHORT).show();
                        Music_Activity.this.finish();
                    }
                }
                else{
                    mediaPlayer.reset();
                    play.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    pos1--;
                    if (pos1 > -1) {
                        initMedia(m.get(pos1).url);
                        delay(3);
                        initializeSeekbar();
                        mediaPlayer.start();
                        applelogo.startAnimation(AnimationUtils.loadAnimation(Music_Activity.this, R.anim.rotateimg));
                        music.setText(m.get(pos1).m_name);
                        play.setImageResource(R.drawable.ic_baseline_pause_24);
                    } else {
                        Toast.makeText(Music_Activity.this, "End of list", Toast.LENGTH_SHORT).show();
                        Music_Activity.this.finish();
                    }
                }

            }
        });

    }


    private void delay(int i) {
        try {
            TimeUnit.SECONDS.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void initMedia(String url) {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(url);
        } catch (Exception E) {
            E.printStackTrace();
        }
        mediaPlayer.prepareAsync();
        delay(3);
        mediaPlayer.setOnPreparedListener(mediaPlayer -> {
            Toast.makeText(Music_Activity.this, "Buffering wait please", Toast.LENGTH_SHORT).show();
            maxdur.setText(getmaxDur());
        });
    }

    private String getmaxDur() {
        long seconds = mediaPlayer.getDuration() / 1000;
        long s = seconds % 60;
        long m = (seconds / 60) % 60;
        long h = (seconds / (60 * 60)) % 24;
        String format = String.format("%d:%02d:%02d", h, m, s);
        return format;
    }


    private void initializeSeekbar() {
        seekBar.setMax(mediaPlayer.getDuration());
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    long seconds = mediaPlayer.getCurrentPosition() / 1000;
                    long s = seconds % 60;
                    long m = (seconds / 60) % 60;
                    long h = (seconds / (60 * 60)) % 24;
                    String format = String.format("%d:%02d:%02d", h, m, s);
                    currduration.setText(format);
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    handler.postDelayed(this, 100);
                } catch (Exception e) {
                    seekBar.setProgress(0);
                }
            }
        }, 0);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.reset();
        url = null;
    }
}