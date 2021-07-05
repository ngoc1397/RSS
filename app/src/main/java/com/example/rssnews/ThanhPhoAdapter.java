package com.example.rssnews;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ThanhPhoAdapter extends BaseAdapter {
    int layout;
    ArrayList<ThanhPho> thanhPhoArrayList;
    Context context;

    public ThanhPhoAdapter(int layout, ArrayList<ThanhPho> thanhPhoArrayList, Context context) {
        this.layout = layout;
        this.thanhPhoArrayList = thanhPhoArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return thanhPhoArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return thanhPhoArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewDanhMuc;
        if(convertView == null){
            viewDanhMuc = View.inflate(context,layout,null);
        }
        else {
            viewDanhMuc = convertView;
        }
        ThanhPho thanhPho = thanhPhoArrayList.get(position);
        ((TextView)viewDanhMuc.findViewById(R.id.txtThanhPhoAdapter)).setText(thanhPho.getName());
        return viewDanhMuc;
    }
}
