package com.example.quanlytracnghiem.viewhoders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlytracnghiem.R;

public class QuanLyVPKhoa_viewhoder extends RecyclerView.ViewHolder {
    public TextView tvDiaChi , tvTenKhoa , tvMoTa ;
    public ImageView ivImages;
    public QuanLyVPKhoa_viewhoder(@NonNull View itemView) {
        super(itemView);
        tvDiaChi = itemView.findViewById(R.id.tvDiaChi);
        tvTenKhoa = itemView.findViewById(R.id.tvTenKhoa);
        tvMoTa = itemView.findViewById(R.id.tvMoTa);
        ivImages = itemView.findViewById(R.id.ivImages);

    }
}
