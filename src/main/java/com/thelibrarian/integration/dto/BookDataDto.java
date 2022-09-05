package com.thelibrarian.integration.dto;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookDataDto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public BookDto[] items;
}
