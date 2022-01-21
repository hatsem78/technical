package com.technicalchallenge.app.response;

public class ResponseRequest {
    private Integer code;
    private String message;

    public ResponseRequest(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseRequest() {}

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
