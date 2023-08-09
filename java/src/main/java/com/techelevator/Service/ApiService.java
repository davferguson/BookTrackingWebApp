package com.techelevator.Service;

import com.techelevator.model.Book;
import com.techelevator.model.BookSearchAPI;
import com.techelevator.model.GoogleBooksAPI.GoogleBookSearchResult;
import com.techelevator.model.openlibraryAPI.SearchResult;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class ApiService {
    public SearchResult apiBookSearch(String[] keywords){
        String urlStart = "https://openlibrary.org/search.json?q=";
        String url = generateURLFromSearch(urlStart, keywords);
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

    public SearchResult googleAPIBookSearch(String[] keywords){
        String urlStart = "https://www.googleapis.com/books/v1/volumes?q=";
        String url = generateURLFromSearch(urlStart, keywords);
        RestTemplate restTemplate = new RestTemplate();
        GoogleBookSearchResult googleBookSearchResult = restTemplate.getForObject(url, GoogleBookSearchResult.class);
        SearchResult searchResult = new SearchResult();
        searchResult.setNumFound(googleBookSearchResult.getTotalItems());
        List<Book> books = new ArrayList<>();
        for(int i = 0; i < googleBookSearchResult.getItems().length; i++){
            Book book = new Book();
            //does item exist
            if(googleBookSearchResult.getItems()[i] != null){
                //does volumeInfo exist
                if(googleBookSearchResult.getItems()[i].getVolumeInfo() != null){
                    book.setBook_name(googleBookSearchResult.getItems()[i].getVolumeInfo().getTitle());
                    //does authors exist
                    if(googleBookSearchResult.getItems()[i].getVolumeInfo().getAuthors() != null){
                        book.setAuthor(googleBookSearchResult.getItems()[i].getVolumeInfo().getAuthors()[0]);
                    }
                    //does industryIdentifiers exist
                    if(googleBookSearchResult.getItems()[i].getVolumeInfo().getIndustryIdentifiers() != null){
                        if(googleBookSearchResult.getItems()[i].getVolumeInfo().getIndustryIdentifiers().length > 1){
                            book.setIsbn(googleBookSearchResult.getItems()[i].getVolumeInfo().getIndustryIdentifiers()[1].getIdentifier());
                        } else {
                            book.setIsbn(googleBookSearchResult.getItems()[i].getVolumeInfo().getIndustryIdentifiers()[0].getIdentifier());
                        }

                    }
                    //does imageLinks exist
                    if(googleBookSearchResult.getItems()[i].getVolumeInfo().getImageLinks() != null){
                        book.setImageLink(googleBookSearchResult.getItems()[i].getVolumeInfo().getImageLinks().getThumbnail());
                    }
                }
            }
            books.add(book);
        }
        searchResult.setBooks(books);
        return searchResult;
    }

    private String generateURLFromSearch(String urlStart, String[] keywords){
        String url = urlStart;
        for(int i = 0; i < keywords.length; i++){
            url += keywords[i];
            if(i != keywords.length-1){
                url += "+";
            }
        }
        return url;
    }
}
