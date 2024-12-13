package com.example.a16pract;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// Адаптер для RecyclerView, который отображает список книг
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context; // Контекст приложения или активности
    private ArrayList<Book> bookArrayList; // Список книг для отображения

    // Конструктор адаптера
    public RecyclerViewAdapter(Context context, ArrayList<Book> bookArrayList) {
        this.context = context; // Инициализация контекста
        this.bookArrayList = bookArrayList; // Инициализация списка книг
    }

    // Создание нового элемента ViewHolder при необходимости
    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Инфляция макета для каждого элемента списка (book_card.xml)
        View view = LayoutInflater.from(context).inflate(R.layout.book_card, parent, false);
        return new ViewHolder(view); // Возвращаем новый ViewHolder
    }

    // Привязка данных к элементу ViewHolder
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Book book = bookArrayList.get(position); // Получаем книгу по позиции
        holder.bookName.setText(book.getBook_Name()); // Устанавливаем имя книги в TextView
        holder.bookAuthor.setText(book.getBook_Author()); // Устанавливаем автора книги в TextView

        // Установка обработчика нажатия на элемент списка
        holder.itemView.setOnClickListener(v -> {
            Intent detailIntent = new Intent(context, BookDetailActivity.class); // Создаем Intent для перехода к деталям книги
            detailIntent.putExtra("BOOK_ID", book.getID_Book()); // Передаем ID книги в интент
            context.startActivity(detailIntent); // Запускаем активность деталей книги
        });
    }

    // Возвращает общее количество элементов в списке
    @Override
    public int getItemCount() {
        return bookArrayList.size(); // Возвращаем размер списка книг
    }

    // Внутренний класс ViewHolder для хранения ссылок на элементы интерфейса
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView bookName; // TextView для имени книги
        TextView bookAuthor; // TextView для автора книги

        // Конструктор ViewHolder
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookName = itemView.findViewById(R.id.b_name); // Инициализация TextView для имени книги
            bookAuthor = itemView.findViewById(R.id.b_author); // Инициализация TextView для автора книги
        }
    }
}