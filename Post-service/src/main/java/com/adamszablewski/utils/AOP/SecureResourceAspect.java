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



@Aspect
@Component
@AllArgsConstructor
public class SecureResourceAspect {

    private final SecurityUtil securityUtil;

    @Before("@annotation(om.example.annotations.SecureResource)")
    public void processSecureResource(JoinPoint joinPoint, SecureResource secureResource) {
        Object[] args = joinPoint.getArgs();

        for (Object arg : args) {
            if (arg instanceof HttpServletRequest request) {
                String token = request.getHeader("token");
                String postId = request.getHeader("postId");
                String upvoteId =request.getHeader("upvoteId");
                String commentId = request.getHeader("commentId");
                System.out.println("SECURITY ASPECT CALLED");
                boolean validated;
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
