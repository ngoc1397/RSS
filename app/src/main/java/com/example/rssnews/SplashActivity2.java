package com.example.rssnews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class SplashActivity2 extends AppCompatActivity {

    ArrayList<TinTuc> tinTucArrayList;
    ImageView imgLogo;
    int delay = 1500;
    TextView txtLogo, txtSlogan;
    Animation top, bottom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);
        imgLogo = findViewById(R.id.splashIcon);
        txtLogo = findViewById(R.id.txtLogo);
        txtSlogan = findViewById(R.id.txtSlogan);
        top = AnimationUtils.loadAnimation(this, R.anim.top);
        bottom = AnimationUtils.loadAnimation(this, R.anim.bottom);
        imgLogo.setAnimation(top);
        txtSlogan.setAnimation(bottom);
        txtLogo.setAnimation(bottom);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isNetworkAvailable(getApplicationContext())){
                    new ReadRSS().execute("https://vnexpress.net/rss/tin-moi-nhat.rss");
                }
                else {
                    Toast.makeText(getApplicationContext(),"Không có kết nối mạng",Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        },delay);
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
                tinTuc.setNgayDang(parser.getValue(element, "pubDate"));
                tinTuc.setLogo(logo);
                //tinTuc.setNgayDang(parseTwitterDate(parser.getValue(element,"pubDate")).toString());
                tinTucArrayList.add(tinTuc);
            }
            Intent intent = new Intent(SplashActivity2.this,MainActivity.class);
            intent.putExtra("lst",tinTucArrayList);
            startActivity(intent);
        }
    }
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}