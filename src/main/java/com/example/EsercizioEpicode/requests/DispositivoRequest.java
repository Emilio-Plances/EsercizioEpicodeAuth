package com.example.EsercizioEpicode.requests;

import com.example.EsercizioEpicode.enums.Status;
import com.example.EsercizioEpicode.enums.Tipo;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class DispositivoRequest {
    @NotNull(message = "Non può essere null")
    private Status status;
    @NotNull(message = "Non può essere null")
    private Tipo tipo;
    private Integer dipendenteId;
}
