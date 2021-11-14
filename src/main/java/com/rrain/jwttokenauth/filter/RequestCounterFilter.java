package com.rrain.jwttokenauth.filter;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@CommonsLog
public class RequestCounterFilter implements Filter {

    private final AtomicInteger requestCnt = new AtomicInteger(0);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest request){
            int requestCnt = this.requestCnt.getAndIncrement();
            log.info(String.format(
                "Request #%s Path: %s", requestCnt, request.getRequestURI()
            ));
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

}
