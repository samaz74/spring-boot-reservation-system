package com.app.reservation.security;

import com.app.reservation.repository.InvalidatedTokenRepository;
import com.app.reservation.Service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtFilter extends OncePerRequestFilter{
    private final InvalidatedTokenRepository invalidatedTokenRepository;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtFilter(InvalidatedTokenRepository invalidatedTokenRepository,JwtService jwtService,UserDetailsService userDetailsService){
        this.invalidatedTokenRepository = invalidatedTokenRepository;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getHeader("Authorization") == null || !request.getHeader("Authorization").startsWith("Bearer ")|| invalidatedTokenRepository.existsByToken(request.getHeader("Authorization").substring(7))) {
            filterChain.doFilter(request,response);
            return;
        }else if(jwtService.validateToken(request.getHeader("Authorization").substring(7))){
            String email = jwtService.extractEmail(request.getHeader("Authorization").substring(7));
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request,response);
    }
}
