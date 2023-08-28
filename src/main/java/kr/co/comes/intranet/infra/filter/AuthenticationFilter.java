package kr.co.comes.intranet.infra.filter;


import kr.co.comes.intranet.api.auth.AuthService;
import kr.co.comes.intranet.common.dto.Parttern;
import kr.co.comes.intranet.common.exception.CommonException;
import kr.co.comes.intranet.common.exception.ResponseCode;
import kr.co.comes.intranet.common.type.CookieType;
import kr.co.comes.intranet.common.type.FilterOrderType;
import kr.co.comes.intranet.infra.properties.AuthIgnorePattern;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Component
@AllArgsConstructor
public class AuthenticationFilter implements Filter, Ordered {

    private final AuthService authService;
    private final AuthIgnorePattern authIgnorePattern;
    private final Environment env;
    private final AntPathMatcher matcher = new AntPathMatcher();

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("init AuthenticationFilter");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {


        try {
            if (shouldIgnoreAuthentication((HttpServletRequest) request)) {
                chain.doFilter(request, response);
                return;
            }

            String accessToken = authService.getCookieValue((HttpServletRequest) request, CookieType.ACCESS_TOKEN.getText());
            String refreshToken = authService.getCookieValue((HttpServletRequest) request, CookieType.REFRESH_TOKEN.getText());

            if (AuthService.isTokenExpired(accessToken) && !AuthService.isTokenExpired(refreshToken)) {
                accessToken = AuthService.refreshAccessToken(refreshToken);
                authService.applyCookie((HttpServletResponse) response, CookieType.ACCESS_TOKEN.getText(), accessToken);
                chain.doFilter(request, response);
            }else if(Arrays.asList(env.getActiveProfiles()).contains("local") && accessToken.equals("skip")){
                chain.doFilter(request, response);
            }else {
                throw new CommonException(ResponseCode.NOT_AUTH_USER);
            }


        } catch (CommonException e) {
            if (e.getResponseCode().equals(ResponseCode.NOT_AUTH_USER)) {
                authService.revokeTokens((HttpServletRequest) request, (HttpServletResponse) response);
                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                return;
            }
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        log.info("destroy AuthenticationFilter");
    }

    private boolean shouldIgnoreAuthentication(HttpServletRequest request) {
        for (Parttern parttern : authIgnorePattern.getIgnorePatterns()) {
            if (
                    matcher.match(request.getRequestURI(), parttern.getUrl()) &&
                            (parttern.getMethod().equals("ANY") || request.getMethod().equals(parttern.getMethod()))
            ) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getOrder() {
        return FilterOrderType.AUTH.ordinal();
    }
}