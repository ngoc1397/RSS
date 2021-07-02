package com.example.rssnews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class DocBao extends AppCompatActivity {
    WebView webDocBao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_bao);
        webDocBao = (WebView) findViewById(R.id.webDocBao);
        Intent intent = getIntent();
        String link = intent.getStringExtra("link");
        webDocBao.getSettings().setJavaScriptEnabled(true);
        webDocBao.loadUrl(link);
        webDocBao.setWebViewClient(new WebViewClient());
    }
}