package com.example.rmp_pr6;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContentInfo;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity3 extends Activity implements View.OnClickListener{

    Button btnUpdate, btnDelete, btnADD, btnRead, btnClear;
    EditText etId, etName, etMail;

    DBHelper dbHelper;
    final String SQL_TAG = "SQLDB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);

        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);

        btnADD = (Button) findViewById(R.id.btnADD);
        btnADD.setOnClickListener(this);

        btnRead = (Button) findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);

        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        etId = (EditText) findViewById(R.id.etId);
        etName = (EditText) findViewById(R.id.etName);
        etMail = (EditText) findViewById(R.id.etEmail);

        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View v) {

        String id = etId.getText().toString();
        String name = etName.getText().toString();
        String mail = etMail.getText().toString();

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        switch (v.getId()) {

            case R.id.btnUpdate:
                if (id.equalsIgnoreCase("")) {
                    break;
                }
                Log.d(SQL_TAG, "--- Обновление в АДРЕСНОЙ КНИГЕ ---");
                contentValues.put(DBHelper.KEY_NAME, name);
                contentValues.put(DBHelper.KEY_MAIL, mail);
                int updCount = database.update(DBHelper.TABLE_CONTACTS, contentValues, DBHelper.KEY_ID + "= ?", new String[] {id});

                Log.d(SQL_TAG, "количество обновлённых строк = " + updCount);

                break;

            case R.id.btnDelete:
                if (id.equalsIgnoreCase("")) {
                    break;
                }
                Log.d(SQL_TAG, "--- Удаление из АДРЕСНОЙ КНИГИ  ---");
                int delCount = database.delete(DBHelper.TABLE_CONTACTS, DBHelper.KEY_ID + "= " + id, null);

                Log.d(SQL_TAG, "количество удалённых строк = " + delCount);

                break;

            case R.id.btnADD:
                contentValues.put(DBHelper.KEY_NAME, name);
                contentValues.put(DBHelper.KEY_MAIL, mail);

                long rowId = database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);
                Log.d(SQL_TAG, "строка добавлена, ID = " + DBHelper.KEY_ID + "= " + rowId);
                break;

            case R.id.btnRead:
                Log.d(SQL_TAG, "--- Строки в АДРЕСОЙ КНИГЕ  ---");
                Cursor cursor = database.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);

                if (cursor.moveToFirst()) {
                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
                    int mailIndex = cursor.getColumnIndex(DBHelper.KEY_MAIL);
                    do {
                        Log.d(SQL_TAG, "ID = " + cursor.getInt(idIndex) +
                                ", имя = " + cursor.getString(nameIndex) +
                                ", почта = " + cursor.getString(mailIndex));
                    } while (cursor.moveToNext());
                } else
                    Log.d(SQL_TAG, "o строк");
                break;

            case R.id.btnClear:
                Log.d(SQL_TAG, "--- Чистка АДРЕСНОЙ КНИГИ  ---");
                int clearCount = database.delete(DBHelper.TABLE_CONTACTS, null, null);
                Log.d(SQL_TAG, "количество очищенных строк = " + clearCount);
                break;
        }
        dbHelper.close();
    }


}
