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
    private Long id;

    private String nickname;

    private String phone;

    private String password;

    private String avatar;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}
