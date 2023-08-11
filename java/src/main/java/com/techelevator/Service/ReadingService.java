package com.techelevator.Service;

import com.techelevator.dao.ReadingDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Book;
import com.techelevator.model.Reading;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReadingService {
    private ReadingDao readingDao;
    private UserDao userDao;

    public ReadingService(ReadingDao readingDao, UserDao userDao){
        this.readingDao = readingDao;
        this.userDao = userDao;

    }

   public void logReading(Reading reading){
        readingDao.logReading(reading);
   }
    public List<Reading> selectReadingWithinDateTime(String start_date, String end_date, int userId){
        return readingDao.selectReadingWithinDateTime(start_date, end_date, userId);
    }

    public void removeReadingLog(Book book, String username){
        readingDao.removeReadingLog(book, userDao.findIdByUsername(username));
    }
    public void removeAllReadingLogs(String username){
        readingDao.removeAllReadingLogs(userDao.findIdByUsername(username));
    }

}
