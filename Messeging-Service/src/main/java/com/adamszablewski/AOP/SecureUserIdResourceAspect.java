package com.adamszablewski.AOP;


import com.adamszablewski.util.security.SecurityUtil;
import com.adamszablewski.utils.security.SecurityUtil;
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
public class SecureUserIdResourceAspect {

    private final SecurityUtil securityUtil;

    @Before("@annotation(com.adamszablewski.annotations.SecureUserIdResource)")
    public void processSecureuserIdResource(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof HttpServletRequest request) {
                String token = request.getHeader("token");
                String userId = request.getParameter("userId");
                boolean validated = false;
                if (token == null){
                    throw new RuntimeException("Missing token exception");
                }
                if (userId != null && userId.length() > 0){
                    validated = securityUtil.isUser(Long.parseLong(userId), token);
                }

                if (!validated){
                    throw new NotAuthorizedException("Validation failed");
                }
            }
        }
    }
}
