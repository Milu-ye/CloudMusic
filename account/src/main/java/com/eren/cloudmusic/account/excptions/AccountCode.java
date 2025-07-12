package com.eren.cloudmusic.account.excptions;

import com.eren.cloudmusic.common.exceptions.ErrorCode;

public enum AccountCode implements ErrorCode {

    PHONE_OR_PASSWORD_ERROR(1004, "PHONE_OR_PASSWORD_ERROR"),
    PHONE_FORMAT_ERROR(1005, "PHONE_FORMAT_ERROR");



    private final int code;
    private final String message;

    AccountCode(int code, String message){
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
