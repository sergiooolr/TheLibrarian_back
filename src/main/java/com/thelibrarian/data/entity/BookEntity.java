package com.thelibrarian.data.entity;


import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Data
@Table(name="BOOKS")
@NoArgsConstructor
@AllArgsConstructor
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_book;

    private String title;
    private String publishedDate;
    private String isbn;
    private String description;
    private String imageLinks;
    private int pageCount;
    private String language;
    private String previewLink;

    @JsonBackReference
    @OneToMany(targetEntity = ReservationEntity.class, mappedBy = "id_book", orphanRemoval = false, fetch = FetchType.LAZY)
	private List<ReservationEntity> reserva;

    
}
