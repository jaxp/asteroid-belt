package com.pallas.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.KeyPair;
import java.util.Date;

/**
 * @author: jax
 * @time: 2020/8/26 13:55
 * @desc:
 */
public class JwtUtils {

  public static void main(String[] args) {
    KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);
    String jws = Jwts.builder().setExpiration(new Date(System.currentTimeMillis() + 10000)).setIssuedAt(new Date()).setId("111222333444555666").signWith(keyPair.getPrivate()).compact();
    System.out.println(jws);
    Jws<Claims> claimsJwt = Jwts.parserBuilder().setSigningKey(keyPair.getPublic()).build().parseClaimsJws(jws);
    String sub = claimsJwt.getBody().getSubject();
    System.out.println(sub);
  }

}
