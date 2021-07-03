package com.example.rssnews;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DanhMucTinAdapter extends BaseAdapter {
    int layout;
    ArrayList<DanhMucTinTuc> tinTucArrayList;
    Context context;

    public DanhMucTinAdapter(int layout, ArrayList<DanhMucTinTuc> tinTucArrayList, Context context) {
        this.layout = layout;
        this.tinTucArrayList = tinTucArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return tinTucArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
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
        DanhMucTinTuc dm = tinTucArrayList.get(position);
        ((TextView)viewDanhMuc.findViewById(R.id.txtTenDanhMuc)).setText(dm.getTenDanhMuc());
        ((ImageView) viewDanhMuc.findViewById(R.id.imgDanhMuc)).setImageResource(dm.getImg());
        viewDanhMuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),TinTheoDanhMucActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("link",dm.getLink());
                context.startActivity(intent);
            }
        });
        return viewDanhMuc;
    }
}
