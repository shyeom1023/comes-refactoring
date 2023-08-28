package kr.co.comes.intranet.common.exception;

public class CommonNotFoundException extends CommonException {
    public CommonNotFoundException() {
        super(ResponseCode.NOT_FOUND);
    }
}
