package com.fjyt.auth.controller;

import com.fjyt.auth.pojo.BO.LoginUserBo;
import com.fjyt.auth.pojo.DTO.LoginDto;
import com.fjyt.auth.service.SysLoginService;
import com.fjyt.auth.service.TokenService;
import com.fjyt.common.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/login")
    public R login(@RequestBody LoginDto loginDto){
        LoginUserBo loginUserBo = sysLoginService.login(loginDto.getUsername(),loginDto.getPassword());
        return R.ok(tokenService.createToken(loginUserBo),"生成token成功");
    }
}
