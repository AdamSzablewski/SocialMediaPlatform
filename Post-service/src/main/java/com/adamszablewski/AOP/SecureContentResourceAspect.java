package com.adamszablewski.AOP;


import com.adamszablewski.annotations.SecureContentResource;
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

    @Before("@annotation(secureContentResource) && args(.., request)")
    public void processSecureContentResource(JoinPoint joinPoint, SecureContentResource secureContentResource, HttpServletRequest request) {

        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof HttpServletRequest) {
                String token = request.getHeader("token");
                String postId = request.getParameter("postId");
                String upvoteId =request.getParameter("upvoteId");
                String commentId = request.getParameter("commentId");
                String multimediaId = request.getParameter("multimediaId");

                boolean validated;

                if (token == null){
                    throw new RuntimeException("Missing token exception");
                }

                switch (secureContentResource.value()){
                    case "commentId" -> {
                        assert commentId != null;
                        validated = securityUtil.ownsComment(Long.parseLong(commentId), token);
                    }
                    case "postId" -> {
                        assert postId != null;
                        validated = securityUtil.ownsPost(Long.parseLong(postId), token);
                    }
                    case "upvoteId" -> {
                        assert upvoteId != null;
                        validated = securityUtil.ownsUpvote(Long.parseLong(upvoteId), token);
                    }
                    case "multimediaId" -> {
                        assert commentId != null;
                        validated = securityUtil.ownsMultimedia(Long.parseLong(multimediaId), token);
                    }
                    default -> throw new NotAuthorizedException("Validation failed");

                }

            }
        }
    }
}
