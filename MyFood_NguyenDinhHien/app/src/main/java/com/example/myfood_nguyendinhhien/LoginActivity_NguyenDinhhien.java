package com.example.myfood_nguyendinhhien;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myfood_nguyendinhhien.database.DatabaseHelper_NguyenDinhhien;

public class LoginActivity_NguyenDinhhien extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    Button btnLogin;
    TextView txtRegister;
    DatabaseHelper_NguyenDinhhien dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_nguyendinhhien);

        edtUsername = findViewById(R.id.edtUsername_NguyenDinhhien);
        edtPassword = findViewById(R.id.edtPassword_NguyenDinhhien);
        btnLogin = findViewById(R.id.btnLogin_NguyenDinhhien);
        txtRegister = findViewById(R.id.txtRegister_NguyenDinhhien);

        dbHelper = new DatabaseHelper_NguyenDinhhien(this);

        btnLogin.setOnClickListener(view -> checkUser_NguyenDinhhien());

        txtRegister.setOnClickListener(view -> {
            Intent i = new Intent(this, RegisterActivity_NguyenDinhhien.class);
            startActivity(i);
        });
    }

    private void checkUser_NguyenDinhhien() {
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE Username=? AND Password=?",
                new String[]{username, password});

        if (cursor.moveToFirst()) {
            // Luu trang thai dang nhap
            SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("loggedUser", username); // Ban co the luu UserID neu muon
            editor.apply();

            Toast.makeText(this, "Dang nhap thanh cong!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, HomeActivity_NguyenDinhhien.class));
            finish(); // Dong LoginActivity sau khi dang nhap
        } else {
            Toast.makeText(this, "Sai thong tin dang nhap!", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
    }
}
