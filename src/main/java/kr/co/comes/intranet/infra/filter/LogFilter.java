package kr.co.comes.intranet.infra.filter;

import kr.co.comes.intranet.common.type.FilterOrderType;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
public class LogFilter implements Filter, Ordered {

    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("init LogFilter");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        val uuid = generateUUID();

        try {
            requestInfo((HttpServletRequest) request, uuid);
            chain.doFilter(request, response);
        } finally {
            responseInfo((HttpServletResponse) response, uuid);
        }

    }

    public void requestInfo(HttpServletRequest request, String uuid) {
        MDC.put("uuid", uuid);
        log.info("[{}] {} {}", uuid, request.getMethod(), request.getRequestURI());
    }

    public void responseInfo(HttpServletResponse response, String uuid) {
        log.info("[{}] {}", uuid, response.getStatus());
        MDC.remove("uuid");
    }

    @Override
    public void destroy() {
        log.info("destroy LogFilter");
    }

    @Override
    public int getOrder() {
        return FilterOrderType.LOG.ordinal();
    }
}
