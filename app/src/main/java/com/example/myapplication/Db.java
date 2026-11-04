
package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Db extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ClothesDB";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "Clothes";

    public Db(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "category TEXT, " +
                "colour TEXT)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Insert clothes
    public long insertClothes(String name,
                              String category,
                              String colour) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("category", category);
        values.put("colour", colour);

        return db.insert(TABLE_NAME, null, values);
    }

    // Retrieve all clothes
    public String getAllClothes() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_NAME, null);

        StringBuilder result = new StringBuilder();

        if (cursor.moveToFirst()) {

            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String category = cursor.getString(2);
                String colour = cursor.getString(3);

                result.append("ID: ").append(id).append("\n");
                result.append("Name: ").append(name).append("\n");
                result.append("Category: ").append(category).append("\n");
                result.append("Colour: ").append(colour).append("\n");
                result.append("--------------------------\n");

            } while (cursor.moveToNext());

        } else {
            result.append("No clothes found.");
        }

        cursor.close();

        return result.toString();
    }

    // Update clothes using ID
    public int updateClothes(int id,
                             String name,
                             String category,
                             String colour) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("category", category);
        values.put("colour", colour);

        return db.update(
                TABLE_NAME,
                values,
                "id=?",
                new String[]{String.valueOf(id)}
        );
    }

    // Delete clothes using ID
    public int deleteClothes(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(
                TABLE_NAME,
                "id=?",
                new String[]{String.valueOf(id)}
        );
    }
}
