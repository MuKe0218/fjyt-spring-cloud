package com.fjyt.system.controller;

import com.fjyt.common.domain.R;
import com.fjyt.common.utils.StringUtils;
import com.fjyt.common.domain.LoginUserBo;
import com.fjyt.common.domain.SysUser;
import com.fjyt.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author keQiLong
 * @date 2023年04月23日 14:27
 * 用户信息
 */
@RestController
@RequestMapping("/user")
public class SysUserController {


    @Autowired
    private ISysUserService userService;
    /**
     * 获取当前用户信息
     */
    @GetMapping("/info/{username}")
    public R<LoginUserBo> info(@PathVariable("username") String username)
    {
        SysUser sysUser = userService.selectUserByUserName(username);
        if (StringUtils.isNull(sysUser))
        {
            return R.fail("用户名或密码错误");
        }
//        // 角色集合
//        Set<String> roles = permissionService.getRolePermission(sysUser);
//        // 权限集合
//        Set<String> permissions = permissionService.getMenuPermission(sysUser);
        LoginUserBo sysUserVo = new LoginUserBo();
        sysUserVo.setSysUser(sysUser);
//        sysUserVo.setRoles(roles);
//        sysUserVo.setPermissions(permissions);
        return R.ok(sysUserVo);
    }
}
