package com.airbnb.securityConfig;

import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.PropertyUserRepository;
import com.airbnb.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {
    private JWTService jwtService;
    private PropertyUserRepository propertyUserRepository;

    public JWTRequestFilter(JWTService jwtService,PropertyUserRepository propertyUserRepository) {
        this.jwtService = jwtService;
        this.propertyUserRepository=propertyUserRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        if(tokenHeader!=null && tokenHeader.startsWith("Bearer ")){
            String token = tokenHeader.substring(7);
            String username = jwtService.getUsername(token);
            Optional<PropertyUser> opUser = propertyUserRepository.findByUserName(username);
            if(opUser.isPresent()){
                PropertyUser user = opUser.get();
                //setting into session
                UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(user,null, Collections.singleton(new SimpleGrantedAuthority(user.getUserRole())));
                //creating session
                authentication.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request,response);
    }
}
