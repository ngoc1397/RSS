package com.example.rssnews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<TinTuc> tinTucArrayList;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    DocBaoRecycleAdapter docBaoRecycleAdapter;
    RecyclerView recyclerView;
    Database database;
    SwipeRefreshLayout refreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new Database(this, "qltintuc.sqlite", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS TinTuc(Id INTEGER PRIMARY KEY AUTOINCREMENT, TieuDe VARCHAR(400), Link VARCHAR(500), NgayDang VARCHAR(50) ,AnhBia VARCHAR(500), Logo VARCHAR(500))");
        AnhXa();
        actionToolBar();
        setNavigationViewListener();
        Intent intent = getIntent();
        tinTucArrayList = new ArrayList<>();
        tinTucArrayList = (ArrayList<TinTuc>) intent.getSerializableExtra("lst");
        docBaoRecycleAdapter = new DocBaoRecycleAdapter(MainActivity.this,R.layout.tintuc_layout,tinTucArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setAdapter(docBaoRecycleAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        refreshLayout = findViewById(R.id.swipeLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tinTucArrayList.clear();
                new ReadRSS().execute("https://vnexpress.net/rss/tin-moi-nhat.rss");
                refreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
    // Lấy sự kiện click on Item navigation view
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_item_danhmuc:
                if(isNetworkAvailable(MainActivity.this)){
                    Intent intent = new Intent(MainActivity.this,DanhMuc.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(MainActivity.this,"Không có kết nối mạng",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.nav_item_tin_da_luu:
                if(isNetworkAvailable(MainActivity.this)){
                    Intent intent1 = new Intent(MainActivity.this,TinDaLuuActivity.class);
                    startActivity(intent1);
                }else {
                    Toast.makeText(MainActivity.this,"Không có kết nối mạng",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.nav_item_xo_so:
                if(isNetworkAvailable(MainActivity.this)){
                    Intent intent2 = new Intent(MainActivity.this,XoSoActivity.class);
                    startActivity(intent2);
                }else {
                    Toast.makeText(MainActivity.this,"Không có kết nối mạng",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.nav_item_thoi_tiet:
                if(isNetworkAvailable(MainActivity.this)){
                    Intent intent3 = new Intent(MainActivity.this,ThoiTietActivity.class);
                    startActivity(intent3);
                }else {
                    Toast.makeText(MainActivity.this,"Không có kết nối mạng",Toast.LENGTH_LONG).show();
                }
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    private void setNavigationViewListener() {
        navigationView.setNavigationItemSelectedListener(this);
    }

    private class ReadRSS extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line);
                }
                bufferedReader.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            XMLDOMParser parser = new XMLDOMParser();
            Document document = parser.getDocument(s);
            String logo = "";
            NodeList nodeListLogo = document.getElementsByTagName("image");
            NodeList nodeList = document.getElementsByTagName("item");
            NodeList nodeListMieuTa = document.getElementsByTagName("description");
            for (int j = 0; j < nodeListLogo.getLength(); j++) {
                Element element = (Element) nodeListLogo.item(j);
                logo = parser.getValue(element, "url");
            }
            for (int i = 0; i < nodeList.getLength(); i++) {
                TinTuc tinTuc = new TinTuc();
                String cdata = nodeListMieuTa.item(i + 1).getTextContent();
                Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
                Matcher matcher = p.matcher(cdata);
                Element element = (Element) nodeList.item(i);
                if (matcher.find()) {
                    tinTuc.setAnhBia(matcher.group(1));
                }
                tinTuc.setTieuDe(parser.getValue(element, "title"));
                tinTuc.setLink(parser.getValue(element, "link"));
                tinTuc.setNgayDang(parser.getValue(element,"pubDate"));
                tinTuc.setLogo(logo);
                //tinTuc.setNgayDang(parseTwitterDate(parser.getValue(element,"pubDate")).toString());
                tinTucArrayList.add(tinTuc);
            }
            docBaoRecycleAdapter.notifyDataSetChanged();
        }
    }
    private void actionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_action_menu_light);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    public void AnhXa() {
        toolbar = findViewById(R.id.toolBar);
        drawerLayout = findViewById(R.id.drawerLayout);
        recyclerView = findViewById(R.id.lstTrangchu);
        navigationView = findViewById(R.id.navigationMenu);
    }

}