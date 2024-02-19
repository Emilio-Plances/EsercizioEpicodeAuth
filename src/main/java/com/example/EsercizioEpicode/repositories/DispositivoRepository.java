package com.example.EsercizioEpicode.repositories;

import com.example.EsercizioEpicode.entities.Dispositivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DispositivoRepository extends JpaRepository<Dispositivo,Integer>, PagingAndSortingRepository<Dispositivo,Integer> {
}
