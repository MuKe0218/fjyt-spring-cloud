package com.fjyt.system.controller;

import com.fjyt.common.domain.R;
import com.fjyt.common.text.Convert;
import com.fjyt.common.utils.JwtUtils;
import com.fjyt.common.utils.StringUtils;
import com.fjyt.common.domain.LoginUser;
import com.fjyt.common.domain.SysUser;
import com.fjyt.common.utils.security.SecurityUtils;
import com.fjyt.system.service.ISysPermissionService;
import com.fjyt.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

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
    @Autowired
    private ISysPermissionService permissionService;
    /**
     * 获取当前用户信息
     */
    @GetMapping("/info/{username}")
    public R<LoginUser> info(@PathVariable("username") String username)
    {
        SysUser sysUser = userService.selectUserByUserName(username);
        if (StringUtils.isNull(sysUser))
        {
            return R.fail("用户名或密码错误");
        }
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(sysUser);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(sysUser);
        LoginUser sysUserVo = new LoginUser();
        sysUserVo.setSysUser(sysUser);
        sysUserVo.setRoles(roles);
        sysUserVo.setPermissions(permissions);
        return R.ok(sysUserVo);
    }
    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("/getInfo")
    public Map<String,Object> getInfo(HttpServletRequest request)
    {
        String token = SecurityUtils.getToken(request);
        SysUser user = userService.selectUserById(Convert.toLong(JwtUtils.getUserId(token)));
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        Map<String,Object> ajax = new Hashtable<>();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }
}
