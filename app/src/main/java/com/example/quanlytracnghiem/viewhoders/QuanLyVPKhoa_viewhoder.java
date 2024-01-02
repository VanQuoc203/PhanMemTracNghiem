package com.example.quanlytracnghiem.viewhoders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlytracnghiem.R;

public class QuanLyVPKhoaViewholder extends RecyclerView.ViewHolder {
    public TextView tvMaKhoa , tvTenKhoa , tvMoTa ;
    public ImageView ivImages;
    public QuanLyVPKhoaViewholder(@NonNull View itemView) {
        super(itemView);
        tvMaKhoa = itemView.findViewById(R.id.tvMaKhoa);
        tvTenKhoa = itemView.findViewById(R.id.tvTenKhoa);
        tvMoTa = itemView.findViewById(R.id.tvMoTaKhoa);
        ivImages = itemView.findViewById(R.id.ivImages);

    }
}
