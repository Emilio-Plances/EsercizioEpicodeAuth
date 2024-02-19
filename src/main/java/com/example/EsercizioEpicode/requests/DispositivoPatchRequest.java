package com.example.EsercizioEpicode.requests;

import com.example.EsercizioEpicode.enums.Status;
import com.example.EsercizioEpicode.enums.Tipo;
import lombok.Data;


@Data
public class DispositivoPatchRequest {
    private Status status;
    private Tipo tipo;
    private Integer dipendenteId;
}
