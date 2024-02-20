package com.example.EsercizioEpicode.entities;

import com.example.EsercizioEpicode.enums.Ruolo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name="dipendenti")
public class Dipendente implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "sequenza_dipendente")
    @SequenceGenerator(name="sequenza_dipendente",initialValue = 1,allocationSize = 1)
    private int id;
    private String nome;
    private String cognome;
    @Column(unique = true)
    private String username;
    private String email;
    private String password;
    private String fotoProfilo;

    @Enumerated(EnumType.STRING)
    private Ruolo ruolo;
    @JsonIgnore
    @OneToMany(mappedBy = "dipendente")
    private List<Dispositivo> listaDispositivi;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(ruolo.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
