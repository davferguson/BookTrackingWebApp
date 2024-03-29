package com.techelevator.dao;

import com.techelevator.model.Book;
import com.techelevator.model.ChangeUsername;
import com.techelevator.model.User.User;

import java.security.Principal;
import java.util.List;

public interface UserDao {

    List<User> findAll();

    User getUserById(int userId);

    User findByUsername(String username);

    int findIdByUsername(String username);

    boolean create(String username, String password, String role);
    void deleteUser(int userId);
    void changeUsername(ChangeUsername changeUsername);

    void createFamilyAccount(String familyName, int userId);

    String getFamilyNameForUsername(String username);
    int getFamilyIdFromUsername(String username);
    void addToPrizeFamily(int familyId, int prizeId);

    void addFamilyMember(int curId, int addedId);
    void removeFamilyMember(int userId);

    void updateBookStatus(int userId, int bookId, String status);

    int getBooksByStatus(int userId, String status);

    int getMinutesRead(int userId);

    int minutesReadFromISBN(int userId, String ISBN);

    List<Book> currentlyReading(int userId);

    List<Book> finishedReading(int userId);

    List<User> familyMembers(int userId);


}
