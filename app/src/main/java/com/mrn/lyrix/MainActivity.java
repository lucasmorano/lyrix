package com.mrn.lyrix;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private TextView title;
    private TextView lyrics;
    private String track;
    private String artist;
    private String album;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadContent();
        IntentFilter iF = new IntentFilter();

        iF.addAction("com.rdio.android.metachanged");
        iF.addAction("com.rdio.android.playstatechanged");
        registerReceiver(mReceiver, iF);
    }

    private void loadContent() {
        title = (TextView) findViewById(R.id.title);
        lyrics = (TextView) findViewById(R.id.lyrics);
        title.setText("Pause/Unpause or just Play a song!\n\t          So I can follow you!");
        lyrics.setText("");
        lyrics.setMovementMethod(new ScrollingMovementMethod());
        title.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(getApplicationContext(), "ROLACIIOOOOOOOOOOOOOOO", Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                track = intent.getStringExtra("track");
                artist = intent.getStringExtra("artist");
                album = intent.getStringExtra("album");
                if(track != null && album !=null && artist !=null){
                    updateContent(artist, track, album);
                }
            }
        };

    public void updateLyrics(String lyrics) {
        this.lyrics.setText(lyrics);
    }


    private void updateContent(String artist, String track, String album) {
        title.setText(artist + " - " + track);
        title.setTextSize(15);
        new Plyrics(getCurrentActivity()).execute(artist, track);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public MainActivity getCurrentActivity() {
        return MainActivity.this;
    }

    private class WidgetProvider extends AppWidgetProvider {

        @Override
        public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
            remoteViews.setTextViewText(R.id.title, title.getText());
            remoteViews.setTextViewText(R.id.desc, lyrics.getText());
            pushWidgetUpdate(context, remoteViews);
        }

        public void pushWidgetUpdate(Context context, RemoteViews remoteViews) {
            ComponentName myWidget = new ComponentName(context, WidgetProvider.class);
            AppWidgetManager manager = AppWidgetManager.getInstance(context);
            manager.updateAppWidget(myWidget, remoteViews);
        }
    }

}
