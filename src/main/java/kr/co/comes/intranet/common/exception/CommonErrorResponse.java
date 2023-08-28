package kr.co.comes.intranet.common.exception;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommonErrorResponse {
    String code;
    String message;
    Object payload;

    public CommonErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public CommonErrorResponse(String code, String message, Object payload) {
        this.code = code;
        this.message = message;
        this.payload = payload;
    }
}
