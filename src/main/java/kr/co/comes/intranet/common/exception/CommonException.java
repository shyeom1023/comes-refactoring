package kr.co.comes.intranet.common.exception;

public class CommonException extends Exception {

    private final ResponseCode responseCode;

    public CommonException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }

    public CommonException(ResponseCode responseCode, Throwable cause) {
        super(responseCode.getMessage(), cause);
        this.responseCode = responseCode;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }


}
