package kr.co.comes.intranet.api.user;

import kr.co.comes.intranet.api.user.domain.User;
import kr.co.comes.intranet.api.user.dto.UserDto;
import kr.co.comes.intranet.api.user.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toEntity(User model) {
        return new UserEntity(
                model.getId(),
                model.getUserId(),
                model.getPwd(),
                model.getName(),
                model.getRole(),
                model.getAddress(),
                model.getSex(),
                model.getTelNo(),
                model.getEmail(),
                model.getDepartmentId(),
                model.getState(),
                model.getEmployeeType(),
                model.getPosition(),
                model.getJoinDate(),
                model.getRetireDate(),
                model.getUpdatedBy(),
                model.getCreatedBy()
        );
    }

    public UserEntity toEntity(UserDto.UserCreateRequest request) {
        return new UserEntity(
                0L,
                request.getUserId(),
                request.getPwd(),
                request.getName(),
                request.getRole(),
                request.getAddress(),
                request.getSex(),
                request.getTelNo(),
                request.getEmail(),
                request.getDepartmentId(),
                request.getState(),
                request.getEmployeeType(),
                request.getPosition(),
                request.getJoinDate(),
                null,
                request.getUpdatedBy(),
                request.getCreatedBy()
        );
    }

    public User toModel(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getUserId(),
                entity.getPwd(),
                entity.getName(),
                entity.getRole(),
                entity.getAddress(),
                entity.getSex(),
                entity.getTelNo(),
                entity.getEmail(),
                entity.getDepartmentId(),
                entity.getState(),
                entity.getEmployeeType(),
                entity.getPosition(),
                entity.getJoinDate(),
                entity.getRetireDate(),
                entity.getUpdatedBy(),
                entity.getCreatedBy()
        );
    }

    public UserDto.UserResponse toResponse(User model) {
        return new UserDto.UserResponse(
                model.getId(),
                model.getUserId(),
                model.getName(),
                model.getRole(),
                model.getAddress(),
                model.getSex(),
                model.getTelNo(),
                model.getEmail(),
                model.getDepartmentId(),
                model.getState(),
                model.getEmployeeType(),
                model.getPosition(),
                model.getJoinDate(),
                model.getRetireDate(),
                model.getUpdatedBy(),
                model.getCreatedBy()
        );
    }
}
