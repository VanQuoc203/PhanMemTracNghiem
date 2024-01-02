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
import com.example.quanlytracnghiem.viewhoders.QuanLyVPKhoa_viewhoder;

import java.util.ArrayList;
import java.util.List;

public class QuanLyVPKhoa_adapter extends RecyclerView.Adapter<QuanLyVPKhoa_viewhoder> {
    private Context context;
    private ArrayList<VPKhoa> datalist;

    public QuanLyVPKhoa_adapter(Context context, ArrayList<VPKhoa> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public QuanLyVPKhoa_viewhoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_quanlykhoa_layout, parent, false);
        return new QuanLyVPKhoa_viewhoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuanLyVPKhoa_viewhoder holder, int position) {
        VPKhoa vpKhoa = datalist.get(position);
        holder.tvDiaChi.setText("Địa chỉ  "+vpKhoa.getDiaChi());
        holder.tvTenKhoa.setText("Tên khoa "+vpKhoa.getTenKhoa());
        holder.tvMoTa.setText(vpKhoa.getMoTaKhoa());
        Glide.with(context).load(vpKhoa.getImagesKhoa()).into(holder.ivImages);
    }
    public void SearchData(ArrayList<VPKhoa> search){
        datalist = search;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }
}
