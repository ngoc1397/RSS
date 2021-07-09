package com.example.rssnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

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

public class SplashActivity extends AppCompatActivity {

    ArrayList<TinTuc> tinTucArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                tinTuc.setNgayDang(parser.getValue(element, "pubDate"));
                tinTuc.setLogo(logo);
                //tinTuc.setNgayDang(parseTwitterDate(parser.getValue(element,"pubDate")).toString());
                tinTucArrayList.add(tinTuc);
            }
            Intent intent = new Intent(SplashActivity.this,MainActivity.class);
            intent.putExtra("lst",tinTucArrayList);
            startActivity(intent);
        }
    }
}