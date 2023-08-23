package kr.co.comes.intranet.api;

import kr.co.comes.intranet.api.user.UserMapper;
import kr.co.comes.intranet.api.user.UserService;
import kr.co.comes.intranet.api.user.domain.User;
import kr.co.comes.intranet.api.user.dto.UserDto;
import kr.co.comes.intranet.common.exception.CommonException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public void create(@RequestBody UserDto.UserCreateRequest request) {
        userService.create(request);
    }

    @GetMapping("/{userId}")
    public UserDto.UserResponse getUserById(@PathVariable String userId) throws CommonException {
        return userMapper.toResponse(userService.getUser(userId));
    }

    @GetMapping("/me")
    public UserDto.UserResponse getUserByMe(User user) {
        return userMapper.toResponse(user);
    }
}
