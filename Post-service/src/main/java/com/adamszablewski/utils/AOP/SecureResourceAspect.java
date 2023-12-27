package com.adamszablewski.utils.AOP;


import com.adamszablewski.annotations.SecureResource;
import com.adamszablewski.exceptions.SecurityException;
import com.adamszablewski.utils.security.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Aspect
@Component
@AllArgsConstructor
public class SecureResourceAspect {

    private final SecurityUtil securityUtil;

    @Before("@annotation(com.adamszablewski.annotations.SecureResource)")
    public void processSecureResource(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        System.out.println(Arrays.toString(args));
        System.out.println("SECURITY ASPECT CALLED");
        for (Object arg : args) {
            if (arg instanceof HttpServletRequest request) {
                String token = request.getHeader("token");
                String postId = request.getHeader("postId");
                String upvoteId =request.getHeader("upvoteId");
                String commentId = request.getHeader("commentId");

                boolean validated = false;

                System.out.println("validated status "+validated);
                if (postId != null && postId.length() > 0){
                    validated = securityUtil.ownsPost(Long.parseLong(postId), token);
                }
                else if (upvoteId != null && upvoteId.length() > 0) {
                    validated = securityUtil.ownsUpvote(Long.parseLong(upvoteId), token);
                }
                else if (commentId != null && commentId.length() > 0) {
                    validated = securityUtil.ownsComment(Long.parseLong(commentId), token);
                }
                else{
                    throw new SecurityException();
                }
                if (!validated){
                    throw new SecurityException();
                }
            }
        }
    }
}
