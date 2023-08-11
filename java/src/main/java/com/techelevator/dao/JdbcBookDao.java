package com.techelevator.dao;

import com.techelevator.model.Book;
import com.techelevator.model.Reading;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcBookDao implements BookDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcBookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createBook(Book book, int userId){
        String bookCountSQL = "Select book_id from book where isbn = ?";
        String recordCountSQL = ("Select count(*) from book_user where book_id = ? and user_id = ?");
        Integer curBookId = 0;
        Integer bookUserExists = 0;
        String sql;

        try {
            curBookId = jdbcTemplate.queryForObject(bookCountSQL, Integer.class, book.getIsbn());
//            System.out.println("loop0  BookId : " + curBookId + " -- UserId : " + userId);
        }catch (DataAccessException e) {
//            return;
        }

        try {
            bookUserExists = jdbcTemplate.queryForObject(recordCountSQL, Integer.class, book.getBook_id(), userId);
//            System.out.println("loopA  BookId : " + curBookId + " -- Book_User Record count : " + bookUserExists + " -- UserId : " + userId);

        }catch (DataAccessException e) {
            e.getMessage();
//            return;
        }


        if(curBookId == 0){
//            System.out.println("loop1  BookId : " + curBookId + " -- Book_User Record count : " + bookUserExists + " -- UserId : " + userId);
            Integer bookId;
            sql = "INSERT INTO book (book_name, isbn, description, author, numberofpages, rating, genre, image_link) values (?, ?, ?, ?, ?, ?, ?, ?) RETURNING book_id";

            try {
                bookId = jdbcTemplate.queryForObject(sql, Integer.class, book.getBook_name(), book.getIsbn(), book.getDescription(), book.getAuthor(), book.getNumberofpages(), book.getRating(), book.getGenre(), book.getImageLink());
            } catch (DataAccessException e) {
                return;
            }

            sql = "INSERT INTO book_user (book_id, user_id) values (?, ?)";
            try {
                jdbcTemplate.update(sql, bookId, userId);
            } catch (DataAccessException e) {
                return;
            }

        }else if (curBookId != 0 && bookUserExists == 0) {
//            System.out.println("loop2  BookId : " + curBookId + " -- Book_User Record count : " + bookUserExists + " -- UserId : " + userId);
            sql = "INSERT INTO book_user (book_id, user_id) values (?, ?)";
            try {
                jdbcTemplate.update(sql, curBookId, userId);
            } catch (DataAccessException e) {
                return;
            }

        }
    }

    @Override
    public void removeBook(Book book, int userId) {
        String sql = "DELETE FROM book_user WHERE book_id = ? AND user_id = ?";
        jdbcTemplate.update(sql, book.getBook_id(), userId);
    }

    @Override
    public void removeAllBooks(int userId) {
        String sql = "DELETE FROM book_user WHERE user_id = ?";
        jdbcTemplate.update(sql, userId);
    }

    public int getIdByIsbn(String Isbn){
        String sql = "SELECT book_id FROM book WHERE isbn = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, Isbn);
    }

    public ArrayList<Book> userReadingList(int userId){
        String sql = "SELECT b.* FROM book_user bu JOIN book b ON bu.book_id = b.book_id WHERE bu.user_id = ?";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, userId);

        ArrayList<Book> books = new ArrayList<>();
        while(rows.next()){
            books.add(mapRowToBook(rows));
        }
        return books;
    }

    @Override
    public List<Book> selectBooksWithinDateTime(String start_date, String end_date, int userId) {
        if(start_date != null && start_date.length() >= 2){
            start_date = start_date.substring(0, start_date.length()-2);
        }
        if(end_date != null && end_date.length() >= 2){
            end_date = end_date.substring(0, end_date.length()-2);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if(start_date != null && end_date != null){
            LocalDateTime startDate = LocalDateTime.parse(start_date, formatter);
            LocalDateTime endDate = LocalDateTime.parse(end_date, formatter);

            String sql = "SELECT * FROM book b JOIN book_user bu ON b.book_id = bu.book_id WHERE bu.user_id = ? AND bu.finish_date BETWEEN ? AND ?";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, startDate, endDate);
            List<Book> books = new ArrayList<>();
            while (results.next()) {
                Book book = mapRowToBook(results);
                books.add(book);
            }
            return books;
        }
        return null;
    }

    private Book mapRowToBook(SqlRowSet row){
        Book book = new Book();
        book.setDescription(row.getString("description"));
        book.setAuthor(row.getString("author"));
        book.setNumberofpages(row.getInt("numberofpages"));
        book.setBook_id(row.getInt("book_id"));
        book.setIsbn(row.getString("isbn"));
        book.setGenre(row.getString("genre"));
        book.setRating(row.getString("rating"));
        book.setFormat(row.getString("format"));
        book.setBook_name(row.getString("book_name"));
        book.setImageLink(row.getString("image_link"));
        return book;
    }
}
