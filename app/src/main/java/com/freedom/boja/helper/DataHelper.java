package com.freedom.boja.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Order.db";
    public static final String TABLE_NAME = "order_table";
    public static final String COL_1 = "Id";
    public static final String COL_2 = "Code";
    public static final String COL_3 = "Name";
    public static final String COL_4 = "Qty";
    public static final String COL_5 = "Note";
    public static final String COL_6 = "Harga";
    public static final String COL_7 = "Total";

    public static final String TABLE_Session_Login = "session_login";
    public static final String id_session = "ID";
    public static final String session_login = "session";
    public static final String status_login = "status";

    public static final String TABLE_Session_Order = "session_Order";
    public static final String id_session_Order = "ID";
    public static final String table_name = "table_name";
    public static final String number_key = "number_key";


    public static final String TABLE_Session_Login_user = "session_login_user";
    public static final String id_login_user = "ID";
    public static final String login_user_id = "login_user_id";
    public static final String login_user_nama = "login_user_nama";
    public static final String TABLE_Outlet = "Outlet_table";
    public static final String COL_Outlet_1 = "Outlet_Id";


    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (Id INTEGER PRIMARY KEY AUTOINCREMENT, Code TEXT, Name TEXT, Qty INTEGER, Note TEXT, Harga TEXT, Total TEXT)");
        db.execSQL("create table " + TABLE_Outlet + " (Id INTEGER PRIMARY KEY AUTOINCREMENT, Outlet_Id TEXT, Outlet TEXT)");
        db.execSQL("create table " + TABLE_Session_Login + " (ID INTEGER PRIMARY KEY ,Session Text,Status Text)");
        db.execSQL("create table " + TABLE_Session_Login_user + " (ID INTEGER PRIMARY KEY ,login_user_id Text,login_user_nama Text)");
        db.execSQL("create table " + TABLE_Session_Order + " (ID INTEGER PRIMARY KEY ,table_name Text,number_key Text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Outlet);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Session_Login);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Session_Login_user);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Session_Order);
        onCreate(db);
    }

    public boolean insertData(String codemenu, String namasubmenu, String qty, String note, String harga, String total) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, codemenu);
        contentValues.put(COL_3, namasubmenu);
        contentValues.put(COL_4, qty);
        contentValues.put(COL_5, note);
        contentValues.put(COL_6, harga);
        contentValues.put(COL_7, total);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean updateData(String codemenu, String namasubmenu, String jumlah, String harga, String total) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, codemenu);
        contentValues.put(COL_3, namasubmenu);
        contentValues.put(COL_4, jumlah);
        contentValues.put(COL_6, harga);
        contentValues.put(COL_7, total);
        db.update(TABLE_NAME, contentValues, "code = ?", new String[]{codemenu});
        return true;
    }

    public boolean updateNote(String codemenu, String note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, codemenu);
        contentValues.put(COL_5, note);
        db.update(TABLE_NAME, contentValues, "code = ?", new String[]{codemenu});
        return true;

    }

    public String getnote(String codemenu) {
        String qty = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT Note FROM " + TABLE_NAME + " WHERE Id IN (SELECT MAX(Id)  AS Id FROM " + TABLE_NAME + "  GROUP BY `code`) AND `code`=" + codemenu + " ORDER BY Id limit 1; ", null);
        while (res.moveToNext()) {
            qty = res.getString(0);
            // Log.e("getnote", qty);
        }
        res.close();
        return qty;
    }

    public Integer deleteData(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "code = ?", new String[]{String.valueOf(id)});
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME);

    }

    public String get_qty_menu_paket(String codemenu) {
        String qty = "0";
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor res = db.rawQuery("SELECT SUM(qty) FROM  " + TABLE_NAME + " where code=" + codemenu + " ", null);
            while (res.moveToNext()) {
                qty = res.getString(0);

            }
            res.close();

            Log.d("get jumlah", qty);
            return qty;
        } catch (Exception e) {

            qty = "0";
            Log.d("get jumlah", qty);
            return qty;
        }

    }

    public boolean insert_session_order(String id, String table_name_, String number_key_) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(id_session_Order, id);
        contentValues.put(table_name, table_name_);
        contentValues.put(number_key, number_key_);


        long result = db.insert(TABLE_Session_Order, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public String get_session_order(String id_session) {
        String number_key = "0";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_Session_Order + " WHERE ID=" + id_session + " ", null);
        while (res.moveToNext()) {
            number_key = res.getString(2);


        }
        res.close();
        return number_key;
    }

    public boolean update_session_order(String id_session, String number_key_) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(number_key, number_key_);
        db.update(TABLE_Session_Order, contentValues, "ID = ?", new String[]{id_session});
        return true;
    }

    //inflate total
    public Cursor jumlah_qty() {
        // String jumlah = "0";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("  SELECT sum (Qty) FROM  " + TABLE_NAME + "", null);

        return res;
    }

    public String getTotal() {
        String qty = "0";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT SUM(Total) FROM  " + TABLE_NAME + " ", null);
        while (res.moveToNext()) {
            qty = res.getString(0);

        }
        // Log.e("get total",qty);

        res.close();
        return qty;

    }
    public String hitungjumlahdata() {
        String jmlh = "0";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("  SELECT count () FROM  " + TABLE_NAME + "", null);
        while (res.moveToNext()) {
            jmlh = res.getString(0);

        }
        res.close();
        return jmlh;

        // return res;


    }
    public Cursor getjumlah2() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Id IN (SELECT MAX(Id) AS Id FROM " + TABLE_NAME + " GROUP BY `id`) ORDER BY Id; ", null);
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME + "", null);

        return res;
    }

}
