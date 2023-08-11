package com.techelevator.dao;

import com.techelevator.model.Book;

import java.util.List;

public interface BookDao {

    void createBook(Book book, int userId);
    void removeBook(Book book, int userId);
    void removeAllBooks(int userId);

    List<Book> userReadingList(int userId);
    List<Book> selectBooksWithinDateTime(String start_date, String end_date, int userId);

    int getIdByIsbn(String Isbn);

}
