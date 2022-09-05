package com.thelibrarian.data.repository;

import com.thelibrarian.data.entity.UsersEntity;
import com.thelibrarian.data.entity.proyeccion.UsersWithOutReserve;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUser extends JpaRepository <UsersEntity, Integer> {
    List<UsersWithOutReserve> findBy();

    UsersEntity findByCorreoAndPassword(String correo, String password);


}
