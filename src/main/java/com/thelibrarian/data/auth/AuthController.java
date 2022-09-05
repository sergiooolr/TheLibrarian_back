package com.thelibrarian.data.auth;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.thelibrarian.integration.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.thelibrarian.data.auth.dto.LoginDto;
import com.thelibrarian.data.auth.dto.TokenResponseDto;
import com.thelibrarian.data.entity.UsersEntity;
import com.thelibrarian.data.security.SecurityConstants;
import com.thelibrarian.data.service.UserServiceBBDD;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private final @NonNull UserServiceBBDD usuService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/getCurrentUser")
    public int currentUserId(Authentication authentication){
        int userId =(int) authentication.getCredentials();
        return userId;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody @Valid LoginDto login) throws NoSuchAlgorithmException {

        UsersEntity u = usuService.login(login.getCorreo(), login.getPassword());
        
        if(u != null) {
            String token = getToken(u);
            
            TokenResponseDto resp = new TokenResponseDto(token);

            return ResponseEntity.ok().body(resp);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/registro")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void registro(@RequestBody @Valid UsersEntity usuario) throws NoSuchAlgorithmException, IOException {
        usuService.insert(usuario);
       // emailService.sendEmail(usuario.getNombre(), usuario.getCorreo());
    }

    @GetMapping("/validate")
    public void validateToken() {
    }

    private String getToken(UsersEntity user) {	
		Map<String, Object> data = new HashMap<String, Object>();
		
		data.put("id", user.getId());
		data.put("correo", user.getCorreo());
		data.put("authorities", Arrays.asList("ROLE_USER"));
		
		String token = Jwts.builder().setId("springEventos")
				.setSubject(user.getNombre()).addClaims(data)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + (24*60*60*1000))) // Caduca en un día
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET_KEY).compact();
		
		return token;
	}

    //Reset Password

    

}
