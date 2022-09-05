
package com.thelibrarian.core.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.thelibrarian.data.dto.BookDto;
import com.thelibrarian.data.entity.BookEntity;
import com.thelibrarian.data.service.BookServiceBBDD;

import io.swagger.v3.oas.models.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RestController
    @RequiredArgsConstructor
    public class BookControllerBBDD {

        @Autowired
        BookServiceBBDD bookService;

        @Autowired
        ModelMapper modelMapper;

        @GetMapping("/getAllBooks")
        public List<BookDto> findAll() {

            return bookService.findAll();

        }

        @PostMapping("/createBook")
        @ResponseStatus(code = HttpStatus.CREATED)
        public ResponseEntity <BookDto> save (@RequestBody BookDto bookDto) {
        
        return bookService.save(bookDto);

        }

        @DeleteMapping("/deleteBook/{id}")
        public ResponseEntity <ApiResponse> delete(@PathVariable Integer id) {

            bookService.delete(id);

            ApiResponse apiResponse = new ApiResponse();

            return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);

        }

        @PutMapping("/updateBook/{id}")
        public ResponseEntity<BookDto> Update(@PathVariable Integer id,@RequestBody BookDto bookDto) {

            BookEntity bookRequest = modelMapper.map(bookDto,BookEntity.class);

            BookEntity book = bookService.Update(bookRequest,id);

            BookDto bookResponse = modelMapper.map(book,BookDto.class);

            return ResponseEntity.ok().body(bookResponse);

        }

        @GetMapping("/getByIdBook/{id}")
        @ResponseBody
        public  ResponseEntity <BookDto> findById(@PathVariable Integer id) {

            BookEntity book = bookService.findById(id);

            BookDto bookDto = modelMapper.map(book,BookDto.class);

            return ResponseEntity.ok().body(bookDto);


        }

        @GetMapping("/getByIsbn/{isbn}")
        public ResponseEntity <BookDto> findByIsbn(@PathVariable String isbn) {

            BookEntity book = bookService.findByIsbn(isbn);

            BookDto bookDto = modelMapper.map(book,BookDto.class);

            return ResponseEntity.ok().body(bookDto);

        }

    }

