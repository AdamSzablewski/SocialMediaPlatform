package com.adamszablewski.AOP;


import com.adamszablewski.exceptions.MissingResourceHeaderException;
import com.adamszablewski.util.security.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.NotAuthorizedException;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;


@Aspect
@Component
@AllArgsConstructor
public class SecureContentResourceAspect {

    private final SecurityUtil securityUtil;

    @Before("@annotation(com.adamszablewski.annotations.SecureContentResource)")
    public void processSecureContentResource(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof HttpServletRequest request) {
                String token = request.getHeader("token");
                String conversationId = request.getParameter("conversationId");

                boolean validated = false;
                if (conversationId == null){
                    throw new MissingResourceHeaderException();
                }
                if (token == null){
                    throw new RuntimeException("Missing token exception");
                }
                if (conversationId != null && conversationId.length() > 0){
                    validated = securityUtil.ownsConversation(Long.parseLong(conversationId), token);
                }
                if (!validated){
                    throw new NotAuthorizedException("Validation failed");
                }
            }
        }
    }
}
