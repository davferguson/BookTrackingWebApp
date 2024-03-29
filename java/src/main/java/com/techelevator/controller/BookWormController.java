package com.techelevator.controller;

import com.techelevator.Service.*;
import com.techelevator.dao.UserDao;
import com.techelevator.model.*;
import com.techelevator.model.User.User;
import com.techelevator.model.openlibraryAPI.SearchResult;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.security.Principal;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class BookWormController {
    private ParentService parentService;
    private BookService bookService;
    private UserDao userDao;
    private ReadingService readingService;
    private UserService userService;
    private PrizeService prizeService;
    private ApiService apiService;

    BookWormController(ParentService parentService, BookService bookService, UserDao userDao, ReadingService readingService, UserService userService, PrizeService prizeService, ApiService apiService){
        this.parentService = parentService;
        this.bookService = bookService;
        this.userDao = userDao;
        this.readingService = readingService;
        this.userService = userService;
        this.prizeService = prizeService;
        this.apiService = apiService;
    }

    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/book_search", method = RequestMethod.POST)
    public SearchResult apiBookSearch(@RequestBody String[] keywords) {
        return apiService.apiBookSearch(keywords);
    }

    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/google_book_search", method = RequestMethod.POST)
    public SearchResult googleAPIBookSearch(@RequestBody String[] keywords) {
        return apiService.googleAPIBookSearch(keywords);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/register_child", method = RequestMethod.POST)
    public void createChildAccount(@Valid @RequestBody RegisterUserDTO newUser) {
        parentService.createChildAccount(newUser.getUsername(), newUser.getPassword());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/register_family_account", method = RequestMethod.POST)
    public void createFamilyAccount(@Valid @RequestBody Family familyInfo, Principal curUser) {
        parentService.createFamilyAccount(familyInfo.getFamilyName(), curUser.getName());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/family_name")
    public String getFamilyForId(Principal curUser) {
        return parentService.getFamilyForUser(curUser.getName());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/add_family_member", method = RequestMethod.POST)
    public void addFamilyMember(@RequestBody User child, Principal curUser) {
        parentService.addFamilyMember(child.getUsername(), curUser.getName());
    }



    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/add_book", method = RequestMethod.POST)
    public void addBook(@RequestBody Book newBook, Principal curUser){
        bookService.createBook(newBook, curUser.getName());
    }
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/remove_book", method = RequestMethod.POST)
    public void removeBook(@RequestBody Book book, Principal curUser){
        bookService.removeBook(book, curUser.getName());
        readingService.removeReadingLog(book, curUser.getName());
    }
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/delete_user", method = RequestMethod.POST)
    public void deleteUser(Principal curUser){
        bookService.removeAllBooks(curUser.getName());
        readingService.removeAllReadingLogs(curUser.getName());
        userService.removeFamilyMember(curUser.getName());
        userService.deleteUser(curUser.getName());
        System.out.println("DELETED.");
    }
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/change_username", method = RequestMethod.POST)
    public void changeUsername(@RequestBody ChangeUsername changeUsername){
//        newUsername = newUsername.substring(0, newUsername.length()-1);
        userService.changeUsername(changeUsername);
    }

    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/log_reading", method = RequestMethod.POST)
    public void logReading(@RequestBody Reading reading) {
        reading.setBook_id(bookService.getIdByIsbn(reading.getIsbn()));
        reading.setUser_id(userDao.findIdByUsername(reading.getUsername()));
        readingService.logReading(reading);
        userService.updateBookStatus(reading.getUser_id(), reading.getBook_id(), "READING");
    }

    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public List<Book> userReadingList(Principal curUser) {

        return bookService.userReadingList(userDao.findIdByUsername(curUser.getName()));
    }
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/books_finished_within_date_time", method = RequestMethod.GET)
    public List<Book> selectFinishedBooksWithinDateTime(@RequestParam String startDate, @RequestParam String endDate, @RequestParam String username) {
        int userId = userDao.findIdByUsername(username);
        return bookService.selectFinishedBooksWithinDateTime(startDate, endDate, userId);
    }

    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/update_book_status", method = RequestMethod.PUT)
    public void updateBookStatus(@RequestBody List<String> data, Principal curUser) {
        userService.updateBookStatus(userDao.findIdByUsername(curUser.getName()), bookService.getIdByIsbn(data.get(0)), data.get(1));
    }

    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/reading_within_date_time", method = RequestMethod.GET)
    public List<Reading> selectReadingWithinDateTime(@RequestParam String startDate, @RequestParam String endDate, @RequestParam String username) {
        int userId = userDao.findIdByUsername(username);
        return readingService.selectReadingWithinDateTime(startDate, endDate, userId);
     }
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/check_reading_activity/{username}", method = RequestMethod.GET)
    public ReadingActivity checkReadingActivity(@PathVariable String username) {

        int userid = userDao.findIdByUsername(username);
        ReadingActivity readingActivity = new ReadingActivity( userService.getMinutesRead(userid), userService.getBooksByStatus(userid, "READ"),userService.getBooksByStatus(userid, "READING"));

        return readingActivity;

    }

    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/minutes_read/{username}/{isbn}", method = RequestMethod.GET)
    public int minutesReadFromISBN(@PathVariable String username, @PathVariable String isbn){
        return userService.minutesReadFromISBN(userDao.findIdByUsername(username), isbn);
    }

    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/currently_reading", method = RequestMethod.GET)
    public List<Book> currentlyReading(Principal user){
        return userService.currentlyReading(userDao.findIdByUsername(user.getName()));
    }

    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/finished_reading", method = RequestMethod.GET)
    public List<Book> finishedReading(Principal user){
        return userService.finishedReading(userDao.findIdByUsername(user.getName()));
    }

    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/check_reading_activity_child", method = RequestMethod.GET)
    public ReadingActivity checkReadingActivityChild(Principal curUser) {

        int userid = userDao.findIdByUsername(curUser.getName());
        ReadingActivity readingActivity = new ReadingActivity( userService.getMinutesRead(userid), userService.getBooksByStatus(userid, "READ"),userService.getBooksByStatus(userid, "READING"));

        return readingActivity;

    }

    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/family_members", method = RequestMethod.GET)
    public List<User> familyMembers(Principal principal) {
        int userId = userDao.findIdByUsername(principal.getName());
        List<User> users =userService.familyMembers(userId);
        return users;
    }

    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/prizes", method = RequestMethod.GET)
    public List<Prize> prizeList() {
        return prizeService.findAll();
    }
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/available_prizes", method = RequestMethod.GET)
    public List<Prize> listAvailablePrizes(@RequestParam int familyId) {
        return prizeService.listAvailablePrizes(familyId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/create_prize", method = RequestMethod.POST)
    public void createPrize(@RequestBody Prize prize, @RequestParam int family_id) {
        int prizeId = prizeService.createPrize(prize);
        userService.addToPrizeFamily(family_id, prizeId);
    }
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/find_family_id", method = RequestMethod.GET)
    public int getFamilyIdFromUsername(@RequestParam String username) {
        return userService.getFamilyIdFromUsername(username);
    }
}
