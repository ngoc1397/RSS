package com.example.rssnews;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class DocBaoRecycleAdapter extends RecyclerView.Adapter<DocBaoRecycleAdapter.ViewHolder> {
    public DocBaoRecycleAdapter(Context context, int layout, List<TinTuc> tinTucList) {
        this.context = context;
        this.layout = layout;
        this.tinTucList = tinTucList;
    }
    private Context context;
    private int layout;
    private List<TinTuc> tinTucList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final TinTuc tinTuc = tinTucList.get(position);
        holder.txtTieude.setText(tinTuc.getTieuDe());
        holder.txtNgaydang.setText(tinTuc.getNgayDang());
        Picasso.with(context).load(tinTuc.getAnhBia()).into(holder.imgAnhbia);
        Picasso.with(context).load(tinTuc.getLogo()).into(holder.imgAnhlogo);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DocBao.class);
                intent.putExtra("link",tinTuc.getLink());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tinTucList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private View itemView;
        public TextView txtTieude;
        public TextView txtNgaydang;
        public ImageView imgAnhbia;
        public ImageView imgAnhlogo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            txtTieude = (TextView) itemView.findViewById(R.id.txtTieude);
            txtNgaydang = (TextView) itemView.findViewById(R.id.txtNgaydang);
            imgAnhbia = (ImageView) itemView.findViewById(R.id.imgAnhbia);
            imgAnhlogo = (ImageView) itemView.findViewById(R.id.imgAnhlogo);
        }
    }
}


