package com.example.rssnews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChonThanhPhoActivity extends AppCompatActivity {

    EditText edtChonTP;
    Button btnLuuTP;
    ArrayList<ThanhPho> lstTP;
    ListView listViewTP;
    ThanhPhoAdapter adapter;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_thanh_pho);
        AnhXa();
        btnLuuTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newCity= edtChonTP.getText().toString();
                Intent intent=new Intent(ChonThanhPhoActivity.this,ThoiTietActivity.class);
                intent.putExtra("City",newCity);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Key",newCity);
                editor.commit();
                startActivity(intent);
            }
        });
        edtChonTP.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return false;
            }
        });
        adapter = new ThanhPhoAdapter(R.layout.thanhpho_layout,lstTP,getApplicationContext());
        listViewTP.setAdapter(adapter);
        listViewTP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ThanhPho tp = (ThanhPho) adapter.getItem(position);
                Intent intent=new Intent(ChonThanhPhoActivity.this,ThoiTietActivity.class);
                intent.putExtra("City",tp.getKey());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Key",tp.getKey());
                editor.commit();
                startActivity(intent);
            }
        });
    }
    void AnhXa(){
        preferences = getApplicationContext().getSharedPreferences("TP",getApplicationContext().MODE_PRIVATE);
        edtChonTP = findViewById(R.id.txtChonThanhPho);
        btnLuuTP = findViewById(R.id.btnNhapThanhPho);
        listViewTP = findViewById(R.id.lstThanhPho);
        lstTP = new ArrayList<>();
        lstTP.add(new ThanhPho("Thanh pho Ho Chi Minh","TP.Hồ Chí Minh"));
        lstTP.add(new ThanhPho("Ha Noi","Hà Nội"));
        lstTP.add(new ThanhPho("Can Tho","TP.Cần Thơ"));
    }
}