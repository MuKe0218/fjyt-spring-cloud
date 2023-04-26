package com.fjyt.system.service;

import com.fjyt.common.domain.SysUser;

import java.util.Set;

/**
 * @author keQiLong
 * @date 2023年04月25日 17:38
 */
public interface ISysPermissionService {
    /**
     * 获取角色数据权限
     *
     * @param  user
     * @return 角色权限信息
     */
    public Set<String> getRolePermission(SysUser user);

    /**
     * 获取菜单数据权限
     *
     * @param  user
     * @return 菜单权限信息
     */
    public Set<String> getMenuPermission(SysUser user);
}
