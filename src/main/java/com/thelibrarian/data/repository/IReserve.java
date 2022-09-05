package com.thelibrarian.data.repository;



import com.thelibrarian.data.entity.ReservationEntity;
import com.thelibrarian.data.entity.UsersEntity;
import org.springframework.http.ResponseEntity;

import com.thelibrarian.data.dto.BookingDto;

import java.awt.print.Book;
import java.util.List;


public interface IReserve {


   public ResponseEntity<BookingDto> createReserve(BookingDto booking);


   public List<ReservationEntity> reservedBooksByUserId(Integer id);

   public List<ReservationEntity> findAllByUserId(Integer id);


    
}
