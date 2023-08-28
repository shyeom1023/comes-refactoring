package kr.co.comes.intranet.infra.resolver;

import kr.co.comes.intranet.api.auth.AuthService;
import kr.co.comes.intranet.api.auth.model.AuthUser;
import kr.co.comes.intranet.api.user.dto.UserDto;
import kr.co.comes.intranet.common.exception.CommonException;
import kr.co.comes.intranet.common.exception.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@AllArgsConstructor
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

        val httpServletRequest = Objects.requireNonNull(webRequest.getNativeRequest(HttpServletRequest.class));
        val accessToken = authService.getCookieValue(httpServletRequest, "accessToken");
        if (accessToken == null) {
            throw new CommonException(ResponseCode.NOT_AUTH_USER);
        }
        if (accessToken.equals("skip")) {
            return authService.authentication(new UserDto.LoginRequest("shyeom", "duatjrgus1"));
        }
        return authService.getAuthUser(accessToken);
    }
}
