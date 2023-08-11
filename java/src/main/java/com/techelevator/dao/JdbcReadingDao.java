package com.techelevator.dao;

import com.techelevator.model.Book;
import com.techelevator.model.Reading;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class JdbcReadingDao implements ReadingDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcReadingDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void logReading(Reading reading) {
        String sql = "INSERT INTO reading_details (user_id, book_id, isbn,  minutes_read, log_date) values(?, ?, ?, ?, ?) ";
        jdbcTemplate.update(sql,  reading.getUser_id(), reading.getBook_id(), reading.getIsbn(), reading.getMinutes_read(), LocalDateTime.now());

    }

    @Override
    public void removeReadingLog(Book book, int userId) {
        String sql = "DELETE FROM reading_details WHERE book_id = ? AND user_id = ?";
        jdbcTemplate.update(sql, book.getBook_id(), userId);
    }

    @Override
    public void removeAllReadingLogs(int userId) {
        String sql = "DELETE FROM reading_details WHERE user_id = ?";
        jdbcTemplate.update(sql, userId);
    }
}
