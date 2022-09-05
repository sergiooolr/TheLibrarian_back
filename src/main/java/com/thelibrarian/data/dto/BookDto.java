package com.thelibrarian.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id_book;

    private String title;
    private String publishedDate;
    private String isbn;
    private String description;
    private String imageLinks;
    private int pageCount;
    private String language;
    private String previewLink;
}
