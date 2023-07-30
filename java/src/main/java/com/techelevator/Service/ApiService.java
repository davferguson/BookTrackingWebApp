package com.techelevator.Service;

import com.techelevator.model.Book;
import com.techelevator.model.BookSearchAPI;
import com.techelevator.model.openlibraryAPI.SearchResult;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class ApiService {

    public SearchResult apiBookSearch(String[] keywords){
        String url = "https://openlibrary.org/search.json?q=";
        for(int i = 0; i < keywords.length; i++){
            url += keywords[i];
            if(i != keywords.length-1){
                url += "+";
            }
        }
        RestTemplate restTemplate = new RestTemplate();
        BookSearchAPI bookSearchAPI = restTemplate.getForObject(url, BookSearchAPI.class);

        SearchResult searchResult = new SearchResult();
        searchResult.setNumFound(bookSearchAPI.getNumFound());
        List<Book> books = new ArrayList<>();
        int numOfResults = 10;
        for(int i = 0; i < numOfResults && i < bookSearchAPI.getNumFound(); i++){
            Book book = new Book();
            if(bookSearchAPI.getDocs().get(i).getIsbn() != null){
                book.setIsbn(bookSearchAPI.getDocs().get(i).getIsbn()[0]);
            }
            book.setBook_name(bookSearchAPI.getDocs().get(i).getTitle());
            if(bookSearchAPI.getDocs().get(i).getAuthor() != null){
                book.setAuthor(bookSearchAPI.getDocs().get(i).getAuthor()[0]);
            }
            books.add(book);
        }
        searchResult.setBooks(books);

        return searchResult;
    }
}
