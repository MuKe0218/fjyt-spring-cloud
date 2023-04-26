package com.fjyt.system.mapper;

import com.fjyt.common.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author keQiLong
 * @date 2023年04月23日 14:37
 * 用户表 数据层
 */
@Mapper
public interface SysUserMapper {
    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    public SysUser selectUserByUserName(String userName);
    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    public SysUser selectUserById(Long userId);
}
