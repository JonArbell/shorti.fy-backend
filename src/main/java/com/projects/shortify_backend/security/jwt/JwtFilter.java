package com.projects.shortify_backend.security.jwt;

import com.projects.shortify_backend.entities.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@RequiredArgsConstructor
@Component
@Log4j2
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        var header = request.getHeader("Authorization");

        var token = "";

        log.info("Header : {}", header);

        if(header != null && header.startsWith("Bearer"))
            token = header.substring(6);

        log.info("Token : {}", token);

        if(!token.isEmpty()){

            try{

                var getEmail = jwtService.extractEmail(token);

                log.info("Email : {}",getEmail);

                if(getEmail != null && jwtService.validateToken(token,getEmail)){

                    var userDetails = userDetailsService.loadUserByUsername(getEmail);

                    log.info("User Details : {}", userDetails);

                    var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    var role = ((User) userDetails).getRole();

                    log.info("Role : {}", role);

                    log.info("Authenticated or not : {}", SecurityContextHolder.getContext().getAuthentication().isAuthenticated());

                }
            }catch (JwtException e){
                log.info("JwtException : {}",e.getMessage());

            }
        }
        filterChain.doFilter(request, response);

        log.info("Security Context After doFilter: {}", SecurityContextHolder.getContext().getAuthentication());
    }

}
