package com.example.EsercizioEpicode.controllers;

import com.cloudinary.Cloudinary;
import com.example.EsercizioEpicode.exceptions.BadRequestException;
import com.example.EsercizioEpicode.exceptions.NotFoundException;
import com.example.EsercizioEpicode.requests.DipendentePatchRequest;
import com.example.EsercizioEpicode.requests.DipendenteRequest;
import com.example.EsercizioEpicode.responses.DefaultResponse;
import com.example.EsercizioEpicode.services.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/api/dipendenti")
public class DipendenteController {
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private DipendenteService dipendenteService;
    @Autowired
    private JavaMailSenderImpl JMS;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DefaultResponse> getAll(Pageable pageable){
        return DefaultResponse.noMessage(dipendenteService.findAll(pageable),HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<DefaultResponse> getById(@PathVariable int id) throws NotFoundException {
        return DefaultResponse.customMessage("Trovato",dipendenteService.findById(id),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DefaultResponse> update(@PathVariable int id,@RequestBody @Validated DipendenteRequest d, BindingResult bR) throws NotFoundException {
        if(bR.hasErrors()) throw new BadRequestException(bR.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());
        return DefaultResponse.customMessage("Aggiornato",dipendenteService.update(id,d),HttpStatus.OK);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<DefaultResponse> patchUpdate(@PathVariable int id, @RequestBody @Validated DipendentePatchRequest d, BindingResult bR) throws NotFoundException {
        if(bR.hasErrors()) throw new BadRequestException(bR.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());
        return DefaultResponse.customMessage("Aggiornato",dipendenteService.patchUpdate(id,d),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<DefaultResponse> delete(@PathVariable int id) throws NotFoundException {
        dipendenteService.delete(id);
        return DefaultResponse.noObject("Dipendente con id="+id+" eliminato",HttpStatus.OK);
    }
    @PatchMapping("/upload/{id}")
    public ResponseEntity<DefaultResponse> upload(@PathVariable int id,@RequestParam("upload") MultipartFile file) throws IOException, NotFoundException {
        String url=(String) cloudinary.uploader().upload(file.getBytes(),new HashMap()).get("url");
        return DefaultResponse.customMessage("ImmagineCaricata",dipendenteService.setFotoProfilo(id,url),HttpStatus.OK);
    }
    public void sendEmail(String email){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Ciao!");
        message.setText("So che te l'ho già detto ma, ciao!");
        JMS.send(message);
    }
}
