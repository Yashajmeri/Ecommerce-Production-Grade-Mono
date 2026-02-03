package com.example.ecommerce.Project1.security.JWT;


import com.example.ecommerce.Project1.security.services.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger  = LoggerFactory.getLogger(JwtUtils.class);
    @Value("${spring.app.jwtExpirationInMs}")
    private int jwtExpirationInMs;
    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;
    @Value("${spring.ecom.app.jwtCookie}")
    private  String jwtCookie;
    // Getting JWT from Header
//    public String getJwtFromHeader(HttpServletRequest request) {
//        String bearerToken = request.getHeader("Authorization");
//        logger.debug("Authorization Header: {}", bearerToken);
//        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7);
//
//        }
//        return null;
//    }
    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookie);
        if(cookie != null) {
            System.out.println("COOKIE : "+cookie.getValue());
            return cookie.getValue();
        } else {
            return null;
        }
    }
    public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal) {
          String jwt = generateToken(userPrincipal.getUsername());
          ResponseCookie cookie = ResponseCookie.from(jwtCookie,jwt)
                  .path("/api")
                  .maxAge(24 *60* 60)
                  .httpOnly(false)
                  .build();
          return cookie;
    }
    // this is for Logout to clean cookie
    public ResponseCookie getCleanCookie() {
        return ResponseCookie.from(jwtCookie,null)
                .path("/api")
                .build();
    }
    // Get  Token From UserName
    public  String generateToken(String username) {
//        String userName = userDetails.getUsername();
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date((new Date().getTime()+jwtExpirationInMs)))
                .signWith(key())
                .compact(); // Return in String format
    }
    // Get Username From Token
    public  String getUserNameFromToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build().parseSignedClaims(token)
                .getPayload().getSubject();
    }
    //Generate the signing key
    public Key key() {
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret)
        );
    }
    //validate Jwt Token
    public boolean validateJwtToken(String authToken) {
        try {
            System.out.println("Validate....");
            Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(authToken).getPayload();
            return true;
        }catch (MalformedJwtException exception) {
            logger.error("Invalid JWT token: {}", exception.getMessage());
        }catch (ExpiredJwtException exception) {
            logger.error("JWT token is expired: {}", exception.getMessage());
        }catch (UnsupportedJwtException exception) {
            logger.error("JWT token is unsupported: {}", exception.getMessage());
        }
        catch (IllegalArgumentException exception) {
            logger.error("JWT claims string is empty: {}", exception.getMessage());
        }
        return false;
    }


}

