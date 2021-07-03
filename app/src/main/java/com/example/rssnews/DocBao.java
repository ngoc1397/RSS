package com.example.rssnews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class DocBao extends AppCompatActivity {
    WebView webDocBao;
    Button btnBack,btnSave,btnShare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_bao);
        AnhXa();
        Intent intent = getIntent();
        String link = intent.getStringExtra("link");
        webDocBao.getSettings().setJavaScriptEnabled(true);
        webDocBao.loadUrl(link);
        webDocBao.setWebViewClient(new WebViewClient());
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, link);
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }
        });
    }
    void AnhXa()
    {
        webDocBao = (WebView) findViewById(R.id.webDocBao);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnSave = (Button) findViewById(R.id.btnSaveNew);
        btnShare = (Button) findViewById(R.id.btnShareNew);
    }
}