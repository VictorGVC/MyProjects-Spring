package com.victorgvc.multilanguagespring.auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class CustomAuthorizationFilter extends OncePerRequestFilter{

    private static final String[] AUTH_WHITELIST = {
        // -- Swagger UI v3 (OpenAPI)
        "/v3/api-docs",
        "/swagger-ui/**",
        "/swagger-ui.html",
        // other public endpoints of your API may be appended to this array
        "/login"
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(Arrays.stream(AUTH_WHITELIST).anyMatch(request.getServletPath()::equalsIgnoreCase)) {
            filterChain.doFilter(request, response);
        } else {
            try {
                String authorizationHeader = request.getHeader("Authorization");
                if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                    String token = authorizationHeader.substring("Bearer ".length());
                    Algorithm algorithm = Algorithm.HMAC256("secret".getBytes()); // TODO remember to use heroku secret
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);
                    String username = decodedJWT.getSubject();
                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                    Collection <SimpleGrantedAuthority> authorities = new ArrayList<>();
                    Arrays.stream(roles).forEach(role -> {
                        authorities.add(new SimpleGrantedAuthority(role));
                    });
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                }
            } catch (Exception e) {
                response.setStatus(401);
                response.getWriter().println(e);
            }
        }
    }
    
}
