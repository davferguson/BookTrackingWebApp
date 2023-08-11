package com.techelevator.dao;

import com.techelevator.model.Book;
import com.techelevator.model.Prize;
import com.techelevator.model.Reading;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
    public List<Reading> selectReadingWithinDateTime(String start_date, String end_date, int userId) {
        if(start_date != null && start_date.length() >= 2){
            start_date = start_date.substring(0, start_date.length()-2);
        }
        if(end_date != null && end_date.length() >= 2){
            end_date = end_date.substring(0, end_date.length()-2);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDate = LocalDateTime.parse(start_date, formatter);
        LocalDateTime endDate = LocalDateTime.parse(end_date, formatter);

        String sql = "SELECT * FROM reading_details WHERE user_id = ? AND log_date BETWEEN ? AND ?";
        List<Reading> readings = new ArrayList<>();
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, startDate, endDate);
        while (results.next()) {
            Reading reading = mapRowToReading(results);
            readings.add(reading);
        }
        return readings;
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
    private Reading mapRowToReading(SqlRowSet row){
        Reading reading = new Reading();
        reading.setUser_id(row.getInt("user_id"));
        reading.setBook_id(row.getInt("book_id"));
        reading.setIsbn(row.getString("isbn"));
        reading.setMinutes_read(row.getInt("minutes_read"));
        reading.setLog_date(row.getString("log_date"));
        return reading;
    }
}
