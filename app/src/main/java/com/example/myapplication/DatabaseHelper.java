package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    public static final String DB_NAME = "nhahang.db";
    public static final int DB_VERSION = 3; // Increased version to force database recreation
    public static final String TABLE_NAME = "NhaHang";
    public static final String COL_ID = "id";
    public static final String COL_TENNHAHANG = "tenNhaHang";
    public static final String COL_DIACHI = "diaChi";
    public static final String COL_SOPHIEUBINHCHON = "soPhieuBinhChon";
    public static final String COL_DIEMTRUNGBINH = "diemTrungBinh";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.d(TAG, "DatabaseHelper constructor called");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COL_TENNHAHANG + " TEXT," +
                    COL_DIACHI + " TEXT," +
                    COL_SOPHIEUBINHCHON + " INTEGER," +
                    COL_DIEMTRUNGBINH + " REAL)";
            
            Log.d(TAG, "Creating table with SQL: " + createTable);
            db.execSQL(createTable);

            // Dữ liệu mẫu
            String insertData = "INSERT INTO " + TABLE_NAME + " (" +
                    COL_TENNHAHANG + "," + COL_DIACHI + "," + COL_SOPHIEUBINHCHON + "," + COL_DIEMTRUNGBINH +
                    ") VALUES " +
                    "('ChamCham','Ha Noi',15,8.5)," +
                    "('KimThanh','Ho Chi Minh',20,9.2)," +
                    "('Happy Garden','Da Nang',12,7.8)," +
                    "('Seafood Palace','Nha Trang',18,8.9)";
                    
            Log.d(TAG, "Inserting sample data with SQL: " + insertData);
            db.execSQL(insertData);
            Log.d(TAG, "Database created successfully with sample data");
        } catch (Exception e) {
            Log.e(TAG, "Error creating database: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
