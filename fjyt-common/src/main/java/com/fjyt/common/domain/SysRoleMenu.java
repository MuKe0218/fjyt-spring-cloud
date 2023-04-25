package com.fjyt.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author keQiLong
 * @date 2023年04月25日 15:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRoleMenu {
    /** 角色ID */
    private Long roleId;

    /** 菜单ID */
    private Long menuId;

}
