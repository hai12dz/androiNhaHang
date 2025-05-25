package com.example.myapplication;

public class NhaHang {

    private int id;
    private String tenNhaHang;
    private String diaChi;
    private int soPhieuBinhChon;
    private float diemTrungBinh; // true: thu (income), false: chi (expense)

    public NhaHang(int id, String tenNhaHang, String diaChi, int soPhieuBinhChon, float diemTrungBinh) {
        this.id = id;
        this.tenNhaHang = tenNhaHang;
        this.diaChi = diaChi;
        this.soPhieuBinhChon = soPhieuBinhChon;
        this.diemTrungBinh = diemTrungBinh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenNhaHang() {
        return tenNhaHang;
    }

    public void setTenNhaHang(String tenNhaHang) {
        this.tenNhaHang = tenNhaHang;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public float getDiemTrungBinh() {
        return diemTrungBinh;
    }

    public void setDiemTrungBinh(float diemTrungBinh) {
        this.diemTrungBinh = diemTrungBinh;
    }

    public int getSoPhieuBinhChon() {
        return soPhieuBinhChon;
    }

    public void setSoPhieuBinhChon(int soPhieuBinhChon) {
        this.soPhieuBinhChon = soPhieuBinhChon;
    }
}
