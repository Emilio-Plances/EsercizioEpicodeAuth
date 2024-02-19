package com.example.EsercizioEpicode.services;

import com.example.EsercizioEpicode.entities.Dipendente;
import com.example.EsercizioEpicode.exceptions.NotFoundException;
import com.example.EsercizioEpicode.repositories.DipendenteRepository;
import com.example.EsercizioEpicode.requests.DipendentePatchRequest;
import com.example.EsercizioEpicode.requests.DipendenteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DipendenteService {
    @Autowired
    private DipendenteRepository dipendenteRepository;
    public Dipendente save(DipendenteRequest d){
        Dipendente dipendente=new Dipendente();
        dipendente.setPassword(d.getPassword());
        return dipendenteRepository.save(put(d,dipendente));
    }
    public Dipendente update(int id, DipendenteRequest d) throws NotFoundException {
        Dipendente dipendente=findById(id);
        return dipendenteRepository.save(put(d,dipendente));
    }
    public Dipendente patchUpdate(int id, DipendentePatchRequest d) throws NotFoundException {
        Dipendente dipendente=findById(id);
        if(d.getNome()!=null)dipendente.setNome(d.getNome());
        if(d.getCognome()!=null)dipendente.setCognome(d.getCognome());
        if(d.getUsername()!=null)dipendente.setUsername(d.getUsername());
        if(d.getEmail()!=null)dipendente.setEmail(d.getEmail());
        return dipendenteRepository.save(dipendente);
    }
    public Dipendente findById(int id) throws NotFoundException {
         Optional<Dipendente> dipendente=dipendenteRepository.findById(id);
         if(dipendente.isEmpty()) throw new NotFoundException("Non esiste un dipendente con id="+id);
         return dipendente.get();
    }
    public Page<Dipendente> findAll(Pageable pageable){
        return dipendenteRepository.findAll(pageable);
    }
    public Dipendente setFotoProfilo(int id,String s) throws NotFoundException {
        Dipendente dipendente=findById(id);
        dipendente.setFotoProfilo(s);
        return dipendenteRepository.save(dipendente);
    }
    private Dipendente put(DipendenteRequest d,Dipendente dipendente){
        dipendente.setNome(d.getNome());
        dipendente.setCognome(d.getCognome());
        dipendente.setUsername(d.getUsername());
        dipendente.setEmail(d.getEmail());
        return dipendente;
    }

    public void delete(int id) throws NotFoundException {
        Dipendente dipendente=findById(id);
        dipendenteRepository.delete(dipendente);
    }

    public Dipendente findByUsername(String username) throws NotFoundException {
        Optional<Dipendente> dipendenteOptional=dipendenteRepository.findByUsername(username);
        if(dipendenteOptional.isEmpty()) throw new NotFoundException("Questo username non è registrato");
        return dipendenteOptional.get();
    }

}
