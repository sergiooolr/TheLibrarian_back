package com.thelibrarian;

import com.thelibrarian.integration.dto.BookDataDto;
import com.thelibrarian.integration.dto.BookDto;
import com.thelibrarian.integration.service.BookServiceImpl;
import com.thelibrarian.integration.utilities.AuthorsForTest;
import com.thelibrarian.integration.utilities.TitlesForTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

@SpringBootTest
class TheLibrarianApplicationTests {

    private static BookDataDto randomBooks;
    private static BookDto[] booksArray;
    private static BookServiceImpl bookService;

    @BeforeAll
    static void setUp() {
        bookService = new BookServiceImpl();
        randomBooks = bookService.getRandomBooks().getBody();
    }

    @Test
    @DisplayName("Test size of random books list")
    void getBooksCount() {
        Assertions.assertEquals(12, randomBooks.items.length);
    }

    @Test()
    @DisplayName("Test book language")
    void getException() {
        Exception exception = Assertions.assertThrows(NumberFormatException.class, () -> {
            Integer.parseInt(randomBooks.items[0].volumeInfo.getLanguage());
        });

        String expectedMessage = "For input string: \"" + randomBooks.items[0].volumeInfo.getLanguage() + "\"";
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Test book by author")
    void getBookByAuthor() {

        String authorActualName;

        for (AuthorsForTest author : AuthorsForTest.values()) {

            booksArray = Objects.requireNonNull(bookService.getBookByAuthor(author.name()).getBody()).getItems();

            for (BookDto book : booksArray) {
                if (book.getVolumeInfo().getAuthors() != null) {
                    for (int i = 0; i < book.getVolumeInfo().getAuthors().length; i++) {
                        if (book.getVolumeInfo().getAuthors()[i].contains(author.name())) {
                            authorActualName = book.getVolumeInfo().getAuthors()[i];
                            Assertions.assertEquals(authorActualName, book.getVolumeInfo().getAuthors()[i]);
                        }

                    }

                }

            }

        }
    }

    @Test
    @DisplayName("Test book by title")
    void getBookByTitle() {

        String actualTitle;

        for (TitlesForTest title : TitlesForTest.values()) {
            booksArray = Objects.requireNonNull(bookService.getBookByTitle(title.name()).getBody()).getItems();

            for (BookDto book : booksArray) {
                if (book.getVolumeInfo().getTitle().contains(title.name())) {
                    actualTitle = book.getVolumeInfo().getTitle();
                    Assertions.assertEquals(actualTitle, book.getVolumeInfo().getTitle());
                }

            }
        }
    }

    @Test
    @DisplayName("Test book by isbn")
    void getBookByIsbn() {
        booksArray = Objects.requireNonNull(bookService.getBookByIsbn("9780738702865").getBody()).getItems();
        Assertions.assertEquals("9780738702865", booksArray[0].getVolumeInfo().getIndustryIdentifiers()[0].get("identifier"));
    }

}
