package kr.co.comes.intranet.api;

import kr.co.comes.intranet.api.auth.AuthService;
import kr.co.comes.intranet.api.user.dto.UserDto;
import kr.co.comes.intranet.common.exception.CommonException;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public void login(HttpServletResponse response, @RequestBody UserDto.LoginRequest request) throws CommonException {
        val user = authService.authentication(request);
        authService.applyAuthInfo(response, user.getUserId());
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response){
        authService.revokeTokens(request, response);
    }
}
