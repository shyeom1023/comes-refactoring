package kr.co.comes.intranet.infra.resolver;

import kr.co.comes.intranet.api.auth.AuthService;
import kr.co.comes.intranet.api.auth.model.AuthUser;
import kr.co.comes.intranet.common.exception.CommonException;
import kr.co.comes.intranet.common.exception.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.support.ModelAndViewContainer;

@AllArgsConstructor
public class UserSessionArgumentResolver implements AuthArgumentResolver {

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
        if (accessToken == null) {
            throw new CommonException(ResponseCode.NOT_AUTH_USER);
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
