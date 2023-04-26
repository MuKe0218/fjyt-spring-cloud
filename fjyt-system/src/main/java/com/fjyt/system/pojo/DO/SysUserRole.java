package com.fjyt.system.pojo.DO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author keQiLong
 * @date 2023年04月26日 15:12
 * 用户和角色关联 sys_user_role
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserRole {
    /** 用户ID */
    private Long userId;

    /** 角色ID */
    private Long roleId;

}
