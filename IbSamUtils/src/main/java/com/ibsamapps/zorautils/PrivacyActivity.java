package com.ibsamapps.zorautils;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

public class PrivacyActivity extends AppCompatActivity {

    WebView webView;
    RelativeLayout privacyPolicy;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);

        privacyPolicy = findViewById(R.id.privacyPolicy);
        privacyPolicy.setVisibility(View.VISIBLE);
        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                privacyPolicy.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }
        });
        webView.loadUrl(getIntent().getStringExtra("Url"));
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

    }

    public void close(View view) {
        finish();
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        } else {
            finish();
        }
    }
}