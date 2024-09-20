package com.xsustain.xsustaincrm.config;

import java.io.IOException;
import java.util.Collections;

import com.xsustain.xsustaincrm.exception.CustomError;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

import java.security.Key;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${token.secret.key}")
    private String jwtSecretKey;

    private final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.replace("Bearer ", "");

            try {
                Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(getSigningKey()).build()
                        .parseClaimsJws(token);

                Claims body = claimsJws.getBody();
                String username = body.getSubject();
                String roles = body.get("roles", String.class);

                String userId = body.get("email", String.class);
                SimpleGrantedAuthority grantedAuthorities = getAuthorities(roles);

                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null,
                        Collections.singletonList(grantedAuthorities));
                authToken.setDetails(userId);
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);

                log.debug("JWT is successfully verified");
            } catch (IllegalArgumentException e) {
                log.error("An error occurred while fetching Username from Token");
                SecurityContextHolder.clearContext();
                setErrorException(response, HttpStatus.UNAUTHORIZED, "Invalide access token");
                return;
            } catch (ExpiredJwtException e) {
                log.warn("The token has expired");
                SecurityContextHolder.clearContext();
                setErrorException(response, HttpStatus.FORBIDDEN, "Access token has expired !");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private SimpleGrantedAuthority getAuthorities(String roles) {
        SimpleGrantedAuthority authorities = new SimpleGrantedAuthority("ROLE_" + roles);
        return authorities;
    }

    private void setErrorException(HttpServletResponse response, HttpStatus httpStatus, String errorMessage)
            throws IOException {
        CustomError error = new CustomError(httpStatus.value(), httpStatus.name(), errorMessage,
                System.currentTimeMillis());
        response.setStatus(httpStatus.value());
        response.getWriter().write(objectWriter.writeValueAsString(error));
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().flush();
    }
}