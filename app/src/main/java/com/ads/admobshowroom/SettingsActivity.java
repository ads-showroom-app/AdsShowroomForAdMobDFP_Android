package com.ads.admobshowroom;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class SettingsActivity extends AppCompatActivity {
    private SettingsFragment settingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // [START screen_view_hit]
        GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
        // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
        Tracker mTracker = analytics.newTracker(R.xml.global_tracker);

        Log.i("screenName", this.getPackageName() + "." + this.getLocalClassName());
        mTracker.setScreenName(this.getPackageName() + "."+this.getLocalClassName());
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        //

        settingsFragment = new SettingsFragment();
        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, settingsFragment )
                .commit();

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
