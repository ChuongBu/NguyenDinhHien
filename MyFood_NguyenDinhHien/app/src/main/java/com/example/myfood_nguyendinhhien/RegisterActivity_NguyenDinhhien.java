package com.example.myfood_nguyendinhhien;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myfood_nguyendinhhien.database.DatabaseHelper_NguyenDinhhien;

public class RegisterActivity_NguyenDinhhien extends AppCompatActivity {

    EditText edtUsername, edtPassword, edtRePassword;
    Button btnRegister;
    DatabaseHelper_NguyenDinhhien dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_nguyendinhhien);

        edtUsername = findViewById(R.id.edtUsernameReg_NguyenDinhhien);
        edtPassword = findViewById(R.id.edtPasswordReg_NguyenDinhhien);
        edtRePassword = findViewById(R.id.edtRePasswordReg_NguyenDinhhien);
        btnRegister = findViewById(R.id.btnRegister_NguyenDinhhien);

        dbHelper = new DatabaseHelper_NguyenDinhhien(this);

        btnRegister.setOnClickListener(view -> registerUser_NguyenDinhhien());
    }

    private void registerUser_NguyenDinhhien() {
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String rePassword = edtRePassword.getText().toString().trim();

        if (!password.equals(rePassword)) {
            Toast.makeText(this, "Mật khẩu không khớp!", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Username", username);
        values.put("Password", password);

        long result = db.insert("User", null, values);

        if (result != -1) {
            Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity_NguyenDinhhien.class));
            finish();
        } else {
            Toast.makeText(this, "Tên đăng nhập đã tồn tại!", Toast.LENGTH_SHORT).show();
        }
    }
}
