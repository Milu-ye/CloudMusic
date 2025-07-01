package com.eren.cloudmusic.account.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eren.cloudmusic.common.config.GlobalExceptionHandler;
import com.eren.cloudmusic.common.exceptions.ServerException;
import com.eren.cloudmusic.account.domain.entity.Account;
import com.eren.cloudmusic.account.excptions.AccountCode;
import com.eren.cloudmusic.account.mapper.AccountMapper;
import com.eren.cloudmusic.account.service.AccountService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    @Resource
    private AccountMapper accountMapper;

    private byte[] secretKey;

    @PostConstruct
    public void init() throws IOException {
        BufferedInputStream reader = IOUtils.buffer(AccountServiceImpl.class.getClassLoader().getResourceAsStream("private_key.txt"));
        int len ;
        byte[] buffer = new byte[1024];
        while ((len = reader.read(buffer, 0, buffer.length)) != -1){
            if (ArrayUtil.isEmpty(secretKey)){
                secretKey = Arrays.copyOfRange(buffer, 0, len);
            }
            else {
                secretKey = (byte[]) ArrayUtil.append(secretKey, Arrays.copyOfRange(buffer, 0, len));
            }
        }


    }

    @Override
    public String login(String phone, String password){

        // 使用 lambda 查询构造器查询用户
        Account account = this.getOne(new QueryWrapper<Account>().lambda()
                .select(Account::getId)
                .eq(Account::getPhone, phone)
                .eq(Account::getPassword, password));

        if (account == null) {
            throw new ServerException(AccountCode.PHONE_OR_PASSWORD_ERROR);
        }

        return JWTUtil.createToken(Map.of("accountId", account.getId()),secretKey);

    }

}
