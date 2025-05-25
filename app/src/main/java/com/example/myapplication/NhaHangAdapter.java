package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class NhaHangAdapter extends BaseAdapter {
    private Context context;
    private List<NhaHang> list;

    public NhaHangAdapter(Context context, List<NhaHang> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() { return list.size(); }

    @Override
    public Object getItem(int i) { return list.get(i); }

    @Override
    public long getItemId(int i) { return list.get(i).getId(); }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);

        NhaHang gd = list.get(i);
        TextView tvTenNhaHang = convertView.findViewById(R.id.tenNhaHang);
        TextView tvDiemTrungBinh = convertView.findViewById(R.id.diemTrungBinh);
        TextView tvDiaChiNhaHang = convertView.findViewById(R.id.diaChiNhaHang);

        String tenNhaHang, diemTrungBinh,diaChiNhaHang;

        tenNhaHang =  gd.getTenNhaHang();
        diemTrungBinh = String.valueOf(gd.getDiemTrungBinh());
        diaChiNhaHang=gd.getDiaChi();

        tvTenNhaHang.setText(tenNhaHang);
        tvDiemTrungBinh.setText(diemTrungBinh);
        tvDiaChiNhaHang.setText(diaChiNhaHang);
        return convertView;
    }

    private String formatDate(String date) {
        // Đổi yyyy-MM-dd thành dd/MM/yyyy nếu cần
        if (date.contains("-")) {
            String[] arr = date.split("-");
            if (arr.length == 3) return arr[2] + "/" + arr[1] + "/" + arr[0];
        }
        return date;
    }
}
