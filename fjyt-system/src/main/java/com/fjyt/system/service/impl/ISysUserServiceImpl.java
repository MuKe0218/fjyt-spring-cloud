package com.fjyt.system.service.impl;

import com.fjyt.system.mapper.SysUserMapper;
import com.fjyt.common.domain.SysUser;
import com.fjyt.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author keQiLong
 * @date 2023年04月23日 14:32
 * 用户 业务层处理
 */
@Service
public class ISysUserServiceImpl implements ISysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Override
    public SysUser selectUserByUserName(String userName) {
        return sysUserMapper.selectUserByUserName(userName);
    }
    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserById(Long userId) {
        return sysUserMapper.selectUserById(userId);
    }
}
