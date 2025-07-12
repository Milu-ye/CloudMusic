package com.eren.cloudmusic.account.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@TableName("user_info")
public class User {

    @TableId
    private Long userId;

    private Long accountId;

    private String age;

    private String sex;

    private String birthday;

    private String address;

    private String email;

    private String signature;
}
