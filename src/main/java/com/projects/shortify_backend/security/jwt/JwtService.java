package com.projects.shortify_backend.security.jwt;
import com.projects.shortify_backend.entities.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Log4j2
public class JwtService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public String generateToken(Authentication authentication) {

        var email = "";

        var principal = authentication.getPrincipal();

        Collection<? extends GrantedAuthority> authorities = new ArrayList<>();

        if(principal instanceof UserDetails userDetails){
            var user = (User) userDetails;
            email = user.getEmail();
            authorities = user.getAuthorities();
        }

        var claims = JwtClaimsSet.builder()
                .issuer("https://pea-todo-list-application.netlify.app")
                .subject(email)
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(300))
                .claim("scope",getAuthorities(authorities))
                .build();
        var parameter = JwtEncoderParameters.from(claims);
        return jwtEncoder.encode(parameter).getTokenValue();
    }

    private String getAuthorities(Collection<? extends GrantedAuthority> authorities){
        return authorities.stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "));
    }

    public String extractEmail(String token){
        return jwtDecoder.decode(token)
                .getSubject();
    }

    public boolean validateToken(String token, String email) {
        try {
            var claims = jwtDecoder.decode(token);

            if (!claims.getSubject().equals(email)) {
                return false;
            }

            var expiresAt = claims.getExpiresAt();

            return expiresAt != null && !expiresAt.isBefore(Instant.now());

        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid JWT Token: {}", e.getMessage());
            return false;
        }
    }

}
