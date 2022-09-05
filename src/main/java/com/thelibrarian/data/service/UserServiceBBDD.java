package com.thelibrarian.data.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

import com.thelibrarian.data.auth.dto.ChangePasswordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thelibrarian.data.entity.UsersEntity;
import com.thelibrarian.data.repository.IUser;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceBBDD {

    @Autowired
    private @NonNull IUser usuRepo;
    

    public List<UsersEntity> findAll() {
        return usuRepo.findAll();
    }

    public UsersEntity insert(UsersEntity u) throws NoSuchAlgorithmException {
        u.setPassword(encodePassword(u.getPassword()));
        return usuRepo.save(u);
    }

    public UsersEntity login(String correo, String password) throws NoSuchAlgorithmException {
        return usuRepo.findByCorreoAndPassword(correo, encodePassword(password));
    }

    public UsersEntity update(UsersEntity e, int id) {
        if(usuRepo.existsById(id)) {
            e.setId(id);
            return usuRepo.save(e);
        }
        return null; // No existe
    }

    public ChangePasswordDTO updatePassword(ChangePasswordDTO passwordChange) throws NoSuchAlgorithmException {


        UsersEntity userNewPassword = usuRepo.findByCorreoAndPassword(passwordChange.getEmail(),encodePassword(passwordChange.getPassword()));
        

        if(userNewPassword.getId()!=null) {

            userNewPassword.setPassword(encodePassword(passwordChange.getNewPassword()));

            usuRepo.save(userNewPassword);

            return passwordChange;
        }

        return null; // No existe
    }


    private String encodePassword(String pass) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
		String encodedPass = Base64.getEncoder().encodeToString(hash);
		return encodedPass;
	}

    public UsersEntity findById(Integer id_usuario) {
        return usuRepo.findById(id_usuario).orElse(null);
    }
}
