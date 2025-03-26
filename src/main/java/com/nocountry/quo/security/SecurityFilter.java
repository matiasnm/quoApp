package com.nocountry.quo.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.AllArgsConstructor;

import org.springframework.web.filter.OncePerRequestFilter;

import com.nocountry.quo.repository.UserRepository;

@Component
@AllArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {
 
    private final JWTTokenService tokenService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("====Running JwtAuthFilter on: " + request.getRequestURI());
        var authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            var token = authHeader.replace("Bearer ", "");
            try {
                var username = tokenService.getSubject(token);
                if (username != null) {
                    var user = userRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
                    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception ex) {
                System.out.println("====Invalid token: " + ex.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }   
}