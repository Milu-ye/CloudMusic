package com.eren.cloudmusic.account.controller;

import com.eren.cloudmusic.account.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account/verify")
@Tag(name = "验证")
public class VerifyController {

    @Resource
    private AccountService accountService;

    @GetMapping("/login")
    @Operation(summary = "用户登录")
    public String verify(String phone, String password){
        return accountService.login(phone,password);
    }

    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public boolean register(String phone, String password, String nickname){
        return accountService.register(phone, password, nickname);
    }
}
