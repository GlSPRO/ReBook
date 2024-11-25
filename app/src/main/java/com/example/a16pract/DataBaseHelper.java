package com.example.a16pract;

import static android.app.DownloadManager.COLUMN_DESCRIPTION;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    private  static final String DATABASE_NAME="books";
    private static final int SCHEMA=1;
    static final String TABLE_NAME="book";
    public static final String COLUMN_ID="id_book";
    public static final String COLUMN_NAME="book_name";
    public static final String COLUMN_AUTHOR="book_author";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(" CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + " TEXT," + COLUMN_AUTHOR + " TEXT );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
    public long addBook(String bookName, String bookAythor){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, bookName);
        values.put(COLUMN_AUTHOR, bookAythor);
        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result;
    }

    public Cursor getAllBooks() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, null, null, null, null, null); // Получаем все книги
    }
    public Cursor getBookById(int bookId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, COLUMN_ID + "=?", new String[]{String.valueOf(bookId)}, null, null, null);
    }
    public int deleteBookById(long bookId){
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(bookId)});
        db.close();
        return result;
    }
    public boolean updateBook(int id, String name, String author, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_AUTHOR, author);

        // Убедитесь, что COLUMN_DESCRIPTION определен в вашей таблице
        if (description != null) {
            values.put(COLUMN_DESCRIPTION, description); // Обновляем описание
        }

        int result = db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0; // Возвращает true, если обновление прошло успешно
    }


}
