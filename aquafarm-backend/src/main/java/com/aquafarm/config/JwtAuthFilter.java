// src/main/java/com/aquafarm/config/JwtAuthFilter.java
package com.aquafarm.config;

import com.aquafarm.user.entity.User;
import com.aquafarm.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication
    .UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority
    .SimpleGrantedAuthority;
import org.springframework.security.core.context
    .SecurityContextHolder;
import org.springframework.security.web.authentication
    .WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/*
 📚 LEARN: JWT Filter
 
 This runs BEFORE every API request.
 It checks: "Does this request have a valid JWT token?"
 
 Flow:
 1. Extract token from "Authorization: Bearer xxx" header
 2. Validate token
 3. Load user from database
 4. Set authentication in Spring Security
 5. Continue to controller
*/

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        // Step 1: Get Authorization header
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Step 2: Extract token (remove "Bearer " prefix)
        String token = authHeader.substring(7);

        // Step 3: Validate
        if (jwtUtil.validateToken(token)) {
            String phone = jwtUtil.extractPhone(token);

            // Step 4: Load user
            User user = userRepository.findByPhone(phone)
                    .orElse(null);

            if (user != null) {
                // Step 5: Set authentication
                var authorities = List.of(
                    new SimpleGrantedAuthority(
                        "ROLE_" + user.getRole()));

                var authToken =
                    new UsernamePasswordAuthenticationToken(
                        user, null, authorities);

                authToken.setDetails(
                    new WebAuthenticationDetailsSource()
                        .buildDetails(request));

                SecurityContextHolder.getContext()
                    .setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}