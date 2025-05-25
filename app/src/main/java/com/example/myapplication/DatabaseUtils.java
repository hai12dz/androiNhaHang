package com.example.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseUtils {
    private static final String TAG = "DatabaseUtils";
    
    public static List<NhaHang> getAllNhaHang(DatabaseHelper dbHelper) {
        List<NhaHang> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        
        try {
            Log.d(TAG, "Getting all restaurants from database");
            Cursor c = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME + 
                " ORDER BY " + DatabaseHelper.COL_DIEMTRUNGBINH + " DESC", null);
                    
            Log.d(TAG, "Query executed. Cursor has " + c.getCount() + " rows");
            
            if (c.getCount() == 0) {
                // If no data, add some sample data directly
                Log.d(TAG, "No data found, inserting sample data directly");
                SQLiteDatabase writeDb = dbHelper.getWritableDatabase();
                ContentValues cv = new ContentValues();
                
                cv.put(DatabaseHelper.COL_TENNHAHANG, "Emergency Restaurant");
                cv.put(DatabaseHelper.COL_DIACHI, "Emergency Address");
                cv.put(DatabaseHelper.COL_SOPHIEUBINHCHON, 10);
                cv.put(DatabaseHelper.COL_DIEMTRUNGBINH, 9.0);
                writeDb.insert(DatabaseHelper.TABLE_NAME, null, cv);
                
                // Query again
                c.close();
                c = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME, null);
                Log.d(TAG, "After inserting emergency data, cursor has " + c.getCount() + " rows");
            }
            
            int idIdx = c.getColumnIndex(DatabaseHelper.COL_ID);
            int tenNhaHangIdx = c.getColumnIndex(DatabaseHelper.COL_TENNHAHANG);
            int diaChiIdx = c.getColumnIndex(DatabaseHelper.COL_DIACHI);
            int soPhieuBinhChonIdx = c.getColumnIndex(DatabaseHelper.COL_SOPHIEUBINHCHON);
            int diemTrungBinhIdx = c.getColumnIndex(DatabaseHelper.COL_DIEMTRUNGBINH);
            
            Log.d(TAG, "Column indices - ID: " + idIdx + ", tenNhaHang: " + tenNhaHangIdx + 
                ", diaChi: " + diaChiIdx + ", soPhieu: " + soPhieuBinhChonIdx + ", diem: " + diemTrungBinhIdx);
            
            while (c.moveToNext()) {
                if (idIdx < 0 || tenNhaHangIdx < 0 || diaChiIdx < 0 || soPhieuBinhChonIdx < 0 || diemTrungBinhIdx < 0) {
                    Log.e(TAG, "Column indices invalid, skipping row");
                    continue;
                }
                    
                int id = c.getInt(idIdx);
                String tenNhaHang = c.getString(tenNhaHangIdx);
                String diaChi = c.getString(diaChiIdx);
                int soPhieuBinhChon = c.getInt(soPhieuBinhChonIdx);
                float diemTrungBinh = c.getFloat(diemTrungBinhIdx);
                
                list.add(new NhaHang(id, tenNhaHang, diaChi, soPhieuBinhChon, diemTrungBinh));
                Log.d(TAG, "Added restaurant: " + tenNhaHang + " with ID: " + id);
            }
            c.close();
            Log.d(TAG, "Finished loading " + list.size() + " restaurants");
        } catch (Exception e) {
            Log.e(TAG, "Error retrieving restaurants: " + e.getMessage(), e);
        }
        
        return list;
    }

    public static void delete(DatabaseHelper dbHelper, int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.COL_ID + "=?", new String[] { String.valueOf(id) });
    }
}
