package de.fhms.sweng.event_management.security;

import de.fhms.sweng.event_management.exceptions.ResourceNotFoundException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${security.jwt.token.expire-length:3600000}")
    private long validityTime;

    @Autowired
    private SigningKeyProvider keys;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public String createJwt(String username, String role) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("auth", role);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(keys.getPrivateKey())
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(keys.getPublicKey()).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean isValidJWT(String token) {
        try {
            Jwts.parser().setSigningKey(keys.getPublicKey()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new ResourceNotFoundException("JWT invalid");
        }
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
