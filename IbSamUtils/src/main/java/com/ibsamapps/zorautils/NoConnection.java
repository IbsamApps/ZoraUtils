package com.ibsamapps.zorautils;

import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class NoConnection extends AppCompatActivity {

    LinearLayout tryAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_connection);
        if (ZoraUtils.isOnline(NoConnection.this)){
            finish();
        }
        tryAgain = findViewById(R.id.try_again);
        tryAgain.setOnClickListener(view -> recreate());
    }
}