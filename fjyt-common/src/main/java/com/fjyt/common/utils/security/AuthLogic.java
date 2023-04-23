package com.fjyt.common.utils.security;

import com.fjyt.common.utils.SpringUtils;

/**
 * @author keQiLong
 * @date 2023年04月23日 14:57
 * Token 权限验证，逻辑实现类
 */
public class AuthLogic {

    public TokenService tokenService = SpringUtils.getBean(TokenService.class);
    /**
     * 会话注销
     */
    public void logout()
    {
        String token = SecurityUtils.getToken();
        if (token == null)
        {
            return;
        }
        logoutByToken(token);
    }
    /**
     * 会话注销，根据指定Token
     */
    public void logoutByToken(String token)
    {
        tokenService.delLoginUser(token);
    }
}
