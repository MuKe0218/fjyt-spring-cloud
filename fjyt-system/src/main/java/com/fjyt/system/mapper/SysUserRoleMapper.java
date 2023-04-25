package com.fjyt.system.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author keQiLong
 * @date 2023年04月25日 16:05
 * 用户与角色关联表 数据层
 */
@Mapper
public interface SysUserRoleMapper {
    /**
     * 通过角色ID查询角色使用数量
     *
     * @param roleId 角色ID
     * @return 结果
     */
    public int countUserRoleByRoleId(Long roleId);
}
