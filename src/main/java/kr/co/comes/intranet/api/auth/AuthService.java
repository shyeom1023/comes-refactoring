package kr.co.comes.intranet.api.auth;

import kr.co.comes.intranet.api.user.UserService;
import kr.co.comes.intranet.api.user.domain.User;
import kr.co.comes.intranet.api.user.dto.UserDto;
import kr.co.comes.intranet.common.exception.CommonException;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserService userService;

    public User authentication(UserDto.LoginRequest request) throws CommonException {
        val user = userService.getUser(request.getUserId());
        user.verifyPassword(request.getPwd());

        return user;
    }
}
