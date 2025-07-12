package com.eren.cloudmusic.account.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@TableName("account_info")
@AllArgsConstructor
@Data
public class Account {

    @TableId
    private Long accountId;

    private String nickname;

    private String phone;

    private String password;

    private String avatar;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    public Account(String nickname, String phone, String password){
        this.nickname = nickname;
        this.phone = phone;
        this.password = password;
    }

    public Account(Long accountId){
        this.accountId = accountId;
    }

}
