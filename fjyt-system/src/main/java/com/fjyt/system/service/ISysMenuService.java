package com.fjyt.system.service;

import com.fjyt.system.pojo.DO.SysMenu;
import com.fjyt.system.pojo.VO.RouterVo;

import java.util.List;

/**
 * @author keQiLong
 * @date 2023年04月24日 9:06
 * 菜单 业务层
 */
public interface ISysMenuService {
    /**
     * 根据用户ID查询菜单树信息
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    public List<SysMenu> selectMenuTreeByUserId(Long userId);
    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    public List<RouterVo> buildMenus(List<SysMenu> menus);
    /**
     * 根据用户查询系统菜单列表
     *
     * @param menu 菜单信息
     * @param userId 用户ID
     * @return 菜单列表
     */
    public List<SysMenu> selectMenuList(SysMenu menu, Long userId);
}
