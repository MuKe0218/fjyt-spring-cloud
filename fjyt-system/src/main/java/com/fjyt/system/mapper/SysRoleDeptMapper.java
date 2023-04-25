package com.fjyt.system.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author keQiLong
 * @date 2023年04月25日 16:09
 * 角色与部门关联表
 */
@Mapper
public interface SysRoleDeptMapper {
    /**
     * 批量删除角色部门关联信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRoleDept(Long[] ids);
}
