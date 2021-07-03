package com.example.rssnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;

public class TinDaLuuActivity extends AppCompatActivity {

    Database database;
    ArrayList<TinTuc> tinTucArrayList;
    DocBaoRecycleAdapter docBaoRecycleAdapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tin_da_luu);
        database = new Database(this, "qltintuc.sqlite", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS TinTuc(Id INTEGER PRIMARY KEY AUTOINCREMENT, TieuDe VARCHAR(400), Link VARCHAR(500), NgayDang VARCHAR(50) ,AnhBia VARCHAR(500), Logo VARCHAR(500))");
        AnhXa();
        docBaoRecycleAdapter = new DocBaoRecycleAdapter(TinDaLuuActivity.this,R.layout.tintuc_layout,tinTucArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TinDaLuuActivity.this);
        recyclerView.setAdapter(docBaoRecycleAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
    void AnhXa(){
        recyclerView = findViewById(R.id.lstTinDaLuu);
        tinTucArrayList = new ArrayList<>();
        String sql = "SELECT * FROM TinTuc";
        Cursor cursor = database.GetData(sql);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String TieuDe = cursor.getString(1);
            String Link = cursor.getString(2);
            String NgayDang = cursor.getString(3);
            String AnhBia = cursor.getString(4);
            String Logo = cursor.getString(5);
            TinTuc tt = new TinTuc(TieuDe,Link,AnhBia,NgayDang,Logo);
            tinTucArrayList.add(tt);
        }
    }
}