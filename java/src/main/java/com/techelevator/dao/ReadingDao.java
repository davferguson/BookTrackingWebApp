package com.techelevator.dao;

import com.techelevator.model.Book;
import com.techelevator.model.Reading;

public interface ReadingDao {

    void logReading(Reading reading);
    void removeReadingLog(Book book, int userId);
    void removeAllReadingLogs(int userId);
}
