package com.example.rssnews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentXSMB extends Fragment {
    WebView webView;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xsmb,container,false);
        webView = view.findViewById(R.id.webXSMB);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://xosodaiphat.com/xsmb-xo-so-mien-bac.html");
        webView.setWebViewClient(new WebViewClient());
        return  view;
    }
}
