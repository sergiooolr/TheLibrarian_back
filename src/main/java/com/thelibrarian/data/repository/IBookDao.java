
package com.thelibrarian.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thelibrarian.data.entity.BookEntity;

@Repository
public interface IBookDao extends JpaRepository<BookEntity,Integer> {
    public BookEntity findByIsbn(String isbn);

}

