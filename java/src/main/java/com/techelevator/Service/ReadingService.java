package com.techelevator.Service;

import com.techelevator.dao.ReadingDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Book;
import com.techelevator.model.Reading;
import org.springframework.stereotype.Component;

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

    public void removeReadingLog(Book book, String username){
        readingDao.removeReadingLog(book, userDao.findIdByUsername(username));
    }
    public void removeAllReadingLogs(String username){
        readingDao.removeAllReadingLogs(userDao.findIdByUsername(username));
    }

}
