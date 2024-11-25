package com.example.a16pract;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BookDetailActivity extends AppCompatActivity {
    private TextView textViewName, textViewAuthor;
    private Button buttonDelete, buttonEdit; // Добавляем кнопку редактирования
    private DataBaseHelper dbHelper;
    private int bookId; // ID книги для удаления и редактирования

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        textViewName = findViewById(R.id.textViewName);
        textViewAuthor = findViewById(R.id.textViewAuthor);
        buttonDelete = findViewById(R.id.buttonDelete); // Инициализация кнопки удаления
        buttonEdit = findViewById(R.id.buttonEdit); // Инициализация кнопки редактирования

        dbHelper = new DataBaseHelper(this);

        // Получаем ID книги из Intent
        Intent intent = getIntent();
        bookId = intent.getIntExtra("BOOK_ID", -1);
        if (bookId == -1) {
            Toast.makeText(this, "Неверный ID книги", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Загружаем информацию о книге
        loadBookDetails();

        // Устанавливаем обработчик нажатия на кнопку удаления
        buttonDelete.setOnClickListener(v -> deleteBook());

        // Устанавливаем обработчик нажатия на кнопку редактирования
        buttonEdit.setOnClickListener(v -> editBook());
    }

    private void loadBookDetails() {
        Cursor cursor = dbHelper.getBookById(bookId);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_NAME));
                String author = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_AUTHOR));
                textViewName.setText(name);
                textViewAuthor.setText(author);
            } else {
                Toast.makeText(this, "Книга не найдена", Toast.LENGTH_SHORT).show();
                finish(); // Закрываем активность, если книга не найдена
            }
            cursor.close();
        } else {
            Toast.makeText(this, "Ошибка при получении данных о книге", Toast.LENGTH_SHORT).show();
            finish(); // Закрываем активность в случае ошибки
        }
    }

    private void deleteBook() {
        boolean isDeleted = dbHelper.deleteBookById(bookId) > 0; // Удаляем книгу по ID
        if (isDeleted) {
            Toast.makeText(this, "Книга удалена", Toast.LENGTH_SHORT).show();
            finish(); // Закрываем активность после удаления
        } else {
            Toast.makeText(this, "Ошибка при удалении книги", Toast.LENGTH_SHORT).show();
        }
    }

    private void editBook() {
        Intent editIntent = new Intent(BookDetailActivity.this, EditBookActivity.class);
        editIntent.putExtra("BOOK_ID", bookId); // Передаем ID книги для редактирования
        startActivity(editIntent); // Запускаем EditBookActivity
    }
}