package de.fhms.sweng.event_management.security;

import de.fhms.sweng.event_management.exceptions.NotAuthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.util.WebUtils;

@Component
public class JwtTokenProvider {


    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private JwtUserDetailsService jwtUserDetailsService;

    private SigningKeyProvider keys;

    @Autowired
    public JwtTokenProvider (SigningKeyProvider keys, JwtUserDetailsService jwtUserDetailsService){
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.keys = keys;
    }

    public String getUsername(String token) {
        String result = Jwts.parser().setSigningKey(keys.getPublicKey()).parseClaimsJws(token).getBody().getSubject();
        return result;
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(keys.getPublicKey()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new NotAuthorizedException("JWT invalid");
        }
    }

    public String resolveToken(HttpServletRequest req) {
        Cookie bearerToken = WebUtils.getCookie(req,"Authorization");
        if (bearerToken != null) {
            return bearerToken.getValue();
        }
        return null;
    }
}

