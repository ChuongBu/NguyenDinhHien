package com.example.myfood_nguyendinhhien;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;
import com.example.myfood_nguyendinhhien.database.DatabaseHelper_NguyenDinhhien;
import java.util.ArrayList;

public class FoodListActivity_NguyenDinhhien extends AppCompatActivity {

    RecyclerView recyclerView;
    FoodAdapter_NguyenDinhhien adapter;
    ArrayList<Food_NguyenDinhhien> list = new ArrayList<>();
    DatabaseHelper_NguyenDinhhien dbHelper;
    int resID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list_nguyendinhhien);

        recyclerView = findViewById(R.id.recyclerFood_NguyenDinhhien);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dbHelper = new DatabaseHelper_NguyenDinhhien(this);

        resID = getIntent().getIntExtra("resID", -1);
        loadFoods();

        adapter = new FoodAdapter_NguyenDinhhien(this, list);
        recyclerView.setAdapter(adapter);
    }

    private void loadFoods() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Food WHERE ResID=?", new String[]{String.valueOf(resID)});
        list.clear();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String desc = cursor.getString(3);
            String image = cursor.getString(4);
            double price = cursor.getDouble(5);
            list.add(new Food_NguyenDinhhien(id, name, desc, image, price));
        }
        cursor.close();
    }
}
