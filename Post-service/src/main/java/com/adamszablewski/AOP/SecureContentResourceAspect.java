package com.adamszablewski.AOP;


import com.adamszablewski.exceptions.MissingResourceHeaderException;
import com.adamszablewski.exceptions.SecurityException;
import com.adamszablewski.interfaces.UserResource;
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
public class SecureContentResourceAspect {

    private final SecurityUtil securityUtil;

    @Before("@annotation(com.adamszablewski.annotations.SecureContentResource)")
    public void processSecureContentResource(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof HttpServletRequest request) {
                String token = request.getHeader("token");
                String postId = request.getParameter("postId");
                String upvoteId =request.getParameter("upvoteId");
                String commentId = request.getParameter("commentId");
                String multimediaId = request.getParameter("multimediaId");

                boolean validated;
                if (postId == null && upvoteId == null && commentId == null && multimediaId == null){
                    throw new MissingResourceHeaderException();
                }
                if (token == null){
                    throw new RuntimeException("Missing token exception");
                }
                if (postId != null && postId.length() > 0){
                    validated = securityUtil.ownsPost(Long.parseLong(postId), token);
                }
                else if (upvoteId != null && upvoteId.length() > 0) {
                    validated = securityUtil.ownsUpvote(Long.parseLong(upvoteId), token);
                }
                else if (commentId != null && commentId.length() > 0) {
                    validated = securityUtil.ownsComment(Long.parseLong(commentId), token);
                }
                else if (multimediaId != null && multimediaId.length() > 0) {
                    validated = securityUtil.ownsMultimedia(Long.parseLong(multimediaId), token);
                }
                else{
                    throw new SecurityException();
                }
                if (!validated){
                    throw new NotAuthorizedException("Validation failed");
                }
            }
        }
    }
}
