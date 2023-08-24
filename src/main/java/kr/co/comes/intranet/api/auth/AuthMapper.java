package kr.co.comes.intranet.api.auth;

import kr.co.comes.intranet.api.auth.model.AuthUser;
import kr.co.comes.intranet.api.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public AuthUser toAuthUser(User model) {
        return new AuthUser(
                model.getId(),
                model.getUserId(),
                model.getName(),
                model.getRole(),
                model.getDepartmentId(),
                model.getState(),
                model.getEmployeeType(),
                model.getPosition()
        );
    }
}
