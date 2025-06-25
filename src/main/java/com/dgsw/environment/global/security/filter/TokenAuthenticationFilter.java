package com.dgsw.environment.global.security.filter;

import com.dgsw.environment.entity.UserEntity;
import com.dgsw.environment.exception.CustomException;
import com.dgsw.environment.exception.ErrorCode;
import com.dgsw.environment.exception.UserErrorCode;
import com.dgsw.environment.global.response.ErrorResponse;
import com.dgsw.environment.global.security.auth.CustomUserDetails;
import com.dgsw.environment.global.security.token.TokenProvider;
import com.dgsw.environment.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        try {
            if (token != null) {
                String userId = tokenProvider.getUserId(token);

                UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

                CustomUserDetails userDetails = new CustomUserDetails(userId, userEntity.getUsername());

                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                SecurityContext context = SecurityContextHolder.createEmptyContext();

                context.setAuthentication(authentication);
                SecurityContextHolder.setContext(context);
            }
        } catch (CustomException e) {
            setErrorResponse(response, e.getErrorCode());
        }

        filterChain.doFilter(request, response);
    }

    private void setErrorResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setStatus(errorCode.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        response.getWriter().write(objectMapper.writeValueAsString(ErrorResponse.of(errorCode.getMessage(), errorCode.getStatus())));
    }
}
