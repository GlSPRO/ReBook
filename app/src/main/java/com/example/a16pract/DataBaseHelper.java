package com.example.a16pract;

import static android.app.DownloadManager.COLUMN_DESCRIPTION;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
//запросы
public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "books"; // Имя базы данных
    private static final int SCHEMA = 1; // Версия схемы базы данных
    static final String TABLE_NAME = "book"; // Имя таблицы
    public static final String COLUMN_ID = "id_book"; // Имя столбца для идентификатора книги
    public static final String COLUMN_NAME = "book_name"; // Имя столбца для названия книги
    public static final String COLUMN_AUTHOR = "book_author"; // Имя столбца для автора книги

    // Конструктор класса DataBaseHelper
    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, SCHEMA); // Вызов конструктора родительского класса
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Создание таблицы при первом запуске базы данных
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_AUTHOR + " TEXT );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Удаление старой таблицы и создание новой при обновлении схемы базы данных
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase); // Создание новой таблицы
    }

    public long addBook(String bookName, String bookAythor) {
        // Получаем объект базы данных в режиме записи
        SQLiteDatabase db = this.getWritableDatabase();

        // Создаем объект ContentValues для хранения значений книги
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, bookName); // Добавляем название книги
        values.put(COLUMN_AUTHOR, bookAythor); // Добавляем автора книги

        // Вставляем новую запись в таблицу и сохраняем результат (ID новой записи)
        long result = db.insert(TABLE_NAME, null, values);

        db.close(); // Закрываем базу данных после выполнения операции
        return result; // Возвращаем ID новой записи (или -1 в случае ошибки)
    }

    public Cursor getAllBooks() {
        // Получаем объект базы данных в режиме чтения
        SQLiteDatabase db = this.getReadableDatabase();

        // Возвращаем курсор с результатами запроса на получение всех книг из таблицы
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }

    public Cursor getBookById(int bookId) {
        // Получаем объект базы данных в режиме чтения
        SQLiteDatabase db = this.getReadableDatabase();

        // Возвращаем курсор с результатами запроса на получение книги по ID
        return db.query(TABLE_NAME, null, COLUMN_ID + "=?", new String[]{String.valueOf(bookId)}, null, null, null);
    }

    public int deleteBookById(long bookId) {
        // Получаем объект базы данных в режиме записи
        SQLiteDatabase db = this.getWritableDatabase();

        // Удаляем книгу из таблицы по ID и сохраняем количество удаленных строк
        int result = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(bookId)});

        db.close(); // Закрываем базу данных после выполнения операции
        return result; // Возвращаем количество удаленных строк (0 если ничего не удалено)
    }

    public boolean updateBook(int id, String name, String author, String description) {
        // Получаем объект базы данных в режиме записи
        SQLiteDatabase db = this.getWritableDatabase();

        // Создаем объект ContentValues для хранения обновленных значений книги
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name); // Обновляем название книги
        values.put(COLUMN_AUTHOR, author); // Обновляем автора книги

        if (description != null) {
            values.put(COLUMN_DESCRIPTION, description); // Обновляем описание (если не null)
        }

        // Выполняем обновление записи в таблице и сохраняем количество обновленных строк
        int result = db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(id)});

        db.close(); // Закрываем базу данных после выполнения операции
        return result > 0; // Возвращает true, если обновление прошло успешно (количество обновленных строк больше 0)
    }
}