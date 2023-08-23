package kr.co.comes.intranet.common.exception;

public enum ResponseCode {

    /***
     * common response code
     */
    SUCCESS("200", "Success"),
    INVALID_INPUT("400", "Invalid input"),
    NOT_FOUND("404", "Resource not found"),
    INTERNAL_ERROR("500", "Internal server error"),

    /**
     * custom response code
     */

    NOT_FOUND_USER("1000", "Not found User")

    ;

    private final String code;
    private final String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
