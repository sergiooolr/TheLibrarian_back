package com.thelibrarian.data.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.thelibrarian.data.dto.BookingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.thelibrarian.data.entity.ReservationEntity;
import com.thelibrarian.data.repository.IReserve;
import com.thelibrarian.data.repository.IReserveJpa;

@Service
public class ReservationServiceBBDD implements IReserve {

    @Autowired
    IReserveJpa reserve;

    @Autowired
    UserServiceBBDD userService;


    public List<ReservationEntity> findAll() {
        return reserve.findAll();
    }

    @Override
    public ResponseEntity<BookingDto> createReserve(BookingDto booking) {

        List<ReservationEntity> reserveListOfUser = userService.findById(booking.getId_usuario()).getReservation();

        int count = 0;

        if (reserveListOfUser != null) {

            for (ReservationEntity reservationEntity : reserveListOfUser) {

                if (reservationEntity.getIs_reservado()) {

                    count++;

                    if (count == 3) {
                        break;
                    }

                }

            }

            if (count < 3) {

                ReservationEntity reservationEntity = new ReservationEntity();

                reservationEntity.setId(booking.getId());
                reservationEntity.setId_book(booking.getId_book());
                reservationEntity.setId_usuario(booking.getId_usuario());
                reservationEntity.setIs_reservado(booking.getIs_reservado());

                reserve.save(reservationEntity);

                return ResponseEntity.ok().body(booking);

            } else {
                return ResponseEntity.notFound().build();
            }

        } else {
            return ResponseEntity.notFound().build();
        }

    }


    public List<ReservationEntity> findAllByUserId(Integer id) {

        return userService.findById(id).getReservation();

    }

    public ReservationEntity findById(Integer id) {

    return reserve.findById(id).orElse(null);
    }


    public ReservationEntity Update(ReservationEntity reservation, Integer id) {

        if (reserve.existsById(id)) {

            reservation.setId(id);

            return reserve.save(reservation);
        }

        return null;
    }

    @Override
    public List<ReservationEntity> reservedBooksByUserId(Integer id) {

        List<ReservationEntity> books = new ArrayList<>();

            for (ReservationEntity book : findAllByUserId(id)) {
                if (book.getIs_reservado()) {
                    books.add(book);
                }
            }

        return books;

    }

}
