package com.example.rssnews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class DanhMuc extends AppCompatActivity {

    GridView lstDanhMuc;
    ArrayList<DanhMucTinTuc> danhMucTinTucArrayList;
    DanhMucTinAdapter adapter;
    ImageButton btnTrangChu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_muc);
        AnhXa();
        lstDanhMuc.setAdapter(adapter);
        btnTrangChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    void AnhXa(){
        btnTrangChu = (ImageButton) findViewById(R.id.btnTrangChu);
        danhMucTinTucArrayList = new ArrayList<>();
        danhMucTinTucArrayList.add(new DanhMucTinTuc("Thế Giới","https://vnexpress.net/rss/the-gioi.rss",R.drawable.world_news));
        danhMucTinTucArrayList.add(new DanhMucTinTuc("Khoa Học","https://vnexpress.net/rss/khoa-hoc.rss",R.drawable.science_news));
        danhMucTinTucArrayList.add(new DanhMucTinTuc("Giải Trí","https://vnexpress.net/rss/giai-tri.rss",R.drawable.entertaiment_news));
        danhMucTinTucArrayList.add(new DanhMucTinTuc("Thể Thao","https://vnexpress.net/rss/the-thao.rss",R.drawable.sport_news));
        danhMucTinTucArrayList.add(new DanhMucTinTuc("Giáo Dục","https://vnexpress.net/rss/giao-duc.rss",R.drawable.education_news));
        danhMucTinTucArrayList.add(new DanhMucTinTuc("Pháp Luật","https://vnexpress.net/rss/phap-luat.rss",R.drawable.law_news));
        danhMucTinTucArrayList.add(new DanhMucTinTuc("Sức Khỏe","https://vnexpress.net/rss/suc-khoe.rss",R.drawable.helth_news));
        danhMucTinTucArrayList.add(new DanhMucTinTuc("Du Lịch","https://vnexpress.net/rss/du-lich.rss",R.drawable.travel_news));
        lstDanhMuc = (GridView) findViewById(R.id.lstDanhMucTin);
        adapter = new DanhMucTinAdapter(R.layout.danhmuc_layout,danhMucTinTucArrayList,getApplicationContext());
    }
}