package br.com.ufabc.poo.trocalivros.model;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Classe referente ao token de autenticação
 */
public class AuthToken {

    /**
     * Método responsável para obter um novo token JWT
     * @return Token JWT
     */
    public static String getToken() {
        Instant expirationTime = Instant.now().truncatedTo(ChronoUnit.SECONDS).plus(1, ChronoUnit.HOURS);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("kid", UUID.randomUUID());
        return Jwts.builder().
                signWith(SignatureAlgorithm.HS256, System.getenv("SIGNATURE")).
                setClaims(map).
                setExpiration(Date.from(expirationTime)).
                compact();
    }

    /**
     * Método responsável para verificar se token é válido
     * @param token Token a ser validado
     * @return Booleano se é válido ou não
     */
    public static Boolean checkToken(String token) {
        try {
            Jwts.parser().setSigningKey(System.getenv("SIGNATURE")).parse(token);
            return Boolean.TRUE;
        } catch (JwtException ex) {
            return Boolean.FALSE;
        }
    }
}
