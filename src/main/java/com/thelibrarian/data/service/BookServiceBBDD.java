
package com.thelibrarian.data.service;

import com.thelibrarian.data.dto.BookDto;
import com.thelibrarian.data.dto.BookingDto;
import com.thelibrarian.data.entity.BookEntity;
import com.thelibrarian.data.entity.UsersEntity;
import com.thelibrarian.data.repository.IBookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceBBDD implements IBookService {

    @Autowired
    private IBookDao bookDao;

    public List<BookDto> findAll() {

        return bookDao.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public ResponseEntity<BookDto> save(BookDto bookDto) {
        
        BookEntity book = findByIsbn(bookDto.getIsbn());
        
        
        try {

            if (book != null || bookDao.existsById(book.getId_book())) {
            
                return ResponseEntity.ok().body(bookDto);
            }
            return null;
            
        } catch (Exception e) {
    
            BookEntity bookEntity = new BookEntity();
    
            bookEntity.setId_book(bookDto.getId_book());
            bookEntity.setTitle(bookDto.getTitle());
            bookEntity.setPublishedDate(bookDto.getPublishedDate());
            bookEntity.setIsbn(bookDto.getIsbn());
            bookEntity.setDescription(bookDto.getDescription());
            bookEntity.setImageLinks(bookDto.getImageLinks());
            bookEntity.setPageCount(bookDto.getPageCount());
            bookEntity.setLanguage(bookDto.getLanguage());
            bookEntity.setPreviewLink(bookDto.getPreviewLink());

            bookDao.save(bookEntity);

            return ResponseEntity.ok().body(bookDto);
            
        }
        

        
    }

    public BookEntity findById(Integer id) {

        return bookDao.findById(id).orElse(null);
    }

    public void delete(Integer id) {

        bookDao.deleteById(id);

    }

    public BookEntity Update(BookEntity book, Integer id) {

        if (bookDao.existsById(id)) {

            book.setId_book(id);

            return bookDao.save(book);
        }
        return null;
    }

    @Override
    public BookEntity findByIsbn(String isbn) {

        return bookDao.findByIsbn(isbn);

    }

    public BookDto convertEntityToDto(BookEntity book) {

        BookDto bookDto = new BookDto();

        bookDto.setId_book(book.getId_book());
        bookDto.setTitle(book.getTitle());
        bookDto.setPublishedDate(book.getPublishedDate());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setDescription(book.getDescription());
        bookDto.setImageLinks(book.getImageLinks());
        bookDto.setPageCount(book.getPageCount());
        bookDto.setLanguage(book.getLanguage());

        return bookDto;
    }
}
