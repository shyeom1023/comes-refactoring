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

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/login")
@AllArgsConstructor
public class LoginController {

    private final AuthService authService;

    @PostMapping
    public void login(HttpSession session, @RequestBody UserDto.LoginRequest request) throws CommonException {
        val user = authService.authentication(request);
        session.setAttribute("loggedInUser", user);
    }
}
