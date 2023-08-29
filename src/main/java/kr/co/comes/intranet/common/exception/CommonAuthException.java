package kr.co.comes.intranet.common.exception;

public class CommonAuthException extends CommonException {
    public CommonAuthException() {
        super(ResponseCode.NOT_AUTH_USER);
    }
}
