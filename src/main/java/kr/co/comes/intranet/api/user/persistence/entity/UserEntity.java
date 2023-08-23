package kr.co.comes.intranet.api.user.persistence.entity;

import kr.co.comes.intranet.api.user.common.PasswordHashUtil;
import kr.co.comes.intranet.common.db.DefaultEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity(name = "user")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity extends DefaultEntity {

    Long id;
    @Column(name = "user_id")
    String userId;
    @Column(name = "pwd")
    String pwd;
    @Column(name = "name")
    String name;
    @Column(name = "role")
    String role;
    @Column(name = "address")
    String address;
    @Column(name = "sex")
    String sex;
    @Column(name = "tel_no")
    String telNo;
    @Column(name = "email")
    String email;
    @Column(name = "department_id")
    Long departmentId;
    @Column(name = "state")
    String state;
    @Column(name = "employee_type")
    String employeeType;
    @Column(name = "position")
    String position;
    @Column(name = "join_date")
    LocalDateTime joinDate;
    @Column(name = "retire_date")
    LocalDateTime retireDate;
    @Column(name = "updated_by")
    Long updatedBy;
    @Column(name = "created_by")
    Long createdBy;

    public void encrypt() {
        this.pwd = PasswordHashUtil.hashPassword(pwd);
    }
}
