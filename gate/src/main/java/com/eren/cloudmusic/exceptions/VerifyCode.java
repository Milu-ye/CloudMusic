package com.eren.cloudmusic.exceptions;

import com.eren.cloudmusic.common.exceptions.ErrorCode;

public enum VerifyCode implements ErrorCode {

    TOKEN_EXP(2001, "TOKEN_EXPIRES");

    private final int code;
    private final String message;

    VerifyCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
