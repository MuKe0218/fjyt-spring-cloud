package com.fjyt.common.utils.security;

import com.fjyt.common.domain.LoginUser;
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
    /**
     * 获取当前用户缓存信息, 如果未登录，则抛出异常
     *
     * @param token 前端传递的认证信息
     * @return 用户缓存信息
     */
    public LoginUser getLoginUser(String token)
    {
        return tokenService.getLoginUser(token);
    }

    /**
     * 验证当前用户有效期, 如果相差不足120分钟，自动刷新缓存
     *
     * @param loginUser 当前用户信息
     */
    public void verifyLoginUserExpire(LoginUser loginUser)
    {
        tokenService.verifyToken(loginUser);
    }
}
