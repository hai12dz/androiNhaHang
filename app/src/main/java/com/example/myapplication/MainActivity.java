package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.widget.SearchView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private ListView listView;
    private List<NhaHang> nhaHangList;
    private List<NhaHang> fullNhaHangList; // Lưu danh sách đầy đủ
    private NhaHangAdapter adapter;
//    private EditText editSearch;
    private SearchView searchView;
    private int selectedPosition = -1;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        dbHelper = new DatabaseHelper(this);
//        listView = findViewById(R.id.listView);
//        editSearch = findViewById(R.id.editSearch);
//        loadNhaHangData();
//
//        // Thêm sự kiện TextWatcher cho EditText
//        editSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                filterByScore(s.toString());
//            }
//        });
//
//        registerForContextMenu(listView);
//        listView.setOnItemLongClickListener((parent, view, position, id) -> {
//            selectedPosition = position;
//
//            // Lấy thông tin nhà hàng được chọn
//            NhaHang selectedNhaHang = nhaHangList.get(position);
//            float selectedScore = selectedNhaHang.getDiemTrungBinh();
//
//            // Đếm số nhà hàng có điểm cao hơn
//            int count = 0;
//            for (NhaHang nh : fullNhaHangList) {
//                if (nh.getDiemTrungBinh() > selectedScore) {
//                    count++;
//                }
//            }
//
//            // Hiển thị thông báo Toast
//            Toast.makeText(MainActivity.this,
//                    "Có " + count + " nhà hàng có điểm cao hơn " + selectedScore,
//                    Toast.LENGTH_LONG).show();
//
//            return false; // Vẫn hiển thị context menu
//        }); // Show a toast to confirm the app is running
//        Toast.makeText(this, "Đã tải danh sách nhà hàng", Toast.LENGTH_SHORT).show();
//    }
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_main);

    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
        Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
        return insets;
    });

    // Toolbar
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar); // Gán Toolbar thành ActionBar chính

    dbHelper = new DatabaseHelper(this);
    listView = findViewById(R.id.listView);
    searchView = findViewById(R.id.searchView);

    loadNhaHangData();

    // Xử lý tìm kiếm bằng SearchView
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            filterByScore(query);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            filterByScore(newText);
            return true;
        }
    });

    registerForContextMenu(listView);
    listView.setOnItemLongClickListener((parent, view, position, id) -> {
        selectedPosition = position;
        NhaHang selectedNhaHang = nhaHangList.get(position);
        float selectedScore = selectedNhaHang.getDiemTrungBinh();

        int count = 0;
        for (NhaHang nh : fullNhaHangList) {
            if (nh.getDiemTrungBinh() > selectedScore) {
                count++;
            }
        }

        Toast.makeText(MainActivity.this,
                "Có " + count + " nhà hàng có điểm cao hơn " + selectedScore,
                Toast.LENGTH_LONG).show();

        return false;
    });

    Toast.makeText(this, "Đã tải danh sách nhà hàng", Toast.LENGTH_SHORT).show();
}
    private void loadNhaHangData() {
        fullNhaHangList = DatabaseUtils.getAllNhaHang(dbHelper);
        nhaHangList = new ArrayList<>(fullNhaHangList); // Tạo bản sao

        // Debug message
        Toast.makeText(this, "Đã tìm thấy " + nhaHangList.size() + " nhà hàng", Toast.LENGTH_SHORT).show();

        adapter = new NhaHangAdapter(this, nhaHangList);
        listView.setAdapter(adapter);
    }

    private void filterByScore(String scoreText) {
        nhaHangList.clear();

        if (scoreText.isEmpty()) {
            // Nếu ô tìm kiếm trống, hiển thị tất cả
            nhaHangList.addAll(fullNhaHangList);
        } else {
            try {
                // Chuyển đổi văn bản thành số
                float minScore = Float.parseFloat(scoreText);

                // Thêm nhà hàng có điểm >= điểm nhập vào
                for (NhaHang nh : fullNhaHangList) {
                    if (nh.getDiemTrungBinh() >= minScore) {
                        nhaHangList.add(nh);
                    }
                }
            } catch (NumberFormatException e) {
                // Nếu không phải số, hiển thị tất cả
                nhaHangList.addAll(fullNhaHangList);
            }
        }

        // Cập nhật ListView
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 1, 0, "Sửa");
        menu.add(0, 2, 1, "Xóa");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (selectedPosition < 0)
            return super.onContextItemSelected(item);
        NhaHang nh = nhaHangList.get(selectedPosition);
        if (item.getItemId() == 2) {
            showDeleteDialog(nh);
            return true;
        }
        // Xử lý sửa nếu cần
        return super.onContextItemSelected(item);
    }

    private void showDeleteDialog(NhaHang nh) {
        String title = "Bạn muốn xóa nhà hàng này?";
        StringBuilder msg = new StringBuilder();
        msg.append("Tên nhà hàng: ").append(nh.getTenNhaHang()).append("\n");
        msg.append("Địa chỉ: ").append(nh.getDiaChi()).append("\n");
        msg.append("Điểm trung bình: ").append(nh.getDiemTrungBinh());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(msg.toString());
        builder.setNegativeButton("Quay về", null);
        builder.setPositiveButton("OK", (dialog, which) -> {
            DatabaseUtils.delete(dbHelper, nh.getId());
            loadNhaHangData();
        });
        builder.show();
    }
}