package com.fjyt.system.controller;

import com.fjyt.common.domain.LoginUser;
import com.fjyt.common.domain.R;
import com.fjyt.common.domain.SysUser;
import com.fjyt.common.utils.JwtUtils;
import com.fjyt.common.utils.StringUtils;
import com.fjyt.common.utils.security.SecurityUtils;
import com.fjyt.common.utils.security.TokenService;
import com.fjyt.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Hashtable;
import java.util.Map;

/**
 * @author keQiLong
 * @date 2023年04月26日 17:33
 * 个人信息 业务处理
 */
@RestController
@RequestMapping("/user/profile")
public class SysProfileController {

    @Autowired
    private ISysUserService userService;
    @Autowired
    private TokenService tokenService;
    /**
     * 个人信息
     */
    @GetMapping
    public R profile(HttpServletRequest request)
    {
        String token = SecurityUtils.getToken(request);
        String username = JwtUtils.getUserName(token);
        SysUser user = userService.selectUserByUserName(username);
        Map ajax = new Hashtable();
        ajax.put("user",user);
        // ajax.put("roleGroup", userService.selectUserRoleGroup(username));
        // ajax.put("postGroup", userService.selectUserPostGroup(username));
        return R.ok(ajax);
    }

    /**
     * 修改用户
     */
    @PutMapping
    public R updateProfile(@RequestBody SysUser user)
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser sysUser = loginUser.getSysUser();
        user.setUserName(sysUser.getUserName());
        if (StringUtils.isNotEmpty(user.getPhonenumber()) && !userService.checkPhoneUnique(user))
        {
            return R.fail("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        else if (StringUtils.isNotEmpty(user.getEmail()) && !userService.checkEmailUnique(user))
        {
            return R.fail("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setUserId(sysUser.getUserId());
        user.setPassword(null);
        user.setAvatar(null);
        user.setDeptId(null);
        if (userService.updateUserProfile(user) > 0)
        {
             // 更新缓存用户信息
            loginUser.getSysUser().setNickName(user.getNickName());
            loginUser.getSysUser().setPhonenumber(user.getPhonenumber());
            loginUser.getSysUser().setEmail(user.getEmail());
            loginUser.getSysUser().setSex(user.getSex());
            tokenService.setLoginUser(loginUser);
            return R.ok();
        }
        return R.fail("修改个人信息异常，请联系管理员");
    }

    /**
     * 重置密码
     */
    @PutMapping("/updatePwd")
    public R updatePwd(String oldPassword, String newPassword)
    {
        String username = SecurityUtils.getUsername();
        SysUser user = userService.selectUserByUserName(username);
        String password = user.getPassword();
        if (!SecurityUtils.matchesPassword(oldPassword, password))
        {
            return R.fail("修改密码失败，旧密码错误");
        }
        if (SecurityUtils.matchesPassword(newPassword, password))
        {
            return R.fail("新密码不能与旧密码相同");
        }
        if (userService.resetUserPwd(username, SecurityUtils.encryptPassword(newPassword)) > 0)
        {
            // 更新缓存用户密码
            LoginUser loginUser = SecurityUtils.getLoginUser();
            loginUser.getSysUser().setPassword(SecurityUtils.encryptPassword(newPassword));
            tokenService.setLoginUser(loginUser);
            return R.ok();
        }
        return R.fail("修改密码异常，请联系管理员");
    }
}
