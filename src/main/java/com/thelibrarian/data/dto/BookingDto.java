package com.thelibrarian.data.dto;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingDto implements Serializable {

 private static final long serialVersionUID = 1L;

  private Integer id;
  private Integer  id_usuario;
  private Integer  id_book;
  private Boolean is_reservado;
    
}
