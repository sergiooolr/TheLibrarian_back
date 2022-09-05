package com.thelibrarian.data.repository;

import com.thelibrarian.data.dto.BookingDto;
import com.thelibrarian.data.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IReserveJpa extends JpaRepository <ReservationEntity,Integer> {


}
