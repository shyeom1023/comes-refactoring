package kr.co.comes.intranet.infra.resolver;

import kr.co.comes.intranet.api.auth.AuthService;
import kr.co.comes.intranet.api.auth.model.AuthUser;
import kr.co.comes.intranet.api.user.dto.UserDto;
import kr.co.comes.intranet.common.exception.CommonAuthException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.support.ModelAndViewContainer;

@AllArgsConstructor
@Slf4j
public class UserSessionArgumentResolverMock implements AuthArgumentResolver {

    private final AuthService authService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(AuthUser.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) throws Exception {

        val accessToken = getContextAccessToken();
        log.info("resolver token: {}", accessToken);
        if (accessToken == null) {
            throw new CommonAuthException();
        }
        if (accessToken.equals("skip")) {
            return authService.authentication(new UserDto.LoginRequest("shyeom", "duatjrgus1"));
        }
        return authService.getAuthUser(accessToken);
    }

    public String getContextAccessToken() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            return (String) requestAttributes.getAttribute("accessToken", ServletRequestAttributes.SCOPE_REQUEST);
            // Now you can use the accessToken
        }

        return null;
    }
}
