package iskander.tabaev.springsecuritybasic.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class CustomLoggingAuthenticationUserFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(CustomLoggingAuthenticationUserFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            logger.info("Username: {}, authorities: {}", authentication.getPrincipal(), authentication.getAuthorities());
        }
        filterChain.doFilter(request, response);
    }
}
