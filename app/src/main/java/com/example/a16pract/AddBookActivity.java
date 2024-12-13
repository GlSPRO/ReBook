package com.example.a16pract;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddBookActivity extends AppCompatActivity {
    private EditText editTextName, editTextAuthor; // Поля для ввода имени и автора книги
    private Button addButton; // Кнопка для добавления книги
    private DataBaseHelper dbHelper; // Помощник базы данных

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editTextName = findViewById(R.id.editTextName);
        editTextAuthor = findViewById(R.id.editTextAuthor);
        addButton = findViewById(R.id.add); // Инициализация кнопки добавления

        dbHelper = new DataBaseHelper(this); // Создание экземпляра помощника базы данных

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBookToDatabase(); // Вызов метода добавления книги при нажатии на кнопку
            }
        });
    }

    private void addBookToDatabase() {
        String bookName = editTextName.getText().toString().trim();
        String bookAuthor = editTextAuthor.getText().toString().trim();

        if (bookName.isEmpty() || bookAuthor.isEmpty()) { // Проверка на пустоту полей
            Toast.makeText(this, "Заполните поля", Toast.LENGTH_SHORT).show();
            return;
        }
        long result = dbHelper.addBook(bookName, bookAuthor); // Добавление книги в базу данных
        if (result > 0) { // Если добавление прошло успешно
            Toast.makeText(this, "Книга добавлена", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AddBookActivity.this, MainActivity.class)); // Переход на главную активность
            finish(); // Закрытие текущей активности
        } else {
            Toast.makeText(this, "Ошибка добавления книги", Toast.LENGTH_SHORT).show();
        }
    }
}