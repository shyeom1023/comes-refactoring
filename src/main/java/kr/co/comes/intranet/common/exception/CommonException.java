package kr.co.comes.intranet.common.exception;

import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {
    private final ResponseCode responseCode;

    public CommonException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }

}