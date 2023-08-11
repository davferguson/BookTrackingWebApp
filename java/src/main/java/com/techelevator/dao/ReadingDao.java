package com.techelevator.dao;

import com.techelevator.model.Book;
import com.techelevator.model.Reading;

import java.util.List;

public interface ReadingDao {

    void logReading(Reading reading);
    List<Reading> selectReadingWithinDateTime(String start_date, String end_date, int userId);
    void removeReadingLog(Book book, int userId);
    void removeAllReadingLogs(int userId);
}
