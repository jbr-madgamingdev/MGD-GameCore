package io.madgamingdev.abslots.ui;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import io.madgamingdev.abslots.R;
import io.madgamingdev.abslots.libs.NetworkLibrary;

public class SplashUI extends AppCompatActivity {

    private NetworkLibrary networkReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        supportRequestWindowFeature(1);
        setContentView(R.layout.activity_splash_ui);

        networkReceiver = new NetworkLibrary();

        VideoView mgdSplash = (VideoView) findViewById(R.id.mgdSplash);
        Uri splashFile = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.mgd_intro);
        mgdSplash.setVideoURI(splashFile);
        mgdSplash.start();

        mgdSplash.setOnCompletionListener(mediaPlayer -> {

            mgdSplash.stopPlayback();
            mgdSplash.setVisibility(View.GONE);

            if (NetworkLibrary.isNetworkAvailable(this)) {
                Intent i = new Intent(this, GameUI.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            } else {
                Intent i = new Intent(this, NotifyUI.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("notifyType", 0);
                startActivity(i);
                finish();
            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(networkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(networkReceiver);
    }
}