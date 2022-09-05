package com.thelibrarian.integration.service;

import com.thelibrarian.integration.dto.BookDto;
import org.springframework.http.ResponseEntity;

import com.thelibrarian.integration.dto.BookDataDto;

public interface BookService {
    ResponseEntity<BookDataDto> getRandomBooks();
    ResponseEntity<BookDataDto> getSearchHistoryBooks();
    ResponseEntity<BookDataDto> searchBookByTitleAuthor(String title, String author);
    ResponseEntity<BookDataDto> getBookByIsbn(String isbn);
    ResponseEntity<BookDataDto> getBookByAuthor(String author);
    ResponseEntity<BookDataDto> getBookByTitle(String title);
    ResponseEntity<BookDto> getBookById(String id);
}
