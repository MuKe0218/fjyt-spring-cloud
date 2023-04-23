package com.fjyt.auth.controller;

import com.fjyt.common.domain.LoginDto;
import com.fjyt.common.utils.JwtUtils;
import com.fjyt.common.utils.security.AuthUtil;
import com.fjyt.common.utils.security.SecurityUtils;
import com.fjyt.common.utils.StringUtils;
import com.fjyt.common.domain.LoginUserBo;
import com.fjyt.auth.service.SysLoginService;
import com.fjyt.common.utils.security.TokenService;
import com.fjyt.common.domain.R;
import com.fjyt.system.service.KapchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author keQiLong
 * @date 2023年04月20日 15:11
 */
@RestController
public class TokenController {

    @Autowired
    private SysLoginService sysLoginService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private KapchaService kapchaService;
    //登录
    @PostMapping("/login")
    public R login(@RequestBody LoginDto loginDto){
        LoginUserBo loginUserBo = sysLoginService.login(loginDto.getUsername(),loginDto.getPassword());
        kapchaService.checkCaptcha(loginDto.getUuid(),loginDto.getCode());
        return R.ok(tokenService.createToken(loginUserBo),"生成token成功");
    }

    //登出
    @DeleteMapping("/logout")
    public R logout(HttpServletRequest request){
        String token = SecurityUtils.getToken(request);
        if (StringUtils.isNotEmpty(token))
        {
            String username = JwtUtils.getUserName(token);
            // 删除用户缓存记录
            AuthUtil.logoutByToken(token);
            // 记录用户退出日志
            sysLoginService.logout(username);
        }
        return R.ok("get");
    }
}
