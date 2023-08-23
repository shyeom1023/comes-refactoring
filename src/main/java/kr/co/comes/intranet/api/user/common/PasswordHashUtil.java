package kr.co.comes.intranet.api.user.common;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHashUtil {

    public static String hashPassword(String pwd){
        return BCrypt.hashpw(pwd, BCrypt.gensalt());
    }

    public static boolean verifyPassword(String plainPwd, String hashedPwd){
        return BCrypt.checkpw(plainPwd, hashedPwd);
    }

}
