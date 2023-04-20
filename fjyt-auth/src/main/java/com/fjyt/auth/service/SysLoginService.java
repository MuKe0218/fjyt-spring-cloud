package com.fjyt.auth.service;

import com.fjyt.auth.pojo.BO.LoginUserBo;
import org.springframework.stereotype.Service;

/**
 * @author keQiLong
 * @date 2023年04月20日 15:47
 */
@Service
public class SysLoginService {
    /**
     * 登录
     */
    public LoginUserBo login(String username, String password)
    {
        LoginUserBo userInfo = new LoginUserBo();
        userInfo.setUsername(username);
        return userInfo;
    }
}
