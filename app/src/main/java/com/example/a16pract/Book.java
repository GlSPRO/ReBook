package com.example.a16pract;

public class Book {
    private int ID_Book;
    private String Book_Name;
    private String Book_Author;
    public Book (int ID_Book, String book_Name, String book_Author)
    {
        this.ID_Book=ID_Book;
        this.Book_Author=book_Author;
        this.Book_Name=book_Name;
    }
    public void setID_Book(int ID_Book) {
        this.ID_Book = ID_Book;
    }

    public int getID_Book() {
        return ID_Book;
    }

    public void setBook_Author(String book_Author) {
        Book_Author = book_Author;
    }

    public String getBook_Author() {
        return Book_Author;
    }

    public String getBook_Name() {
        return Book_Name;
    }

    public void setBook_Name(String book_Name) {
        Book_Name = book_Name;
    }
}
