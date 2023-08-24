package kr.co.comes.intranet.api.auth.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@ToString
@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthUser {

    Long id;
    String userId;
    String name;
    String role;
    Long departmentId;
    String state;
    String employeeType;
    String position;

}
