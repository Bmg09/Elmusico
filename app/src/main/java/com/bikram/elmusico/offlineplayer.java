package com.bikram.elmusico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class offlineplayer extends AppCompatActivity {
    TextView music, maxdur, currduration;
    static int pos1 = 0;
    String url;
    String song_name;
    Uri location;
    private SeekBar seekBar;
    private MediaPlayer mediaPlayer;
    ImageView play, replay, applelogo, previoustrack, nexttrack;
    ArrayList<Music> offline = offlinelistview.offlinelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offlineplayer);
        music = findViewById(R.id.music_nameoff);
        seekBar = findViewById(R.id.seekBaroff);
        play = findViewById(R.id.playoff);
        replay = findViewById(R.id.replayoff);
        maxdur = findViewById(R.id.maxdurationoff);
        applelogo = findViewById(R.id.imageView2);
        currduration = findViewById(R.id.currentDurationoff);
        previoustrack = findViewById(R.id.previousoff);
        nexttrack = findViewById(R.id.nextoff);
        currduration.setText("0:00:00");
        Intent intent = getIntent();
        location = Uri.parse(intent.getStringExtra(Adapter.EXTRA_URI));
        music.setText(intent.getStringExtra(Adapter.EXTRA_SONG_OFF));
        pos1 = intent.getIntExtra(Adapter.EXTRA_POS, 0);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mediaPlayer.isPlaying()) {
                    initializeSeekbar();
                    mediaPlayer.start();
                    applelogo.startAnimation(AnimationUtils.loadAnimation(offlineplayer.this, R.anim.rotateimg));
                    play.setImageResource(R.drawable.ic_baseline_pause_24);
                } else {
                    mediaPlayer.pause();
                    applelogo.clearAnimation();
                    play.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                }
            }
        });
        initMedia(location);
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
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.reset();
                    play.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    initMedia(offline.get(pos1).uri);
                    mediaPlayer.start();
                    play.setImageResource(R.drawable.ic_baseline_pause_24);
                } else {
                    mediaPlayer.reset();
                    initMedia(offline.get(pos1).uri);
                    //Toast.makeText(offlineplayer.this, "5 seconds delay pls wait", Toast.LENGTH_SHORT).show();
                    play.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                }

            }
        });
        nexttrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.reset();
                    play.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    pos1++;
                    if (pos1 < offline.size()) {
                        initMedia(offline.get(pos1).uri);
                        music.setText(offline.get(pos1).m_name);
                        initializeSeekbar();
                        mediaPlayer.start();
                        applelogo.startAnimation(AnimationUtils.loadAnimation(offlineplayer.this, R.anim.rotateimg));
                        play.setImageResource(R.drawable.ic_baseline_pause_24);
                    } else {
                        Toast.makeText(offlineplayer.this, "End of list", Toast.LENGTH_SHORT).show();
                        offlineplayer.this.finish();
                    }
                } else {
                    mediaPlayer.reset();
                    play.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    pos1++;
                    if (pos1 < offline.size()) {
                        initMedia(offline.get(pos1).uri);
                        music.setText(offline.get(pos1).m_name);
                        initializeSeekbar();
                        mediaPlayer.start();
                        applelogo.startAnimation(AnimationUtils.loadAnimation(offlineplayer.this, R.anim.rotateimg));
                        play.setImageResource(R.drawable.ic_baseline_pause_24);
                    } else {
                        Toast.makeText(offlineplayer.this, "End of list", Toast.LENGTH_SHORT).show();
                        offlineplayer.this.finish();
                    }
                }

            }
        });
        previoustrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.reset();
                    play.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    pos1--;
                    if (pos1 > -1) {
                        initMedia(offline.get(pos1).uri);

                        initializeSeekbar();
                        mediaPlayer.start();
                        applelogo.startAnimation(AnimationUtils.loadAnimation(offlineplayer.this, R.anim.rotateimg));
                        music.setText(offline.get(pos1).m_name);
                        play.setImageResource(R.drawable.ic_baseline_pause_24);
                    } else {
                        Toast.makeText(offlineplayer.this, "End of list", Toast.LENGTH_SHORT).show();
                        offlineplayer.this.finish();
                    }
                } else {
                    mediaPlayer.reset();
                    play.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    pos1--;
                    if (pos1 > -1) {
                        initMedia(offline.get(pos1).uri);
                        initializeSeekbar();
                        mediaPlayer.start();
                        applelogo.startAnimation(AnimationUtils.loadAnimation(offlineplayer.this, R.anim.rotateimg));
                        music.setText(offline.get(pos1).m_name);
                        play.setImageResource(R.drawable.ic_baseline_pause_24);
                    } else {
                        Toast.makeText(offlineplayer.this, "End of list", Toast.LENGTH_SHORT).show();
                        offlineplayer.this.finish();
                    }
                }

            }
        });
    }

    private void initMedia(Uri loc) {
        mediaPlayer = MediaPlayer.create(this, loc);
        maxdur.setText(getmaxDur());
    }

    private String getmaxDur() {
        long seconds = mediaPlayer.getDuration() / 1000;
        long s = seconds % 60;
        long m = (seconds / 60) % 60;
        long h = (seconds / (60 * 60)) % 24;
        return String.format("%d:%02d:%02d", h, m, s);
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

    }
}