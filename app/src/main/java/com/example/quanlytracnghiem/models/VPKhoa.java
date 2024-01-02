package com.example.quanlytracnghiem.models;

public class VPKhoa {
    String maKhoa , tenKhoa , moTaKhoa =" " , diaChi;
    String imagesKhoa;

    public VPKhoa(String maKhoa, String tenKhoa, String moTaKhoa, String diaChi, String imagesKhoa) {
        this.maKhoa = maKhoa;
        this.tenKhoa = tenKhoa;
        this.moTaKhoa = moTaKhoa;
        this.diaChi = diaChi;
        this.imagesKhoa = imagesKhoa;
    }

    public VPKhoa() {
    }

    public String getMaKhoa() {
        return maKhoa;
    }

    public void setMaKhoa(String maKhoa) {
        this.maKhoa = maKhoa;
    }

    public String getTenKhoa() {
        return tenKhoa;
    }

    public void setTenKhoa(String tenKhoa) {
        this.tenKhoa = tenKhoa;
    }

    public String getMoTaKhoa() {
        return moTaKhoa;
    }

    public void setMoTaKhoa(String moTaKhoa) {
        this.moTaKhoa = moTaKhoa;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getImagesKhoa() {
        return imagesKhoa;
    }

    public void setImagesKhoa(String imagesKhoa) {
        this.imagesKhoa = imagesKhoa;
    }
}
