package com.eren.cloudmusic.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eren.cloudmusic.account.domain.entity.Account;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {

}
