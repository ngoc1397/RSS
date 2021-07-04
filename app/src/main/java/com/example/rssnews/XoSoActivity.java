package com.example.rssnews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.material.tabs.TabLayout;

public class XoSoActivity extends AppCompatActivity {

    ViewPager viewPager;
    Toolbar toolbar;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xo_so);
        AnhXa();
        actionToolBar();
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                toolbar.setTitle("Xổ số " + tab.getText());
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    void AnhXa(){
        viewPager = findViewById(R.id.viewXoSo);
        toolbar = findViewById(R.id.toolBarXoSo);
        tabLayout = findViewById(R.id.TabLayoutXoSo);
    }
    private class PagerAdapter extends FragmentPagerAdapter{
        private int numOfTabs;
        public PagerAdapter(@NonNull FragmentManager fm,int numOfTabs) {
            super(fm);
            this.numOfTabs = numOfTabs;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    FragmentXSMN fragmentXSMN = new FragmentXSMN();
                    return fragmentXSMN;
                case 1:
                    FragmentXSMT fragmentXSMT = new FragmentXSMT();
                    return fragmentXSMT;
                case 2:
                    FragmentXSMB fragmentXSMB = new FragmentXSMB();
                    return fragmentXSMB;
                case 4:
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return numOfTabs;
        }
    }
    private void actionToolBar() {
        setSupportActionBar(toolbar);
    }
}