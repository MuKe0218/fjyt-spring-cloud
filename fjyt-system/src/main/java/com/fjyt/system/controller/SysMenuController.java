package com.fjyt.system.controller;

import com.fjyt.common.domain.R;
import com.fjyt.common.utils.security.SecurityUtils;
import com.fjyt.system.pojo.DO.SysMenu;
import com.fjyt.system.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author keQiLong
 * @date 2023年04月24日 9:05
 * 菜单信息
 */
@RestController
@RequestMapping("/menu")
public class SysMenuController {

    @Autowired
    private ISysMenuService sysMenuService;

    /**
     * 获取菜单列表
     */
    @GetMapping("/list")
    public R list(SysMenu menu){
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = sysMenuService.selectMenuList(menu, userId);
        return R.ok(menus);
    }
    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("/getRouters")
    public R getRouters()
    {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = sysMenuService.selectMenuTreeByUserId(userId);
        return R.ok(sysMenuService.buildMenus(menus));
    }
}
