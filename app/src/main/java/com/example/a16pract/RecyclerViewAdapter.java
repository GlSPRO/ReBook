package com.example.a16pract;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Book> bookArrayList;

    public RecyclerViewAdapter(Context context, ArrayList<Book> bookArrayList) {
        this.context = context;
        this.bookArrayList = bookArrayList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Book book = bookArrayList.get(position);
        holder.bookName.setText(book.getBook_Name());
        holder.bookAuthor.setText(book.getBook_Author());

        // Установка обработчика нажатия на элемент списка
        holder.itemView.setOnClickListener(v -> {
            Intent detailIntent = new Intent(context, BookDetailActivity.class);
            detailIntent.putExtra("BOOK_ID", book.getID_Book());
            context.startActivity(detailIntent);
        });
    }

    @Override
    public int getItemCount() {
        return bookArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView bookName, bookAuthor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookName = itemView.findViewById(R.id.b_name);
            bookAuthor = itemView.findViewById(R.id.b_author);
        }
    }
}