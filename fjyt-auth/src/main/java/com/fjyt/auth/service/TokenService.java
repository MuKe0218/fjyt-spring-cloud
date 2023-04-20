package com.fjyt.auth.service;

import com.fjyt.auth.pojo.BO.LoginUserBo;
import com.fjyt.common.constant.CacheConstants;
import com.fjyt.common.constant.SecurityConstants;
import com.fjyt.common.utils.JwtUtils;
import com.fjyt.common.utils.ip.IpUtils;
import com.fjyt.common.utils.uuid.IdUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author keQiLong
 * @date 2023年04月20日 15:52
 */
@Service
public class TokenService {

    protected static final long MILLIS_SECOND = 1000;
    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;
    private final static long expireTime = CacheConstants.EXPIRATION;
    private final static String ACCESS_TOKEN = CacheConstants.LOGIN_TOKEN_KEY;
    /**
     * 创建令牌
     */
    public Map<String, Object> createToken(LoginUserBo loginUser) {
        String token = IdUtils.fastUUID();
        Long userId = 123156464L;//loginUser.getSysUser().getUserId();
        String userName = loginUser.getUsername();
        loginUser.setToken(token);
        loginUser.setUserid(userId);
        loginUser.setUsername(userName);
        loginUser.setIpaddr(IpUtils.getIpAddr());
        refreshToken(loginUser);

        // Jwt存储信息
        Map<String, Object> claimsMap = new HashMap<String, Object>();
        claimsMap.put(SecurityConstants.USER_KEY, token);
        claimsMap.put(SecurityConstants.DETAILS_USER_ID, userId);
        claimsMap.put(SecurityConstants.DETAILS_USERNAME, userName);

        // 接口返回信息
        Map<String, Object> rspMap = new HashMap<String, Object>();
        rspMap.put("access_token", JwtUtils.createToken(claimsMap));
        rspMap.put("expires_in", expireTime);
        return rspMap;
    }
    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    public void refreshToken(LoginUserBo loginUser)
    {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_MINUTE);
        // 根据uuid将loginUser缓存
        String userKey = getTokenKey(loginUser.getToken());
        // redisService.setCacheObject(userKey, loginUser, expireTime, TimeUnit.MINUTES);
    }
    private String getTokenKey(String token)
    {
        return ACCESS_TOKEN + token;
    }
}
