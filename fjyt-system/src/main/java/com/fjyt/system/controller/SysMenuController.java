package com.fjyt.system.controller;

import com.fjyt.common.constant.UserConstants;
import com.fjyt.common.domain.R;
import com.fjyt.common.utils.StringUtils;
import com.fjyt.common.utils.security.SecurityUtils;
import com.fjyt.system.pojo.DO.SysMenu;
import com.fjyt.system.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

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
     * 根据菜单编号获取详细信息
     */
    @GetMapping(value = "/{menuId}")
    public R getInfo(@PathVariable Long menuId)
    {
        return R.ok(sysMenuService.selectMenuById(menuId));
    }
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
     * 加载对应角色菜单列表树
     */
    @GetMapping(value = "/roleMenuTreeselect/{roleId}")
    public Map<String,Object> roleMenuTreeselect(@PathVariable("roleId") Long roleId)
    {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = sysMenuService.selectMenuList(userId);
        Map<String,Object> map = new Hashtable<String,Object>();
        map.put("checkedKeys", sysMenuService.selectMenuListByRoleId(roleId));
        map.put("menus", sysMenuService.buildMenuTreeSelect(menus));
        return map;
    }
    /**
     * 新增菜单
     */
    @PostMapping
    public R add(@Validated @RequestBody SysMenu menu)
    {
        if (!sysMenuService.checkMenuNameUnique(menu))
        {
            return R.fail("新增菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        }
        else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !StringUtils.ishttp(menu.getPath()))
        {
            return R.fail("新增菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
        }
        menu.setCreateBy(SecurityUtils.getUsername());
        return R.ok(sysMenuService.insertMenu(menu));
    }
    /**
     * 修改菜单
     */
    @PutMapping
    public R edit(@Validated @RequestBody SysMenu menu)
    {
        if (!sysMenuService.checkMenuNameUnique(menu))
        {
            return R.fail("修改菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        }
        else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !StringUtils.ishttp(menu.getPath()))
        {
            return R.fail("修改菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
        }
        else if (menu.getMenuId().equals(menu.getParentId()))
        {
            return R.fail("修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
        }
        menu.setUpdateBy(SecurityUtils.getUsername());
        return R.ok(sysMenuService.updateMenu(menu));
    }
    /**
     * 删除菜单
     */
    @DeleteMapping("/{menuId}")
    public R remove(@PathVariable("menuId") Long menuId)
    {
        if (sysMenuService.hasChildByMenuId(menuId))
        {
            return R.fail("存在子菜单,不允许删除");
        }
//        if (sysMenuService.checkMenuExistRole(menuId))
//        {
//            return R.fail("菜单已分配,不允许删除");
//        }
        return R.ok(sysMenuService.deleteMenuById(menuId));
    }
    /**
     * 获取菜单下拉树列表
     */
    @GetMapping("/treeselect")
    public R treeselect(SysMenu menu)
    {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = sysMenuService.selectMenuList(menu, userId);
        return R.ok(sysMenuService.buildMenuTreeSelect(menus));
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
