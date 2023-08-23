package kr.co.comes.intranet.api.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    Long id;
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
    LocalDateTime joinDate;
    LocalDateTime retireDate;
    Long updatedBy;
    Long createdBy;
}
