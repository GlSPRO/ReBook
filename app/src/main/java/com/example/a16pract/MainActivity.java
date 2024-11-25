package com.example.a16pract;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DataBaseHelper dbHelper;
    private ArrayList<Book> bookArrayList;
    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DataBaseHelper(this);
        bookArrayList = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, bookArrayList);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab_add_book);
        fab.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddBookActivity.class)));

        loadBooks(); // Загружаем книги при создании активности
    }

    public void loadBooks() {
        bookArrayList.clear(); // Очищаем текущий список книг
        Cursor cursor = dbHelper.getAllBooks(); // Получаем все книги из базы данных

        if (cursor != null) { // Проверяем на null
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_ID));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_NAME));
                    String author = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_AUTHOR));
                    bookArrayList.add(new Book(id, name, author)); // Добавляем книгу в список
                } while (cursor.moveToNext());
            }
            cursor.close(); // Закрываем курсор после использования
        } else {
            Toast.makeText(this, "Нет книг для отображения", Toast.LENGTH_SHORT).show();
        }

        adapter.notifyDataSetChanged(); // Уведомляем адаптер об изменениях

        // Вызов метода для печати всех книг в лог
        printAllBooks();
    }

    public void printAllBooks() {
        Cursor cursor = dbHelper.getAllBooks();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_NAME));
                String author = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_AUTHOR));
                Log.d("Book", "ID: " + id + ", Name: " + name + ", Author: " + author);
            }
            cursor.close();
        } else {
            Log.d("Book", "Нет книг для отображения");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadBooks(); // Загружаем книги при возврате в активность
    }

    public void showDeleteConfirmationDialog(Book book) {
        new AlertDialog.Builder(this)
                .setTitle("Подтверждение удаления")
                .setMessage("Вы уверены, что хотите удалить \"" + book.getBook_Name() + "\"?")
                .setPositiveButton("Да", (dialog, which) -> deleteBook(book.getID_Book())) // Удаляем книгу по ID
                .setNegativeButton("Нет", null)
                .show();
    }

    private void deleteBook(int bookId) {
        boolean isDeleted = dbHelper.deleteBookById(bookId) > 0;
        if (isDeleted) {
            Toast.makeText(this, "Книга удалена", Toast.LENGTH_SHORT).show();
            loadBooks(); // Обновляем список книг
        } else {
            Toast.makeText(this, "Ошибка при удалении книги", Toast.LENGTH_SHORT).show();
        }
    }
}