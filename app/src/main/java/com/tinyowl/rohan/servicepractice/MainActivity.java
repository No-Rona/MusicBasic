package com.tinyowl.rohan.servicepractice;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {


    ViewGroup viewGroup;
    Button button1, button2, button3;
    MediaPlayer mediaPlayer = new MediaPlayer();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        viewGroup = (ViewGroup)findViewById(R.id.layout_main);
        button1 = (Button)findViewById(R.id.item1);
        button2 = (Button)findViewById(R.id.item2);
        button3 = (Button)findViewById(R.id.item3);

    }


    @Override
    protected void onPause() {
        mediaPlayer.pause();
        super.onPause();
    }

    @Override
    protected void onStart() {
        if (mediaPlayer != null)
            mediaPlayer.start();
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }

    @Override
    protected void onStop() {

        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void setAnimations(View view) {

        TransitionManager.beginDelayedTransition(viewGroup);

        ViewGroup.LayoutParams expansion = view.getLayoutParams();

        ViewGroup.LayoutParams param1 = button1.getLayoutParams(),
                param2 = button2.getLayoutParams(), param3 = button3.getLayoutParams();


        switch(view.getId()){

            case R.id.item1:
                param1.height = 500;
                param2.height = 144;
                param3.height = 144;
                Toast.makeText(this, "Playing "+
                        getResources().getString(R.string.Song_1_Name), Toast.LENGTH_SHORT).show();
                playMusic(1);
                break;

            case R.id.item2:
                param2.height = 500;
                param1.height = 144;
                param3.height = 144;
                Toast.makeText(this, "Playing "+
                        getResources().getString(R.string.Song_2_Name), Toast.LENGTH_SHORT).show();
                playMusic(2);
                break;

            case R.id.item3:
                param3.height = 500;
                param2.height = 144;
                param1.height = 144;
                Toast.makeText(this, "Playing "+
                        getResources().getString(R.string.Song_3_Name), Toast.LENGTH_SHORT).show();
                playMusic(3);
                break;

            default:
                Toast.makeText(this, "Unidentified Song", Toast.LENGTH_SHORT).show();
                break;
        }

        button1.setLayoutParams(param1);
        button2.setLayoutParams(param2);
        button3.setLayoutParams(param3);


    }

    public void playMusic(int id) {


        StreamSong streamSong = new StreamSong();

        switch (id) {
            case 1:
                streamSong.execute(getResources().getString(R.string.Song_1));
                break;
            case 2:
                streamSong.execute(getResources().getString(R.string.Song_2));
                break;
            case 3:
                streamSong.execute(getResources().getString(R.string.Song_3));
                break;
            default:
                streamSong.execute(getResources().getString(R.string.Song_1));
                break;
        }


    }

    private class StreamSong extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... params) {

            mediaPlayer.reset();
            try {
                mediaPlayer.setDataSource(params[0]);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mediaPlayer.start();

            return "";
        }
    }

}
