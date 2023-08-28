package kr.co.comes.intranet.common.exception;

import lombok.Getter;

@Getter
public class CommonPayloadException extends CommonException {

    private final ResponseCode responseCode;
    private final Object payload;

    public CommonPayloadException(ResponseCode responseCode, Object payload) {
        super(responseCode);
        this.responseCode = responseCode;
        this.payload = payload;
    }

}
