package com.example.EsercizioEpicode.responses;

import com.example.EsercizioEpicode.entities.Dipendente;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
@Data
public class LoginResponse {
    private String message;
    private String token;
    private Dipendente user;
    private LocalDateTime dateResponse;

    public LoginResponse(String message, String token, Dipendente user) {
        this.message = message;
        this.token = token;
        this.user = user;
        dateResponse=LocalDateTime.now();
    }

    public static ResponseEntity<LoginResponse> success(String message, String token, Dipendente user, HttpStatus httpStatus){
        LoginResponse loginResponse=new LoginResponse(message, token, user);
        return new ResponseEntity<>(loginResponse,httpStatus);
    }
}
