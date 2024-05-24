package com.example.lab16;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button buttonDel, buttonClear, buttonShow, buttonAdd;
    EditText ET_id, ET_name, ET_phone, ET_email, ET_address;
    TextView text;
    DatabaseHelper DatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(this);
        buttonShow = findViewById(R.id.buttonShow);
        buttonShow.setOnClickListener(this);
        buttonDel = findViewById(R.id.buttonDel);
        buttonDel.setOnClickListener(this);
        buttonClear = findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(this);
        ET_id = findViewById(R.id.ET_id);
        ET_name = findViewById(R.id.ET_name);
        ET_phone = findViewById(R.id.ET_phone);
        ET_email = findViewById(R.id.ET_email);
        ET_address = findViewById(R.id.ET_address);
        text = findViewById(R.id.text);
        DatabaseHelper = new DatabaseHelper(this);
    }

    @Override
    public void onClick(View v) {
        String input_id = ET_id.getText().toString();
        String input_name = ET_name.getText().toString();
        String input_phone = ET_phone.getText().toString();
        String input_email = ET_email.getText().toString();
        String input_address = ET_address.getText().toString();
        SQLiteDatabase db = DatabaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        int but = v.getId();
        if (but == R.id.buttonShow) {
            text.setText("");
            String[] projection = {
                    DBContract.DBEntry._ID,
                    DBContract.DBEntry.COLUMN_NAME_NAME,
                    DBContract.DBEntry.COLUMN_NAME_PHONE,
                    DBContract.DBEntry.COLUMN_NAME_EMAIL,
                    DBContract.DBEntry.COLUMN_NAME_ADDRESS
            };
            Cursor cursor = db.query(
                    DBContract.DBEntry.TABLE_NAME,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    null
            );
            int index_id = cursor.getColumnIndex(DBContract.DBEntry._ID);
            int index_name = cursor.getColumnIndex(DBContract.DBEntry.COLUMN_NAME_NAME);
            int index_phone = cursor.getColumnIndex(DBContract.DBEntry.COLUMN_NAME_PHONE);
            int index_email = cursor.getColumnIndex(DBContract.DBEntry.COLUMN_NAME_EMAIL);
            int index_address = cursor.getColumnIndex(DBContract.DBEntry.COLUMN_NAME_ADDRESS);
            while (cursor.moveToNext()) {
                String value_id = cursor.getString(index_id);
                String value_name = cursor.getString(index_name);
                String value_phone = cursor.getString(index_phone);
                String value_email = cursor.getString(index_email);
                String value_address = cursor.getString(index_address);
                text.append("\nID: " + value_id + "\nИмя: " + value_name + "\nТелефон: " + value_phone + "\nEmail: " + value_email + "\nАдрес: " + value_address + "\n");
            }
            cursor.close();
        } else if (but == R.id.buttonAdd) {
            values.put(DBContract.DBEntry.COLUMN_NAME_NAME, input_name);
            values.put(DBContract.DBEntry.COLUMN_NAME_PHONE, input_phone);
            values.put(DBContract.DBEntry.COLUMN_NAME_EMAIL, input_email);
            values.put(DBContract.DBEntry.COLUMN_NAME_ADDRESS, input_address);
            db.insert(DBContract.DBEntry.TABLE_NAME, null, values);
            buttonShow.callOnClick();
        } else if (but == R.id.buttonDel) {
            String selection = DBContract.DBEntry._ID + "=?";
            String[] selectionArgs = {input_id};
            db.delete(DBContract.DBEntry.TABLE_NAME, selection, selectionArgs);
            buttonShow.callOnClick();
        } else if (but == R.id.buttonClear) {
            db.delete(DBContract.DBEntry.TABLE_NAME, null, null);
            buttonShow.callOnClick();
        }

        DatabaseHelper.close();
    }
}