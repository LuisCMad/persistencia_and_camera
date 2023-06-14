package com.example.felipe_ochoa.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.felipe_ochoa.data.models.ApliccationModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, ApplicationsContract.DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crea la tabla cuando se crea la base de datos por primera vez
        String createTableQuery = "CREATE TABLE " + ApplicationsContract.TABLE_NAME + " (" +
                ApplicationsContract.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ApplicationsContract.COLUMN_USER_NAME + " TEXT" + ", " + ApplicationsContract.COLUMN_UNIVERSITY_NAME + " TEXT" + " ," + ApplicationsContract.COLUMN_USER_IMAGE + " TEXT" + ")";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ApplicationsContract.TABLE_NAME);
        onCreate(db);
    }

    public List<ApliccationModel> getAllNames() {
        List<ApliccationModel> listApplication = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + ApplicationsContract.TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            int nameColumnIndex = cursor.getColumnIndex(ApplicationsContract.COLUMN_USER_NAME);
            int uniColumnIndex = cursor.getColumnIndex(ApplicationsContract.COLUMN_UNIVERSITY_NAME);
            int imgColumnIndex = cursor.getColumnIndex(ApplicationsContract.COLUMN_USER_IMAGE);
            do {
                ApliccationModel model = new ApliccationModel();
                model.setName(cursor.getString(nameColumnIndex));
                model.setUniversity(cursor.getString(uniColumnIndex));
                model.setImage(cursor.getString(imgColumnIndex));
                listApplication.add(model);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listApplication;
    }
}