package com.example.rssnews;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class DocBaoRecycleAdapter extends RecyclerView.Adapter<DocBaoRecycleAdapter.ViewHolder> {
    public DocBaoRecycleAdapter(Context context, int layout, List<TinTuc> tinTucList) {
        this.context = context;
        this.layout = layout;
        this.tinTucList = tinTucList;
        database = new Database(context, "qltintuc.sqlite", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS TinTuc(Id INTEGER PRIMARY KEY AUTOINCREMENT, TieuDe VARCHAR(400), Link VARCHAR(500), NgayDang VARCHAR(50) ,AnhBia BLOB, Logo BLOB)");
    }

    Database database;
    private Context context;
    private int layout;
    private List<TinTuc> tinTucList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final TinTuc tinTuc = tinTucList.get(position);
        holder.txtTieude.setText(tinTuc.getTieuDe());
        holder.txtNgaydang.setText(tinTuc.getNgayDang());
        holder.imgLuuTin.setImageResource(R.drawable.ic_baseline_bookmark_add_24);
        String sql = "SELECT * FROM TinTuc";
        Cursor cursor = database.GetData(sql);
        while (cursor.moveToNext()) {
            String Link = cursor.getString(2);
            if (Link.equals(tinTuc.getLink())) {
                holder.imgLuuTin.setBackgroundResource(R.drawable.btn_daluu_background);
            }
        }
        holder.imgLuuTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kiemTraTrung(tinTuc.getLink())){
                    String sql = "DELETE FROM TinTuc WHERE Link = '"+tinTuc.getLink()+"'";
                    database.QueryData(sql);
                    holder.imgLuuTin.setBackgroundResource(R.drawable.danhmuc_background);
                }else {
                    database.ThemTinTuc(tinTuc.getTieuDe(), tinTuc.getLink(), tinTuc.getNgayDang(), tinTuc.getAnhBia(), tinTuc.getLogo());
                    holder.imgLuuTin.setBackgroundResource(R.drawable.btn_daluu_background);
                }
            }
        });
        Picasso.with(context).load(tinTuc.getAnhBia()).into(holder.imgAnhbia);
        Picasso.with(context).load(tinTuc.getLogo()).into(holder.imgAnhlogo);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DocBao.class);
                intent.putExtra("link", tinTuc.getLink());
                context.startActivity(intent);
            }
        });
    }
    boolean kiemTraTrung(String link){
        String sql = "SELECT * FROM TinTuc";
        Cursor cursor = database.GetData(sql);
        while (cursor.moveToNext()) {
            String Link = cursor.getString(2);
            if (Link.equals(link)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public int getItemCount() {
        return tinTucList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View itemView;
        public TextView txtTieude;
        public TextView txtNgaydang;
        public ImageView imgAnhbia;
        public ImageView imgAnhlogo;
        public ImageButton imgLuuTin;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            txtTieude = (TextView) itemView.findViewById(R.id.txtTieude);
            txtNgaydang = (TextView) itemView.findViewById(R.id.txtNgaydang);
            imgAnhbia = (ImageView) itemView.findViewById(R.id.imgAnhbia);
            imgAnhlogo = (ImageView) itemView.findViewById(R.id.imgAnhlogo);
            imgLuuTin = (ImageButton) itemView.findViewById(R.id.btnLuuTin);
        }
    }
}


