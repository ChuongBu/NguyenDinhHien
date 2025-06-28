package com.example.myfood_nguyendinhhien;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myfood_nguyendinhhien.database.DatabaseHelper_NguyenDinhhien;

public class FoodDetailActivity_NguyenDinhhien extends AppCompatActivity {

    ImageView imgFood;
    TextView txtName, txtDesc, txtRestaurant, txtPrice;
    RadioGroup radioSize;
    RadioButton radioSmall, radioLarge;
    Button btnAddToCart;

    int foodID;
    double basePrice = 0;
    int resID;
    String foodName, foodDesc, imageUrl, resName, resAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail_nguyendinhhien);

        // Gán view
        imgFood = findViewById(R.id.imgFoodDetail_NguyenDinhhien);
        txtName = findViewById(R.id.txtFoodNameDetail_NguyenDinhhien);
        txtDesc = findViewById(R.id.txtFoodDescDetail_NguyenDinhhien);
        txtRestaurant = findViewById(R.id.txtRestaurantInfo_NguyenDinhhien);
        txtPrice = findViewById(R.id.txtPrice_NguyenDinhhien);
        radioSize = findViewById(R.id.radioSize_NguyenDinhhien);
        radioSmall = findViewById(R.id.radioSmall_NguyenDinhhien);
        radioLarge = findViewById(R.id.radioLarge_NguyenDinhhien);
        btnAddToCart = findViewById(R.id.btnAddToCart_NguyenDinhhien);

        // Nhận foodID
        foodID = getIntent().getIntExtra("foodID", -1);
        if (foodID == -1) {
            Toast.makeText(this, "Không tìm thấy món ăn!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Load chi tiết
        loadFoodDetail();

        radioSize.setOnCheckedChangeListener((group, checkedId) -> updatePrice());

        btnAddToCart.setOnClickListener(view -> {
            String size = radioSmall.isChecked() ? "Small" : "Large";
            double finalPrice = basePrice + (size.equals("Large") ? 10000 : 0);
            Toast.makeText(this, "Đã thêm: " + foodName + " - " + size + " (" + finalPrice + "đ)", Toast.LENGTH_SHORT).show();

        });
    }

    private void loadFoodDetail() {
        DatabaseHelper_NguyenDinhhien dbHelper = new DatabaseHelper_NguyenDinhhien(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM Food WHERE FoodID=?", new String[]{String.valueOf(foodID)});
        if (c.moveToFirst()) {
            foodName = c.getString(1);
            foodDesc = c.getString(3);
            imageUrl = c.getString(4);
            basePrice = c.getDouble(5);
            resID = c.getInt(6);
        } else {
            Toast.makeText(this, "Không tìm thấy dữ liệu món ăn!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        c.close();

        Cursor r = db.rawQuery("SELECT * FROM Restaurant WHERE ResID=?", new String[]{String.valueOf(resID)});
        if (r.moveToFirst()) {
            resName = r.getString(1);
            resAddress = r.getString(2);
        } else {
            resName = "Không rõ";
            resAddress = "";
        }
        r.close();

        txtName.setText(foodName);
        txtDesc.setText(foodDesc);
        txtRestaurant.setText("Nhà hàng: " + resName + " - " + resAddress);

        int imageResId = getResources().getIdentifier(imageUrl, "drawable", getPackageName());
        if (imageResId != 0) {
            imgFood.setImageResource(imageResId);
        } else {
            imgFood.setImageResource(android.R.drawable.ic_menu_report_image); // fallback ảnh nếu thiếu
        }

        updatePrice();
    }

    private void updatePrice() {
        boolean isLarge = radioLarge.isChecked();
        double finalPrice = basePrice + (isLarge ? 10000 : 0);
        txtPrice.setText("Giá: " + finalPrice + "đ");
    }
}
