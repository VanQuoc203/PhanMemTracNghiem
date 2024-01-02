package com.example.quanlytracnghiem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quanlytracnghiem.R;
import com.example.quanlytracnghiem.models.VPKhoa;
import com.example.quanlytracnghiem.viewhoders.QuanLyVPKhoaViewholder;

import java.util.ArrayList;
import java.util.List;

public class QuanLyVPKhoaAdapter extends RecyclerView.Adapter<QuanLyVPKhoaViewholder> {
    private Context context;
    private ArrayList<VPKhoa> datalist;

    public QuanLyVPKhoaAdapter(Context context, ArrayList<VPKhoa> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public QuanLyVPKhoaViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_quanlykhoa_layout, parent, false);
        return new QuanLyVPKhoaViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuanLyVPKhoaViewholder holder, int position) {
        VPKhoa vpKhoa = datalist.get(position);
        holder.tvMaKhoa.setText(vpKhoa.getMaKhoa());
        holder.tvTenKhoa.setText(vpKhoa.getTenKhoa());
        holder.tvMoTa.setText(vpKhoa.getMoTaKhoa());
        Glide.with(context).load(vpKhoa.getImagesKhoa()).into(holder.ivImages);
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }
}
