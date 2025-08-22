package com.ltp.gradesubmission.security.filter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ltp.gradesubmission.security.SecurityConstants;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
    
    @Override    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        String header = request.getHeader("Authorization"); // Bearer JWT

        if (header == null || !header.startsWith(SecurityConstants.BEARER)) {
            try {
                filterChain.doFilter(request, response);
                return;
            } catch (Exception e) {
            
            }
        }

        String token = header.replace(SecurityConstants.BEARER, "");
        String user = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET_KEY))
            .build()
            .verify(token)
            .getSubject();

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            
        }
    }
}
