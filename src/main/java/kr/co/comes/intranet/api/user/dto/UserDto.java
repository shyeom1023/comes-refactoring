package kr.co.comes.intranet.api.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class UserDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class UserCreateRequest {
        String userId;
        String pwd;
        String name;
        String role;
        String address;
        String sex;
        String telNo;
        String email;
        Long departmentId;
        String state;
        String employeeType;
        String position;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime joinDate;
        Long updatedBy;
        Long createdBy;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class UserResponse{
        Long id;
        String userId;
        String name;
        String role;
        String address;
        String sex;
        String telNo;
        String email;
        Long departmentId;
        String state;
        String employeeType;
        String position;
        LocalDateTime joinDate;
        LocalDateTime retireDate;
        Long updatedBy;
        Long createdBy;
    }
}
