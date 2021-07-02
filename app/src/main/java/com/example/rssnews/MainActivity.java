package com.example.rssnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
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

public class MainActivity extends AppCompatActivity {

    ArrayList<TinTuc> tinTucArrayList;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    DocBaoRecycleAdapter docBaoRecycleAdapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        actionToolBar();
        new ReadRSS().execute("https://vnexpress.net/rss/tin-moi-nhat.rss");
    }
    private class ReadRSS extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            tinTucArrayList = new ArrayList<>();
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
                //tinTuc.setNgayDang(parser.getValue(element,"pubDate"));
                tinTuc.setLogo(logo);
                //tinTuc.setNgayDang(parseTwitterDate(parser.getValue(element,"pubDate")).toString());
                tinTucArrayList.add(tinTuc);
            }
            docBaoRecycleAdapter = new DocBaoRecycleAdapter(MainActivity.this,R.layout.tintuc_layout,tinTucArrayList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
            recyclerView.setAdapter(docBaoRecycleAdapter);
            recyclerView.setLayoutManager(linearLayoutManager);
        }
    }
    private void actionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_action_menu);
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