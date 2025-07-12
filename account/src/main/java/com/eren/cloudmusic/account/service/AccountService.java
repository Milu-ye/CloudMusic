package com.eren.cloudmusic.account.service;


public interface AccountService {
    String login(String phone, String password);
    boolean register(String phone, String password,String nickname);
}
