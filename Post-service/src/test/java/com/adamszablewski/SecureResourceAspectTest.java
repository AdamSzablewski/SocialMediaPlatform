package com.adamszablewski;

import com.adamszablewski.exceptions.MissingResourceHeaderException;
import com.adamszablewski.AOP.SecureContentResourceAspect;
import com.adamszablewski.utils.security.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SecureResourceAspectTest {

    @Mock
    private SecurityUtil securityUtil;

    @InjectMocks
    private SecureContentResourceAspect secureResourceAspect;

    @Test
    void processSecureResource_ValidTokenAndPostId_ShouldPassValidation() {

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("token")).thenReturn("token123");
        when(request.getParameter("postId")).thenReturn("123");
        when(securityUtil.ownsPost(123L, "token123")).thenReturn(true);

        JoinPoint joinPoint = mock(JoinPoint.class);
        when(joinPoint.getArgs()).thenReturn(new Object[]{request});


        secureResourceAspect.processSecureContentResource(joinPoint);

    }

    @Test
    void processSecureResource_InvalidToken_ShouldThrowNotAuthorizedException() {

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("token")).thenReturn(null);

        JoinPoint joinPoint = mock(JoinPoint.class);
        when(joinPoint.getArgs()).thenReturn(new Object[]{request});


        assertThrows(RuntimeException.class, () -> secureResourceAspect.processSecureContentResource(joinPoint));
    }
    @Test
    void processSecureResource_NoHeadersForResources_ShouldThrowNotAuthorizedException() {

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("token")).thenReturn("token123");

        JoinPoint joinPoint = mock(JoinPoint.class);
        when(joinPoint.getArgs()).thenReturn(new Object[]{request});


        assertThrows(MissingResourceHeaderException.class, () -> secureResourceAspect.processSecureContentResource(joinPoint));
    }


}
