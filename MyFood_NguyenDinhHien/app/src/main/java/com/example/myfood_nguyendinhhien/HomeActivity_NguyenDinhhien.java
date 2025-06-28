package com.example.myfood_nguyendinhhien;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;

import com.example.myfood_nguyendinhhien.database.DatabaseHelper_NguyenDinhhien;

import java.io.*;
import java.util.ArrayList;

public class HomeActivity_NguyenDinhhien extends AppCompatActivity {

    RecyclerView recyclerView;
    Button btnLogout, btnExport;
    ArrayList<Restaurant_NguyenDinhhien> list = new ArrayList<>();
    DatabaseHelper_NguyenDinhhien dbHelper;
    RestaurantAdapter_NguyenDinhhien adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_nguyendinhhien);

        recyclerView = findViewById(R.id.recyclerRestaurant_NguyenDinhhien);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnLogout = findViewById(R.id.btnLogout_NguyenDinhhien);
        btnExport = findViewById(R.id.btnExportCSV_NguyenDinhhien); // thêm nút trong layout

        dbHelper = new DatabaseHelper_NguyenDinhhien(this);
        loadRestaurants();

        adapter = new RestaurantAdapter_NguyenDinhhien(this, list);
        recyclerView.setAdapter(adapter);

        btnLogout.setOnClickListener(v -> {
            getSharedPreferences("MyPrefs", MODE_PRIVATE).edit().clear().apply();
            startActivity(new Intent(this, LoginActivity_NguyenDinhhien.class));
            finish();
        });

        btnExport.setOnClickListener(v -> {
            exportTableToCSV("User", this);
            exportTableToCSV("Restaurant", this);
            exportTableToCSV("Food", this);
        });
    }

    private void loadRestaurants() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Restaurant", null);
        list.clear();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String address = cursor.getString(2);
            String image = cursor.getString(4);
            list.add(new Restaurant_NguyenDinhhien(id, name, address, image));
        }
        cursor.close();
    }

    public void exportTableToCSV(String tableName, Context context) {
        File exportDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "MyFood_Exports");
        if (!exportDir.exists()) exportDir.mkdirs();

        File file = new File(exportDir, tableName + ".csv");

        try {
            FileWriter writer = new FileWriter(file);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM " + tableName, null);

            for (int i = 0; i < c.getColumnCount(); i++) {
                writer.append(c.getColumnName(i));
                if (i < c.getColumnCount() - 1) writer.append(",");
            }
            writer.append("\n");

            while (c.moveToNext()) {
                for (int i = 0; i < c.getColumnCount(); i++) {
                    String data = c.getString(i);
                    writer.append(data == null ? "" : data.replace(",", " "));
                    if (i < c.getColumnCount() - 1) writer.append(",");
                }
                writer.append("\n");
            }

            c.close();
            writer.flush();
            writer.close();

            Toast.makeText(context, "✅ Xuất " + tableName + ".csv thành công!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "❌ Lỗi export: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
