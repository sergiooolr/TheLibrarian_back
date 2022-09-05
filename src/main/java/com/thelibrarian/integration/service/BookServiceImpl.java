package com.thelibrarian.integration.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.thelibrarian.integration.dto.BookDataDto;
import com.thelibrarian.integration.dto.BookDto;
import com.thelibrarian.integration.utilities.Utilities;

@Service
public class BookServiceImpl implements BookService {

    static final String APIKEY = "&key=AIzaSyB57C58BGeh07Oe5kI63_IBAvjkdNhuCQA&maxResults=12";
    private static final String url = "https://www.googleapis.com/books/v1/volumes?q=";
    private final RestTemplate restTemplate = new RestTemplate();
    private String searchBooksHistoryUrl;

    @Override
    public ResponseEntity<BookDataDto> getRandomBooks() {

        String urlGetBook = url + Utilities.generateRandomTitles() + APIKEY;
        searchBooksHistoryUrl = urlGetBook;

        BookDataDto bookDataDto = restTemplate.getForObject(urlGetBook, BookDataDto.class);

        if (bookDataDto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(checkCorrectDataInsert(bookDataDto));

    }


    @Override
    public ResponseEntity<BookDataDto> getSearchHistoryBooks() {

        BookDataDto bookDataDto = restTemplate.getForObject(searchBooksHistoryUrl, BookDataDto.class);

        if (bookDataDto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(checkCorrectDataInsert(bookDataDto));

    }

    @Override
    public ResponseEntity<BookDataDto> searchBookByTitleAuthor(String title, String author) {

        String urlTitleAuthor = url + "+inauthor:" + author + "+intitle:" + title + APIKEY;

        BookDataDto bookDataDto = restTemplate.getForObject(urlTitleAuthor, BookDataDto.class);

        if (bookDataDto == null) {
            return ResponseEntity.notFound().build();
        }

        checkCorrectDataInsert(bookDataDto);
        checkAuthorExistence(bookDataDto, author);
        checkTitleExistence(bookDataDto, title);

        return ResponseEntity.ok().body(bookDataDto);
    }

    @Override
    public ResponseEntity<BookDataDto> getBookByIsbn(String isbn) {

        String urlIsbn = url + "isbn:" + isbn + APIKEY;
        searchBooksHistoryUrl = urlIsbn;
        BookDataDto bookDataDtoIsbn = restTemplate.getForObject(urlIsbn, BookDataDto.class);

        if (bookDataDtoIsbn == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(checkCorrectDataInsert(bookDataDtoIsbn));

    }

    @Override
    public ResponseEntity<BookDataDto> getBookByAuthor(String author) {

        String urlAuthor = url + "+inauthor:" + author + APIKEY;
        searchBooksHistoryUrl = urlAuthor;
        BookDataDto bookDataDto = restTemplate.getForObject(urlAuthor, BookDataDto.class);

        if (bookDataDto == null) {
            return ResponseEntity.notFound().build();
        }

        checkCorrectDataInsert(bookDataDto);
        checkAuthorExistence(bookDataDto, author);

        return new ResponseEntity<>(bookDataDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BookDataDto> getBookByTitle(String title) {

        String urlTitle = url + "+intitle:" + title + APIKEY;
        searchBooksHistoryUrl = urlTitle;
        BookDataDto bookDataDto = restTemplate.getForObject(urlTitle, BookDataDto.class);

        if (bookDataDto == null) {
            return ResponseEntity.notFound().build();
        }

        checkCorrectDataInsert(bookDataDto);
        checkTitleExistence(bookDataDto, title);

        return ResponseEntity.ok().body(bookDataDto);
    }

    @Override
    public ResponseEntity<BookDto> getBookById(String id) {

        String urlId = "https://www.googleapis.com/books/v1/volumes/" + id;

        BookDto bookDto = restTemplate.getForObject(urlId, BookDto.class);

        if (bookDto == null) {
            return ResponseEntity.notFound().build();
        }

        checkCorrectBookData(bookDto);

        return ResponseEntity.ok().body(bookDto);

    }

    private BookDto checkCorrectBookData(BookDto bookDto) {

        if (bookDto.getVolumeInfo().getTitle() == null) {
            bookDto.getVolumeInfo().setTitle("Informacion no disponible.");
        }

        if (bookDto.getVolumeInfo().getAuthors() == null) {
            String[] authors = {"No existe información."};
            bookDto.getVolumeInfo().setAuthors(authors);
        }

        if ((bookDto.getVolumeInfo().getPublishedDate() == null)) {
            bookDto.getVolumeInfo().setPublishedDate("No existe información.");
        }

        if (bookDto.getVolumeInfo().getDescription() == null) {
            bookDto.getVolumeInfo().setDescription("No hay descripción disponible.");
        }

        if (bookDto.getVolumeInfo().getImageLinks() == null) {
            Map<String, String> imageLinks = new HashMap<>();
            imageLinks.put("smallThumbnail", "No image available.");
            bookDto.getVolumeInfo().setImageLinks(imageLinks);
        }

        if (bookDto.getVolumeInfo().getCategories() == null) {
            String[] categories = {"No categories specified."};
            bookDto.getVolumeInfo().setCategories(categories);
        }

        if (bookDto.getVolumeInfo().getLanguage() == null) {
            bookDto.getVolumeInfo().setLanguage("Language not specified");
        }

        if (bookDto.getVolumeInfo().getIndustryIdentifiers() == null || bookDto.getVolumeInfo().getIndustryIdentifiers().length == 1) {
            Map<String, String> isbn = new HashMap<>();
            isbn.put("identifier", "No isbn available.");
            Map<String, String>[] industryIdentifiers = new HashMap[1];
            industryIdentifiers[0] = isbn;
            bookDto.getVolumeInfo().setIndustryIdentifiers(industryIdentifiers);
        } else {
            for (Map<String, String> isbn : bookDto.getVolumeInfo().getIndustryIdentifiers()) {

                if (isbn.get("type").equals("ISBN_13")) {
                    Map<String, String> newIsbn = new HashMap<>();
                    newIsbn.put("identifier", isbn.get("identifier"));
                    Map<String, String>[] industryIdentifiers = new HashMap[1];
                    industryIdentifiers[0] = newIsbn;
                    bookDto.getVolumeInfo().setIndustryIdentifiers(industryIdentifiers);

                    break;
                }
            }

        }

        return bookDto;

    }

    private BookDataDto checkCorrectDataInsert(BookDataDto bookDataDto) {

        for (int i = 0; i < bookDataDto.getItems().length; i++) {

            if (bookDataDto.getItems()[i].getVolumeInfo().getTitle() == null) {
                bookDataDto.getItems()[i].getVolumeInfo().setTitle("Informacion no disponible..");
            }

            if (bookDataDto.getItems()[i].getVolumeInfo().getAuthors() == null) {
                String[] authors = {"No existe información."};
                bookDataDto.getItems()[i].getVolumeInfo().setAuthors(authors);
            }

            if (bookDataDto.getItems()[i].getVolumeInfo().getPublishedDate() == null) {
                bookDataDto.getItems()[i].getVolumeInfo().setPublishedDate("No existe información.");
            }

            if (bookDataDto.getItems()[i].getVolumeInfo().getDescription() == null) {
                bookDataDto.getItems()[i].getVolumeInfo().setDescription("No hay descripción disponible.");
            }

            if (bookDataDto.getItems()[i].getVolumeInfo().getImageLinks() == null) {
                Map<String, String> imageLinks = new HashMap<>();
                imageLinks.put("smallThumbnail", "No image available.");
                bookDataDto.getItems()[i].getVolumeInfo().setImageLinks(imageLinks);
            }

            if (bookDataDto.getItems()[i].getVolumeInfo().getCategories() == null) {
                String[] categories = {"No categories specified."};
                bookDataDto.getItems()[i].getVolumeInfo().setCategories(categories);
            }

            if (bookDataDto.getItems()[i].getVolumeInfo().getLanguage() == null) {
                bookDataDto.getItems()[i].getVolumeInfo().setLanguage("Language not specified");
            }

            if (bookDataDto.getItems()[i].getVolumeInfo().getIndustryIdentifiers() == null || bookDataDto.getItems()[i].getVolumeInfo().getIndustryIdentifiers().length == 1) {
                Map<String, String> isbn = new HashMap<>();
                isbn.put("identifier", "No isbn available.");
                Map<String, String>[] industryIdentifiers = new HashMap[1];
                industryIdentifiers[0] = isbn;
                bookDataDto.getItems()[i].getVolumeInfo().setIndustryIdentifiers(industryIdentifiers);
            } else {
                for (Map<String, String> isbn : bookDataDto.getItems()[i].getVolumeInfo().getIndustryIdentifiers()) {

                    if (isbn.get("type").equals("ISBN_13")) {
                        Map<String, String> newIsbn = new HashMap<>();
                        newIsbn.put("identifier", isbn.get("identifier"));
                        Map<String, String>[] industryIdentifiers = new HashMap[1];
                        industryIdentifiers[0] = newIsbn;
                        bookDataDto.getItems()[i].getVolumeInfo().setIndustryIdentifiers(industryIdentifiers);

                        break;
                    }
                }

            }
        }

        return bookDataDto;
    }

    private BookDataDto checkAuthorExistence(BookDataDto bookDataDto, String author) {

        List<BookDto> booksList = new ArrayList<>();

        for (int i = 0; i < bookDataDto.getItems().length; i++) {

            for (String auth : bookDataDto.getItems()[i].getVolumeInfo().getAuthors()) {

                if (auth.toLowerCase().contains(author.toLowerCase())) {
                    booksList.add(bookDataDto.getItems()[i]);
                    break;
                }

            }

        }

        BookDto[] booksArray = new BookDto[booksList.size()];
        booksList.toArray(booksArray);
        bookDataDto.setItems(booksList.toArray(booksArray));

        return bookDataDto;
    }

    private BookDataDto checkTitleExistence(BookDataDto bookDataDto, String title) {

        List<BookDto> booksList = new ArrayList<>();

        for (int i = 0; i < bookDataDto.getItems().length; i++) {

            if (bookDataDto.getItems()[i].getVolumeInfo().getTitle().toLowerCase().contains(title.toLowerCase())) {
                booksList.add(bookDataDto.getItems()[i]);
            }

        }

        BookDto[] booksArray = new BookDto[booksList.size()];
        booksList.toArray(booksArray);
        bookDataDto.setItems(booksList.toArray(booksArray));

        return bookDataDto;
    }

}
