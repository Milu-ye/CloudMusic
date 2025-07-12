package com.eren.cloudmusic.account.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.crypto.digest.DigestUtil;
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
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;

@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    @Resource
    private AccountMapper accountMapper;

    private byte[] key;

    @PostConstruct
    public void init() throws IOException {
        try(InputStream reader = AccountServiceImpl.class.getClassLoader().getResourceAsStream("private_key.txt")){
            if (reader == null){
                throw new IOException("private_key.txt not found");
            }
            key = IoUtil.readBytes(reader);
        }catch (IOException e){
            log.error("private_key.txt not found");
        }

    }

    @Override
    public String login(String phone, String password){

        // 使用 lambda 查询构造器查询用户
        Account account = this.getOne(new QueryWrapper<Account>().lambda()
                .select(Account::getAccountId)
                .eq(Account::getPhone, phone)
                .eq(Account::getPassword, DigestUtil.md5Hex16( password)));

        if (account == null) {
            throw new ServerException(AccountCode.PHONE_OR_PASSWORD_ERROR);
        }

        return JWTUtil.createToken(Map.of("accountId", account.getAccountId()),key);

    }

    @Override
    public boolean register(String phone, String password, String nickname) {
        if (!phone.matches("^1[3-9]\\d{9}$")){
            throw new ServerException(AccountCode.PHONE_FORMAT_ERROR);
        }
        return this.save(new Account(nickname,phone, DigestUtil.md5Hex16( password)));
    }

}
