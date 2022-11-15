package com.nothing.todo_app.security;

import com.nothing.todo_app.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/*TokenProvider
* 유저 정보를 받아 JWT(자바 웹 토큰) 생성*/
@Slf4j
@Service
public class TokenProvider {
    private static final String SECRET_KEY = "FlRpX30pMqDbiAkmlfArbrmVkDD4RqISskGZmBFax5oGVxzXXWUzTR5JyskiHMIV9M1Oicegkpi46AdvrcX1E6CmTUBc6IFbTPiD";

    public String create(UserEntity userEntity){
        //기한 설정 _ 이 경우 1일
        Date expiryDate = Date.from(
                Instant.now().plus(1, ChronoUnit.DAYS));

    /*
      { // header
        "alg":"HS512"
      }.
      { // payload
        "sub":"40288093784915d201784916a40c0001",
        "iss": "demo app",
        "iat":1595733657,
        "exp":1596597657
      }.
      // SECRET_KEY를 이용해 서명한 부분
      Nn4d1MOVLZg79sfFACTIpCPKqWmpZMZQsbNrXdJJNWkRv50_l7bPLQPwhMobT4vBOG6Q3JYjhDrKFlBSaUxZOg
       */
        // JWT 생성
        return Jwts.builder()
                // header를 위한 SECRET_KEY
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
                // payload
                .setSubject(userEntity.getId()) // sub
                .setIssuer("todo_app")          // iss
                .setIssuedAt(new Date())        // iat
                .setExpiration(expiryDate)      // exp
                .compact();
    }

    public String validateAndGetUserId(String token){
        // parseClaimJws 메서드가 Base 64로 디코딩 및 파싱.
        // 즉, 헫더와 페이로드를 setSigningKey로 넘어온 시크릿을 이용해 서명 후, token의 서명과 비교.
        // 위조되지 않았담녀 페이로드(Claims) 리턴, 위조라면 예외를 날림
        // 그 중 우리는 userId가 필요하므로 getBody를 부른다.
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}

