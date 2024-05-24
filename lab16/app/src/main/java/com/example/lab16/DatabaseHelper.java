package com.example.lab16;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

class DatabaseHelper extends SQLiteOpenHelper implements BaseColumns {
    public static final String DB_CONTACTS = "contacts.db";
    public static final int DATABASE_VERSION = 2; // Updated version

    public DatabaseHelper(Context context) {
        super(context, DB_CONTACTS, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DBContract.DBEntry.TABLE_NAME
                + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DBContract.DBEntry.COLUMN_NAME_NAME + " TEXT, "
                + DBContract.DBEntry.COLUMN_NAME_PHONE + " TEXT, "
                + DBContract.DBEntry.COLUMN_NAME_EMAIL + " TEXT, "
                + DBContract.DBEntry.COLUMN_NAME_ADDRESS + " TEXT);");
        seedDatabase(db);
    }

    private void seedDatabase(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(DBContract.DBEntry.COLUMN_NAME_NAME, "Иван Иванов");
        values.put(DBContract.DBEntry.COLUMN_NAME_PHONE, "8-999-111-11-11");
        values.put(DBContract.DBEntry.COLUMN_NAME_EMAIL, "ivan@example.com");
        values.put(DBContract.DBEntry.COLUMN_NAME_ADDRESS, "ул. Ленина, 1");
        db.insert(DBContract.DBEntry.TABLE_NAME, null, values);

        values.put(DBContract.DBEntry.COLUMN_NAME_NAME, "Пётр Петров");
        values.put(DBContract.DBEntry.COLUMN_NAME_PHONE, "8-999-222-22-22");
        values.put(DBContract.DBEntry.COLUMN_NAME_EMAIL, "petr@example.com");
        values.put(DBContract.DBEntry.COLUMN_NAME_ADDRESS, "ул. Ленина, 2");
        db.insert(DBContract.DBEntry.TABLE_NAME, null, values);

        values.put(DBContract.DBEntry.COLUMN_NAME_NAME, "Иван Петров");
        values.put(DBContract.DBEntry.COLUMN_NAME_PHONE, "8-999-333-33-33");
        values.put(DBContract.DBEntry.COLUMN_NAME_EMAIL, "ivanp@example.com");
        values.put(DBContract.DBEntry.COLUMN_NAME_ADDRESS, "ул. Ленина, 3");
        db.insert(DBContract.DBEntry.TABLE_NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE " + DBContract.DBEntry.TABLE_NAME + " ADD COLUMN "
                + DBContract.DBEntry.COLUMN_NAME_EMAIL + " TEXT;");
        db.execSQL("ALTER TABLE " + DBContract.DBEntry.TABLE_NAME + " ADD COLUMN "
                + DBContract.DBEntry.COLUMN_NAME_ADDRESS + " TEXT;");
    }
}