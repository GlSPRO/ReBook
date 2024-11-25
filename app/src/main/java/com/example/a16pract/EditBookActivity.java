package com.example.a16pract;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditBookActivity extends AppCompatActivity {
    private EditText editTextName, editTextAuthor; // Убрали editTextDescription
    private Button updateButton;
    private DataBaseHelper dbHelper;
    private int bookId; // ID книги для обновления

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        editTextName = findViewById(R.id.editTextName);
        editTextAuthor = findViewById(R.id.editTextAuthor);
        updateButton = findViewById(R.id.updateButton);

        dbHelper = new DataBaseHelper(this);

        // Получаем ID книги из Intent
        Intent intent = getIntent();
        bookId = intent.getIntExtra("BOOK_ID", -1);

        if (bookId == -1) {
            Toast.makeText(this, "Неверный ID книги", Toast.LENGTH_SHORT).show();
            finish(); // Закрываем активность, если ID некорректен
            return;
        }

        // Загружаем информацию о книге
        loadBookDetails();

        updateButton.setOnClickListener(v -> updateBookInDatabase());
    }

    private void loadBookDetails() {
        Log.d("EditBookActivity", "Загружаем книгу с ID: " + bookId);
        Cursor cursor = dbHelper.getBookById(bookId);

        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_NAME));
            String author = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_AUTHOR));
            editTextName.setText(name);
            editTextAuthor.setText(author);
            cursor.close();
        } else {
            Toast.makeText(this, "Книга не найдена", Toast.LENGTH_SHORT).show();
            finish(); // Закрываем активность, если книга не найдена
        }
    }

    private void updateBookInDatabase() {
        String bookName = editTextName.getText().toString().trim();
        String bookAuthor = editTextAuthor.getText().toString().trim();

        if (bookName.isEmpty() || bookAuthor.isEmpty()) { // Убрали проверку на описание
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            boolean isUpdated = dbHelper.updateBook(bookId, bookName, bookAuthor, null); // Передаем null вместо описания
            if (isUpdated) {
                Toast.makeText(this, "Книга обновлена", Toast.LENGTH_SHORT).show();
                finish(); // Закрываем активность после обновления
            } else {
                Log.e("EditBookActivity", "Ошибка при обновлении книги: книга не найдена или данные не изменены");
                Toast.makeText(this, "Ошибка при обновлении книги", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("EditBookActivity", "Error updating book", e);
            Toast.makeText(this, "Ошибка при обновлении книги", Toast.LENGTH_SHORT).show();
        }
    }
}