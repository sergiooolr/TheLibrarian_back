package com.thelibrarian.data.service;

import com.thelibrarian.data.entity.BookEntity;

public interface IBookService {

    public BookEntity findByIsbn(String isbn);

}
