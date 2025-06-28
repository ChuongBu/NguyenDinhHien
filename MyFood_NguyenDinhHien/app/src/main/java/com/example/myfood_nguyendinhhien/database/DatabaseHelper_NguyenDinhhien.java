package com.example.myfood_nguyendinhhien.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper_NguyenDinhhien extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Food_NguyenDinhhien.db";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHelper_NguyenDinhhien(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // User table
        db.execSQL("CREATE TABLE User (" +
                "UserID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Name TEXT," +
                "Gender TEXT," +
                "Date_of_birth TEXT," +
                "Phone TEXT," +
                "Username TEXT UNIQUE," +
                "Password TEXT)");

        // Restaurant table
        db.execSQL("CREATE TABLE Restaurant (" +
                "ResID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Name TEXT," +
                "Address TEXT," +
                "Phone TEXT," +
                "Image TEXT)");

        // Food table (with Price added)
        db.execSQL("CREATE TABLE Food (" +
                "FoodID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Name TEXT," +
                "Type TEXT," +
                "Description TEXT," +
                "Image TEXT," +
                "Price REAL," +
                "ResID INTEGER," +
                "FOREIGN KEY (ResID) REFERENCES Restaurant(ResID))");

        // Order table
        db.execSQL("CREATE TABLE OrderTable (" +
                "OrderID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Address TEXT," +
                "Date_order TEXT," +
                "Total_value REAL," +
                "Status TEXT," +
                "UserID INTEGER," +
                "FOREIGN KEY (UserID) REFERENCES User(UserID))");

        // OrderDetail table
        db.execSQL("CREATE TABLE OrderDetail (" +
                "OrderID INTEGER," +
                "FoodID INTEGER," +
                "Size TEXT," +
                "Quantity INTEGER," +
                "PRIMARY KEY (OrderID, FoodID)," +
                "FOREIGN KEY (OrderID) REFERENCES OrderTable(OrderID)," +
                "FOREIGN KEY (FoodID) REFERENCES Food(FoodID))");

        // Insert sample Users
        db.execSQL("INSERT INTO User (Name, Gender, Date_of_birth, Phone, Username, Password) VALUES " +
                "('Nguyen Dinh Hien', 'Male', '2004-09-01', '0901000001', 'nguyendinhhien', '123')," +
                "('Tran Thi B', 'Female', '1999-06-02', '0902000002', 'tranthib', '123')," +
                "('Le Van C', 'Male', '1997-07-03', '0903000003', 'levanc', '123')," +
                "('Pham Thi D', 'Female', '2000-08-04', '0904000004', 'phamthid', '123')," +
                "('Hoang Van E', 'Male', '1996-09-05', '0905000005', 'hoangvane', '123')");

        // Insert sample Restaurants
        db.execSQL("INSERT INTO Restaurant (Name, Address, Phone, Image) VALUES " +
                "('Pizza House', '123 Le Lai, Q1', '0281234567', 'pizza1')," +
                "('Bun Bo Hue', '456 Tran Hung Dao, Hue', '0234123456', 'bunbo1')," +
                "('Com Tam Cali', '789 Nguyen Trai, Q5', '0287654321', 'comtam1')," +
                "('Pho 24', '321 Hai Ba Trung, HN', '02433334444', 'pho1')," +
                "('Jollibee', '88 Dien Bien Phu, Q3', '02888889999', 'garan1')");

        // Insert sample Foods (image name must match drawable, price added)
        db.execSQL("INSERT INTO Food (Name, Type, Description, Image, Price, ResID) VALUES " +
                "('Pizza Hải Sản', 'Pizza', 'Hải sản và phô mai', 'pizza1', 50000, 1)," +
                "('Pizza Bò', 'Pizza', 'Bò và phô mai', 'pizza2', 60000, 1)," +
                "('Bún bò đặc biệt', 'Bún', 'Nạm và chả', 'bunbo1', 40000, 2)," +
                "('Bún bò giò heo', 'Bún', 'Giò heo', 'bunbo2', 45000, 2)," +
                "('Cơm tấm sườn', 'Cơm', 'Sườn nướng', 'comtam1', 35000, 3)," +
                "('Cơm tấm bì chả', 'Cơm', 'Bì chả trứng', 'comtam2', 38000, 3)," +
                "('Phở bò tái', 'Phở', 'Thịt bò tái mềm', 'pho1', 42000, 4)," +
                "('Phở gầu', 'Phở', 'Bò gầu béo', 'pho2', 44000, 4)," +
                "('Gà rán nhỏ', 'Gà', '1 miếng gà + nước', 'garan1', 30000, 5)," +
                "('Gà rán lớn', 'Gà', '3 miếng + khoai + nước', 'garan2', 50000, 5)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS OrderDetail");
        db.execSQL("DROP TABLE IF EXISTS OrderTable");
        db.execSQL("DROP TABLE IF EXISTS Food");
        db.execSQL("DROP TABLE IF EXISTS Restaurant");
        db.execSQL("DROP TABLE IF EXISTS User");
        onCreate(db);
    }
}