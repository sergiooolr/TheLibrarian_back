package com.thelibrarian.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties (ignoreUnknown = true)
public class UserDto implements Serializable {

    private static final long serialVersionUID = 1L;


    private Integer id;
    private String nombre;
    private String correo;
    private String password;


}
