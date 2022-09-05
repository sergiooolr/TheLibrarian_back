package com.thelibrarian.core.controller;

import com.thelibrarian.integration.dto.BookDataDto;
import com.thelibrarian.integration.dto.BookDto;
import com.thelibrarian.integration.service.BookServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/get")
public class BookControllerApi {


    @Autowired
    BookServiceImpl bookService;


    @GetMapping(value = "/random")
    public ResponseEntity<BookDataDto> getBook() {

        return bookService.getRandomBooks();

    }

    @GetMapping(value = "/history")
    public ResponseEntity<BookDataDto> getBookHistory() {

        return bookService.getSearchHistoryBooks();

    }


    @GetMapping(value = "/getByTitleAuthor/{title}/{author}")
    public ResponseEntity<BookDataDto> getBookByTitleAuthor(@PathVariable String title, @PathVariable String author) {

        return bookService.searchBookByTitleAuthor(title, author);

    }


    @GetMapping(value = "/searchByIsbn/{isbn}")
    public ResponseEntity<BookDataDto> getBookByIsbn(@PathVariable String isbn) {

        try {
            return bookService.getBookByIsbn(isbn);
            
        } catch (Exception e) {
            return null;
        }

    }


    @GetMapping(value = "/author/{author}")
    public ResponseEntity<BookDataDto> findByAuthor(@PathVariable String author) {

        try {
            return bookService.getBookByAuthor(author);
        } catch (Exception e) {
            return null;
        }

    }


    @GetMapping(value = "searchByTitle/{title}")
    public ResponseEntity<BookDataDto> findByTitle(@PathVariable String title) {

        try{
            return bookService.getBookByTitle(title);
        }catch (Exception e){
            return null;
        }

    }

    @GetMapping(value = "/getById/{id}")
    public ResponseEntity<BookDto> findById(@PathVariable String id) {

        return bookService.getBookById(id);

    }

}
