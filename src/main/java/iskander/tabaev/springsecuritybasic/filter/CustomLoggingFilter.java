package iskander.tabaev.springsecuritybasic.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class CustomLoggingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(CustomLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("Client IP: {}, Host: {}, User: {}, Port: {}",
                request.getRemoteAddr(), request.getRemoteHost(), request.getRemoteUser(), request.getRemotePort());
        filterChain.doFilter(request, response);
    }
}
