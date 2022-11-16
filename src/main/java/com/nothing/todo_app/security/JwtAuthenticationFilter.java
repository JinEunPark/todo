package com.nothing.todo_app.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/*JwtAuthenticationFilter
* 한 요청당 반드시 한번 실행되는 OncePerRequestFilter 상속
* 인증 부분만 구현하고 유효 시간 검사는 생략된 코드이다. 원래 둘이 세트.
* 보안할 점으로 이를 추가할 것을 고려하자.
*
* 토큰 인증은 다음과 같은 과정으로 진행된다.
* 1. parseBearerToken()을 통해 요청의 헤더에서 Bearer 토큰을 가져온다
* 2. TokenProvider를 이용해 토큰을 인증하고 UsernamePasswordAuthenticationToken을 작성
*   해당 오브젝트에 사용자 인증 정보를 저장하고
*   SecurityContext에 인증된 사용자를 등록한다.
*   서버는 요청이 끝나기 전까지 방금 인증한 사용자의 정보를 갖고 있게된다.
*   이로써 요청을 처리하는 과정에서 사용자가 인증됐는지 여부나 인증된 사용자가 누군지 알 수 있게 된다.*/
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException{
        try{
            // 요청에서 토큰 가져오기.
            String token = parseBearerToken(request);
            log.info("Filter is running...");
            // 토큰 검사하기, JWT이므로 인가 서버에 요펑하지 않고도 검증 가능.
            if(token != null && !token.equalsIgnoreCase("null")){
                //userID 가져오기. 위조된 경우 예외 처리
                String userId = tokenProvider.validateAndGetUserId(token);
                log.info("Authenticated user ID : " + userId);
                // 인증 완료; SecurityContextHolder에 등록해야 인증된 사용자라고 생각함.
                AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userId, // 인증된 사용자 정보, 문자열 아니어도 상관 없음. 보통 UserDetail이라는 오브젝트 넣는데 우린 안만듦
                        null,
                        AuthorityUtils.NO_AUTHORITIES
                );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authentication);
                SecurityContextHolder.setContext(securityContext);
            }
        }catch(Exception ex){
            logger.error("Could not set user authentication in security context", ex);
        }
        filterChain.doFilter(request, response);
    }

    private String parseBearerToken(HttpServletRequest request){
        // Http 요청의 헤더를 파싱해 Bearer 토큰을 리턴.
        String bearerToken = request.getHeader("Authentication");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }
}
