package kr.co.comes.intranet.infra.handler;

import kr.co.comes.intranet.common.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CommonException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonErrorResponse handleCommonException(CommonException e) {
        return new CommonErrorResponse(e.getResponseCode().getCode(), e.getMessage());
    }

    @ExceptionHandler(CommonPayloadException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonErrorResponse handleCommonPayloadException(CommonPayloadException e) {
        return new CommonErrorResponse(e.getResponseCode().getCode(), e.getMessage(), e.getPayload());
    }

    @ExceptionHandler(CommonNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public CommonErrorResponse handleCommonNotFoundException(CommonException e) {
        return new CommonErrorResponse(e.getResponseCode().getCode(), e.getMessage());
    }

    @ExceptionHandler(CommonInvalidParameterException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public CommonErrorResponse handleCommonInvalidParameterException(CommonException e) {
        return new CommonErrorResponse(e.getResponseCode().getCode(), e.getMessage());
    }

    @ExceptionHandler(CommonAuthException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public CommonErrorResponse handleCommonAuthException(CommonException e) {
        return new CommonErrorResponse(e.getResponseCode().getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonErrorResponse handleException(Exception e) {
        return new CommonErrorResponse(ResponseCode.INTERNAL_ERROR.getCode(), e.getMessage());
    }

}
