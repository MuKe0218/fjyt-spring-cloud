package com.fjyt.common.utils.security;

import com.fjyt.common.utils.StringUtils;
import com.fjyt.common.domain.LoginUserBo;
import com.fjyt.common.constant.CacheConstants;
import com.fjyt.common.constant.SecurityConstants;
import com.fjyt.common.redis.service.RedisService;
import com.fjyt.common.utils.JwtUtils;
import com.fjyt.common.utils.ip.IpUtils;
import com.fjyt.common.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private RedisService redisService;
    /**
     * 创建令牌
     */
    public Map<String, Object> createToken(LoginUserBo loginUser) {
        String token = IdUtils.fastUUID();
        Long userId = loginUser.getSysUser().getUserId();
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
     * 删除用户缓存信息
     */
    public void delLoginUser(String token)
    {
        if (StringUtils.isNotEmpty(token))
        {
            String userkey = JwtUtils.getUserKey(token);
            redisService.deleteObject(getTokenKey(userkey));
        }
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
        redisService.setCacheObject(userKey, loginUser, expireTime, TimeUnit.MINUTES);
    }
    private String getTokenKey(String token)
    {
        return ACCESS_TOKEN + token;
    }
}