package com.thelibrarian.core.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.thelibrarian.data.repository.IReserve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.DocumentException;
import com.thelibrarian.data.dto.BookingDto;
import com.thelibrarian.data.entity.ReservationEntity;
import com.thelibrarian.data.repository.IBookDao;
import com.thelibrarian.data.service.ReservationServiceBBDD;
import com.thelibrarian.integration.ReservationPDF;

import lombok.RequiredArgsConstructor;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/reserve")
public class ReservationControllerBBDD {

    @Autowired
    ReservationServiceBBDD reservationService;

    @Autowired
    IBookDao bookDao;


    @GetMapping("/getAllReservation")
    public List<ReservationEntity> findAll() {

        return reservationService.findAll();
    }


    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<BookingDto> insert(@RequestBody BookingDto booking){

        return reservationService.createReserve(booking);
    }

    @PutMapping("/updateReserve/{id}")
    public ResponseEntity<ReservationEntity> Update(@RequestBody ReservationEntity reservation, @PathVariable Integer id) {
        ReservationEntity reservation1 = reservationService.Update(reservation, id);

        return ResponseEntity.ok().body(reservation1);
    }

    @GetMapping("/export/pdf/{id}")
    public void exportToPDF(HttpServletResponse response, @PathVariable Integer id) throws DocumentException, IOException, ParseException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<ReservationEntity> list = reservationService.findAllByUserId(id);

        ReservationPDF exporter = new ReservationPDF(list);
        exporter.exportUser(response);

    }

    @GetMapping("/reservedBooksByUserId/{id}")
    public List<ReservationEntity> getReservedBooks(@PathVariable Integer id) {
        return reservationService.reservedBooksByUserId(id);
    }

    @GetMapping("/getAllReservationById/{id}")
    public List<ReservationEntity> findAllByUserId(@PathVariable Integer id) {

        return reservationService.findAllByUserId(id);

    }


    
}
