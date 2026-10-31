package com.luis.financial_backend.modules.auth.filter;

import
        com.luis.financial_backend.modules.auth.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);


    public JwtAuthenticationFilter(
            JwtService jwtService,
            UserDetailsService userDetailsService
    ){
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }



    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse reponse,
            FilterChain filterChain
    ) throws ServletException, IOException
    {


        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, reponse);
            return;
        }



        String token = authHeader.substring(7);

        try{
            String userEmail = jwtService.extractUsername(token);

            if(userEmail != null
                    &&
                            SecurityContextHolder.getContext().getAuthentication() == null
            ){
                UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

                if(jwtService.isTokenValid(token, userEmail)){
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception exception){
            log.debug("JWT validation failed: {}", exception.getMessage());
        }
        filterChain.doFilter(request, reponse);
    }

}
