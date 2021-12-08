package com.rrain.jwttokenauth.filter;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

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

            var url = request.getRequestURL();
            var query = request.getQueryString();
            if (query!=null) url.append('?').append(query);

            log.info(String.format(
                "Request #%s URL: %s", requestCnt, url
            ));
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

}
