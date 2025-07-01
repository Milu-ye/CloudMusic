package com.eren.cloudmusic.common.exceptions;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ServerException extends RuntimeException {
    public int code;
    public ServerException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }
    public ServerException(int code,String message) {
        super(message);
        this.code = code;
    }
}
