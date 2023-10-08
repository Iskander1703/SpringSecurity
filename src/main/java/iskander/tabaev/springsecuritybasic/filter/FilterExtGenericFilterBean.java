package iskander.tabaev.springsecuritybasic.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class FilterExtGenericFilterBean extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("Hello world");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
