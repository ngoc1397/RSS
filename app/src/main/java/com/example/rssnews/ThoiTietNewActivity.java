package com.example.rssnews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ThoiTietNewActivity extends AppCompatActivity {
    EditText edtSearch;
    Button btnSearch;
    TextView txtName,txtCountry,txtTemp,txtStatus,txtHumidity,txtCloud,txtWind,txtDay;
    ImageView imgIcon;
    String City = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thoi_tiet_new);
        Anhxa();
        GetCurrentWeatherData("Bienhoa");
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = edtSearch.getText().toString();
                if (city.equals(""))
                {
                    City = "Bienhoa";
                    GetCurrentWeatherData(City);
                }else {
                    City = city;
                    GetCurrentWeatherData(City);
                }
                GetCurrentWeatherData(city);
            }
        });
    }
    public void GetCurrentWeatherData(String data)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(ThoiTietNewActivity.this);
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+ data +"&units=metric&appid=0c8956bff6a6a8fc40a91a828bc19042";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String day = jsonObject.getString("dt");
                            String name = jsonObject.getString("name");
                            txtName.setText("Thành phố :"+ name);

                            long l = Long.valueOf(day);
                            //Chuyen thoi gian sang mili giay
                            Date date = new Date(l*1000L);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH-mm-ss");
                            String Day = simpleDateFormat.format(date);
                            txtDay.setText(Day);
                            JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                            JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                            String status = jsonObjectWeather.getString("main");
                            String icon = jsonObjectWeather.getString("icon");

                            //Picasso.with(ThoiTietNewActivity.this).load("http://openweathermap.org/img/wn/"+icon+".png").into(imgIcon);
                            txtStatus.setText(status);

                            JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                            String nhietdo = jsonObjectMain.getString("temp");
                            String doam = jsonObjectMain.getString("humidity");
                            Double a = Double.valueOf(nhietdo);
                            String Nhietdo = String.valueOf(a.intValue());
                            txtTemp.setText(Nhietdo+"°C");
                            txtHumidity.setText(doam+"%");

                            JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                            String gio = jsonObjectWind.getString("speed");
                            txtWind.setText(gio+"m/s");

                            JSONObject jsonObjectCloud = jsonObject.getJSONObject("clouds");
                            String may = jsonObjectCloud.getString("all");
                            txtCloud.setText(may+"%");

                            JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                            String country = jsonObjectSys.getString("country");
                            txtCountry.setText("Quốc gia :"+country);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }
    private void Anhxa() {
        edtSearch = (EditText) findViewById(R.id.edttextSearch);
        btnSearch = (Button) findViewById(R.id.btn_Search);
        txtName = (TextView) findViewById(R.id.txt_City);
        txtCountry = (TextView) findViewById(R.id.txt_Country);
        txtTemp = (TextView) findViewById(R.id.txtTemp);
        txtStatus = (TextView) findViewById(R.id.txt_Status);
        txtHumidity = (TextView) findViewById(R.id.txt_Humidity);
        txtCloud = (TextView) findViewById(R.id.txt_Cloud);
        txtWind = (TextView) findViewById(R.id.txt_Wind);
        txtDay = (TextView) findViewById(R.id.txt_Day);
        imgIcon = (ImageView) findViewById(R.id.img_Icon);
    }
}