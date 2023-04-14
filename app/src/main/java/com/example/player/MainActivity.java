package com.example.player;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener,MediaPlayer.OnCompletionListener {

 private final String DATA_STREAM = "https://mp3melodii.ru/files_site_02/001/veselaya_melodiya_iz_peredachi_kalambur_derevnya_durakov.mp3";
    private final String DATA_SD = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC) + "/music.mp3";
 private MediaPlayer mediaPlayer;
 private AudioManager audioManager;
 private Toast toast;
 private TextView textOut;
 private Switch switchLoop;
 private String nameAudio= "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textOut = findViewById(R.id.textOut);
        switchLoop = findViewById(R.id.switchLoop);

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);


        switchLoop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mediaPlayer != null) {
                    mediaPlayer.setLooping(isChecked);
                }
            }
        });
    }
    public void OnClickSource(View view){
       try {


           switch (view.getId()) {
               case R.id.btnStream:
                  toast = Toast.makeText(this, "Запущен поток аудио", Toast.LENGTH_LONG);
                    toast.show();
                   mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(DATA_STREAM);
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.setOnPreparedListener(this);
                    mediaPlayer.prepareAsync();
                    nameAudio ="КРУТАЯ ПЕСНЯ";
               break;
               case R.id.btnRAW:
                toast=Toast.makeText(this, "запущен аудио-файл с памяти телефона", Toast.LENGTH_LONG);
                toast.show();

                mediaPlayer=MediaPlayer.create(this,R.raw.bumblebee);
                mediaPlayer.start();
                nameAudio= "Римский-Корсаков - Полет шмеля";
                   break;
               
           }
       }catch (IOException e){
           e.printStackTrace();
       }
    if(mediaPlayer==null) return;
    mediaPlayer.setLooping(switchLoop.isChecked());

    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
       mediaPlayer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer(){
        if(mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer= null;

        }
    }
}