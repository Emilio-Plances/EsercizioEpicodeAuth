package com.example.EsercizioEpicode.controllers;

import com.example.EsercizioEpicode.entities.Dipendente;
import com.example.EsercizioEpicode.exceptions.BadRequestException;
import com.example.EsercizioEpicode.exceptions.NotFoundException;
import com.example.EsercizioEpicode.exceptions.UnauthorizedException;
import com.example.EsercizioEpicode.requests.DipendenteRequest;
import com.example.EsercizioEpicode.requests.LoginRequest;
import com.example.EsercizioEpicode.responses.DefaultResponse;
import com.example.EsercizioEpicode.responses.LoginResponse;
import com.example.EsercizioEpicode.security.JwtTools;
import com.example.EsercizioEpicode.services.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {
    @Autowired
    private JwtTools jwtTools;
    @Autowired
    private DipendenteService dipendenteService;
    @PostMapping("/register")
    public ResponseEntity<DefaultResponse> register(@RequestBody @Validated DipendenteRequest dR, BindingResult bR){
        if(bR.hasErrors()) throw new BadRequestException(bR.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());
        return DefaultResponse.customMessage("Creato!",dipendenteService.save(dR), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Validated LoginRequest lR, BindingResult bR) throws NotFoundException, UnauthorizedException {
        if(bR.hasErrors()) throw new BadRequestException(bR.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());
        Dipendente d=dipendenteService.findByUsername(lR.getUsername());
        if(!d.getPassword().equals(lR.getPassword())) throw new UnauthorizedException("Username/Password sbagliati");
        return LoginResponse.success("Logged",jwtTools.createToken(d),d,HttpStatus.OK);
    }
}
