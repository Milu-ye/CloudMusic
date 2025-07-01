package com.eren.cloudmusic.common.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Response {
    public int code;
    public String message;
}
