package iskander.tabaev.springsecuritybasic.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class CustomAtFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(CustomAtFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("At filter is working...");
        filterChain.doFilter(request, response);
    }
}
