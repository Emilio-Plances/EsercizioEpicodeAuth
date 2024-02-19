package com.example.EsercizioEpicode.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {
    @Pattern(regexp = ".*[^ ].*",message = "Nome non valido!")
    @Size(min=3,max=15,message = "Deve avere un numero di caratteri compreso tra 3 e 15")
    private String username;

    @NotBlank(message = "Non può essere vuoto")
    @Size(min=3,max=15,message = "Deve avere un numero di caratteri compreso tra 3 e 15")
    private String password;
}
