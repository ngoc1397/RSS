package com.example.rssnews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class DocBao extends AppCompatActivity {
    WebView webDocBao;
    Button btnBack,btnSave,btnShare;
    Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_bao);
        AnhXa();
        database = new Database(DocBao.this, "qltintuc.sqlite", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS TinTuc(Id INTEGER PRIMARY KEY AUTOINCREMENT, TieuDe VARCHAR(400), Link VARCHAR(500), NgayDang VARCHAR(50) ,AnhBia BLOB, Logo BLOB)");
        Intent intent = getIntent();
        TinTuc tt = (TinTuc) intent.getSerializableExtra("tintuc");
        String link = intent.getStringExtra("link");
        webDocBao.getSettings().setJavaScriptEnabled(true);
        webDocBao.loadUrl(link);
        webDocBao.setWebViewClient(new WebViewClient());
        if(kiemTraTrung(link)){
            btnSave.setBackgroundColor(Color.parseColor("#E91E63"));
            btnSave.setTextColor(Color.parseColor("#FFFFFF"));
        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kiemTraTrung(tt.getLink())){
                    String sql = "DELETE FROM TinTuc WHERE Link = '"+tt.getLink()+"'";
                    database.QueryData(sql);
                    btnSave.setTextColor(Color.parseColor("#000000"));
                    btnSave.setBackgroundTintMode(PorterDuff.Mode.SCREEN);
                }else {
                    database.ThemTinTuc(tt.getTieuDe(), tt.getLink(), tt.getNgayDang(), tt.getAnhBia(), tt.getLogo());
                    btnSave.setBackgroundColor(Color.parseColor("#E91E63"));
                    btnSave.setTextColor(Color.parseColor("#FFFFFF"));
                }
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
    boolean kiemTraTrung(String link){
        String sql = "SELECT * FROM TinTuc";
        Cursor cursor = database.GetData(sql);
        while (cursor.moveToNext()) {
            String Link = cursor.getString(2);
            if (Link.equals(link)) {
                return true;
            }
        }
        return false;
    }
    void AnhXa()
    {
        webDocBao = (WebView) findViewById(R.id.webDocBao);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnSave = (Button) findViewById(R.id.btnSaveNew);
        btnShare = (Button) findViewById(R.id.btnShareNew);
    }
}