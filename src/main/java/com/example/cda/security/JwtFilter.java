package com.example.cda.security;

import com.example.cda.services.JwtUserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserService jwtUserService;
    Claims claims= null;
    @Override
    protected  void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(request.getServletPath().matches("/login|/forgotPassword|/signup")){
            filterChain.doFilter(request,response);
        }else {

                final String header = request.getHeader("Authorization");
                String userName = null;
                String jwtToken = null;
                // Si token ne commence pas par bearer et le header null =>sortir
                if (header != null && header.startsWith("Bearer")) {

                    jwtToken = extractJwtFromRequest(request);
                    userName = jwtUserService.getUserNameFromJwt(jwtToken);
                    claims = jwtUserService.getAllClaimsFromToken(jwtToken);
                    System.out.println("jwtToken: "+jwtToken+" userName: "+userName+ " claims role: "+claims.get("authorities"));

                }
                if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    UserDetails userDetails = jwtUserService.loadUserByUsername(userName);
                    System.out.println("userDetails: "+userDetails.getUsername());
                    if (jwtUserService.validateToken(jwtToken, userDetails)) {
                        System.out.println("valid token ");
                        // On le passe aux controllers grâce au context
                        Authentication authentication =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        //authentifier le user
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
                //System.out.println("filterChain.dofilter");
                filterChain.doFilter(request, response);
            }
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}
