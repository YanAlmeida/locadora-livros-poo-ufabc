package br.com.ufabc.poo.trocalivros.model;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AuthToken {

    public static String getToken() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("kid", UUID.randomUUID());
        return Jwts.builder().
                signWith(SignatureAlgorithm.HS256, "SINGNATURE").
                setClaims(map).
                compact();
    }

    public static Boolean checkToken(String token) {
        try {
            Jwts.parser().setSigningKey("SINGNATURE").parseClaimsJws(token);
            return Boolean.TRUE;
        } catch (JwtException ex) {
            return Boolean.FALSE;
        }
    }
}
