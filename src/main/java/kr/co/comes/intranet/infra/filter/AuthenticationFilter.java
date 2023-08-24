package kr.co.comes.intranet.infra.filter;


import kr.co.comes.intranet.api.auth.AuthService;
import kr.co.comes.intranet.common.dto.Parttern;
import kr.co.comes.intranet.common.exception.CommonException;
import kr.co.comes.intranet.common.exception.ResponseCode;
import kr.co.comes.intranet.infra.properties.AuthIgnorePattern;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@AllArgsConstructor
public class AuthenticationFilter implements Filter {

    private final AuthService authService;
    private final AuthIgnorePattern authIgnorePattern;
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

            String accessToken = authService.getCookieValue((HttpServletRequest) request, "accessToken");
            String refreshToken = authService.getCookieValue((HttpServletRequest) request, "refreshToken");

            if (!AuthService.isTokenExpired(accessToken)) {
                authService.getAuthUser(accessToken);
            } else if (!AuthService.isTokenExpired(refreshToken)) {
                accessToken = AuthService.refreshAccessToken(refreshToken);
                authService.applyCookie((HttpServletResponse) response, "accessToken", accessToken);
                authService.getAuthUser(accessToken);
            } else {
                throw new CommonException(ResponseCode.NOT_AUTH_USER);
            }

            chain.doFilter(request, response);
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
}