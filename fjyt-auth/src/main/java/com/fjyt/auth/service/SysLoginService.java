package com.fjyt.auth.service;

import com.fjyt.system.api.RemoteUserService;
import com.fjyt.common.domain.LoginUserBo;
import com.fjyt.common.domain.SysUser;
import com.fjyt.common.constant.CacheConstants;
import com.fjyt.common.constant.SecurityConstants;
import com.fjyt.common.constant.UserConstants;
import com.fjyt.common.domain.R;
import com.fjyt.common.enums.UserStatus;
import com.fjyt.common.exception.ServiceException;
import com.fjyt.common.redis.service.RedisService;
import com.fjyt.common.text.Convert;
import com.fjyt.common.utils.StringUtils;
import com.fjyt.common.utils.ip.IpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author keQiLong
 * @date 2023年04月20日 15:47
 */
@Service
public class SysLoginService {

    @Autowired
    private RedisService redisService;
    @Autowired
    private RemoteUserService remoteUserService;
    @Autowired
    private SysPasswordService passwordService;
    /**
     * 登录
     */
    public LoginUserBo login(String username, String password)
    {
        // 用户名或密码为空 错误
        if (StringUtils.isAnyBlank(username, password))
        {   //记录日志
            //recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, "用户/密码必须填写");
            throw new ServiceException("用户/密码必须填写");
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH)
        {
            //recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, "用户密码不在指定范围");
            throw new ServiceException("用户密码不在指定范围");
        }
        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH)
        {
            //recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, "用户名不在指定范围");
            throw new ServiceException("用户名不在指定范围");
        }
        // IP黑名单校验
        String blackStr = Convert.toStr(redisService.getCacheObject(CacheConstants.SYS_LOGIN_BLACKIPLIST));
        if (IpUtils.isMatchedIp(blackStr, IpUtils.getIpAddr()))
        {
            //recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, "很遗憾，访问IP已被列入系统黑名单");
            throw new ServiceException("很遗憾，访问IP已被列入系统黑名单");
        }
        // 查询用户信息
        R<LoginUserBo> userResult = remoteUserService.getUserInfo(username, SecurityConstants.INNER);
        if (StringUtils.isNull(userResult) || StringUtils.isNull(userResult.getData()))
        {
            //recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, "登录用户不存在");

            throw new ServiceException("登录用户：" + username + " 不存在",500);
        }

        if (R.FAIL == userResult.getCode())
        {
            throw new ServiceException(userResult.getMsg());
        }

        LoginUserBo userInfo = userResult.getData();
        SysUser user = userResult.getData().getSysUser();
        if (UserStatus.DELETED.getCode().equals(user.getDelFlag()))
        {
            //recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, "对不起，您的账号已被删除");
            throw new ServiceException("对不起，您的账号：" + username + " 已被删除");
        }
        if (UserStatus.DISABLE.getCode().equals(user.getStatus()))
        {
            //recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, "用户已停用，请联系管理员");
            throw new ServiceException("对不起，您的账号：" + username + " 已停用");
        }
        passwordService.validate(user, password);
//        recordLogService.recordLogininfor(username, Constants.LOGIN_SUCCESS, "登录成功");
//        LoginUserBo userInfo = new LoginUserBo();
//        userInfo.setUsername(username);
        return userInfo;
    }
    // 退出登录
    public void logout(String loginName)
    {
        System.out.println("退出成功");
        //recordLogService.recordLogininfor(loginName, Constants.LOGOUT, "退出成功");
    }
}
