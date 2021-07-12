package com.example.rssnews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rssnews.api_country.ApiUtilities;
import com.example.rssnews.api_country.CountryData;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CovidActivity extends AppCompatActivity {
    private TextView totalConfirm,totalActive,totalRecovered,totalDeath,totalTests;
    private TextView todayConfirm,todayRecovered,todayDeath,datetime;
    private PieChart pieChart;
    private List<CountryData> list;
    String country = "Vietnam";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid);
        list = new ArrayList<>();
        if (getIntent().getStringExtra("country") != null)
        {
            country = getIntent().getStringExtra("country");
        }
        Anhxa();
        TextView cname = findViewById(R.id.cname);
        cname.setText(country);
        cname.setOnClickListener(v ->
                startActivity(new Intent(CovidActivity.this,CountryCovidActivity.class)));
        getThongTin(country);

    }

    private void setText(String updated) {
        DateFormat format = new SimpleDateFormat("MMM dd, yyyy");

        long miliseconds = Long.parseLong(updated);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(miliseconds);
        datetime.setText("Updated at "+format.format(calendar.getTime()));
    }

    private void Anhxa() {
        totalConfirm = findViewById(R.id.totalConfirm);
        totalActive = findViewById(R.id.totalActive);
        totalRecovered = findViewById(R.id.totalRecovered);
        totalDeath = findViewById(R.id.totalDeath);
        totalTests = findViewById(R.id.totalTests);
        todayConfirm = findViewById(R.id.todayConfirm);
        todayRecovered = findViewById(R.id.todayRecovered);
        todayDeath = findViewById(R.id.todayDeath);
        pieChart = findViewById(R.id.pieChart);
        datetime = findViewById(R.id.date);

    }

    private void getThongTin(String country)
    {
        ApiUtilities.getApiInterface().getCountryData().enqueue(new Callback<List<CountryData>>() {
            @Override
            public void onResponse(Call<List<CountryData>> call, Response<List<CountryData>> response) {
                list.addAll(response.body());
                for (int i =0;i<list.size();i++)
                {
                    if (list.get(i).getCountry().equals(country))
                    {
                        int confirm = Integer.parseInt(list.get(i).getCases());
                        int active = Integer.parseInt(list.get(i).getActive());
                        int recovered = Integer.parseInt(list.get(i).getRecovered());
                        int death = Integer.parseInt(list.get(i).getDeaths());

                        totalActive.setText(NumberFormat.getInstance().format(active));
                        totalConfirm.setText(NumberFormat.getInstance().format(confirm));
                        totalRecovered.setText(NumberFormat.getInstance().format(recovered));
                        totalDeath.setText(NumberFormat.getInstance().format(death));
                        totalTests.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTests())));

                        todayDeath.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayDeaths())));
                        todayConfirm.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayCases())));
                        todayRecovered.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayRecovered())));

                        setText(list.get(i).getUpdated());

                        pieChart.addPieSlice(new PieModel("Confirm",confirm,getResources().getColor(R.color.yellow)));
                        pieChart.addPieSlice(new PieModel("Active",active,getResources().getColor(R.color.blue)));
                        pieChart.addPieSlice(new PieModel("Recovered",recovered,getResources().getColor(R.color.green)));
                        pieChart.addPieSlice(new PieModel("Death",death,getResources().getColor(R.color.red)));
                        pieChart.startAnimation();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CountryData>> call, Throwable t) {
                Toast.makeText(CovidActivity.this,"Error :"+ t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}