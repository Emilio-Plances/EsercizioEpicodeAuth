package com.example.EsercizioEpicode.repositories;

import com.example.EsercizioEpicode.entities.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;


public interface DipendenteRepository extends JpaRepository<Dipendente,Integer>, PagingAndSortingRepository<Dipendente,Integer> {
    Optional<Dipendente> findByUsername(String username);
}
