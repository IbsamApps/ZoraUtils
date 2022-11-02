package com.samydev.ibsamutils;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.ibsamapps.zorautils.ZoraUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ZoraUtils.checkInternet(this);
    }

    public void rateUs(View view) {
        ZoraUtils.ratingApp(this);
    }

    @Override
    public void onBackPressed() {
        ZoraUtils.exitApp(this);
    }

    public void share(View view) {
        ZoraUtils.shareApp(this);
    }

    public void privacyOut(View view) {
        ZoraUtils.privacyOutApp(this, "https://google.com");
    }

    public void privacyIn(View view) {
        ZoraUtils.privacyInApp(this, "https://google.com");
    }

    public void exitApp(View view) {
        ZoraUtils.exitApp(this);
    }
}