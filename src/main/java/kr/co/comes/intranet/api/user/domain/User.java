package kr.co.comes.intranet.api.user.domain;

import kr.co.comes.intranet.api.user.common.PasswordHashUtil;
import kr.co.comes.intranet.common.exception.CommonException;
import kr.co.comes.intranet.common.exception.ResponseCode;
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

    public void verifyPassword(String plainPwd) throws CommonException {
        if(!PasswordHashUtil.verifyPassword(plainPwd, this.pwd)){
            throw new CommonException(ResponseCode.NOT_MATCH_PASSWORD);
        }
    }
}
