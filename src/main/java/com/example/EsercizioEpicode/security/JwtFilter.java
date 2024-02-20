package com.example.EsercizioEpicode.security;

import com.example.EsercizioEpicode.entities.Dipendente;
import com.example.EsercizioEpicode.exceptions.NotFoundException;
import com.example.EsercizioEpicode.exceptions.UnauthorizedException;
import com.example.EsercizioEpicode.services.DipendenteService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTools jwtTools;
    @Autowired
    private DipendenteService dipendenteService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String auth=request.getHeader("Authorization");
        if(auth==null|| !auth.startsWith("Bearer ")) throw new UnauthorizedException("Token mancante!");

        String token=auth.substring(7);
        System.out.println(token);
        jwtTools.validateToken(token);

        String username = jwtTools.extractUsernameFromToken(token);
        Dipendente dipendente = dipendenteService.findByUsername(username);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(dipendente, null,dipendente.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request,response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/api/auth/**", request.getServletPath());
    }
}
