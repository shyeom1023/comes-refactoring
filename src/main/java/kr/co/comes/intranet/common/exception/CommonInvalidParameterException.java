package kr.co.comes.intranet.common.exception;

public class CommonInvalidParameterException extends CommonException {
    public CommonInvalidParameterException() {
        super(ResponseCode.INVALID_INPUT);
    }
}
