package com.thelibrarian.data.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor
@AllArgsConstructor
@Table(
        uniqueConstraints= {
            @UniqueConstraint(name = "UniqueCorreo",columnNames={"correo"})
        }
    )
public class UsersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;
    @NotNull(message = "El nombre es obligatorio")
    private String nombre;
    @NotNull
    @Email
    private String correo;
    @NotEmpty
    private String password;


    @JsonBackReference
    @OneToMany(targetEntity = ReservationEntity.class, mappedBy = "id_usuario", orphanRemoval = false, fetch = FetchType.LAZY)
	private List<ReservationEntity> reservation;
}
