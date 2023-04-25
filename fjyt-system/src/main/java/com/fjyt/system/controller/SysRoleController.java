package com.fjyt.system.controller;

import com.fjyt.common.domain.R;
import com.fjyt.common.domain.SysRole;
import com.fjyt.common.domain.TableDataInfo;
import com.fjyt.common.utils.security.SecurityUtils;
import com.fjyt.system.service.ISysRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author keQiLong
 * @date 2023年04月25日 14:39
 * 角色Controller
 */
@RestController
@RequestMapping("/role")
public class SysRoleController {

    @Autowired
    private ISysRoleService roleService;
    @GetMapping("/list")
    public TableDataInfo list(SysRole role,int pageNum,int pageSize)
    {
        PageHelper.startPage(pageNum,pageSize);
        List<SysRole> list = roleService.selectRoleList(role);
        TableDataInfo rspData = new TableDataInfo();
        rspData.setRows(list);
        rspData.setMsg("查询成功");
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }
    /**
     * 根据角色编号获取详细信息
     */
    @GetMapping(value = "/{roleId}")
    public R getInfo(@PathVariable Long roleId)
    {
        roleService.checkRoleDataScope(roleId);
        return R.ok(roleService.selectRoleById(roleId));
    }
    /**
     * 新增角色
     */
    @PostMapping
    public R add(@Validated @RequestBody SysRole role)
    {
        if (!roleService.checkRoleNameUnique(role))
        {
            return R.fail("新增角色'" + role.getRoleName() + "'失败，角色名称已存在");
        }
        else if (!roleService.checkRoleKeyUnique(role))
        {
            return R.fail("新增角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        role.setCreateBy(SecurityUtils.getUsername());
        return R.ok(roleService.insertRole(role));

    }

    /**
     * 修改保存角色
     */
    @PutMapping
    public R edit(@Validated @RequestBody SysRole role)
    {
        roleService.checkRoleAllowed(role);
        roleService.checkRoleDataScope(role.getRoleId());
        if (!roleService.checkRoleNameUnique(role))
        {
            return R.fail("修改角色'" + role.getRoleName() + "'失败，角色名称已存在");
        }
        else if (!roleService.checkRoleKeyUnique(role))
        {
            return R.fail("修改角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        role.setUpdateBy(SecurityUtils.getUsername());
        return R.ok(roleService.updateRole(role));
    }

    /**
     * 状态修改
     */
    @PutMapping("/changeStatus")
    public R changeStatus(@RequestBody SysRole role)
    {
        roleService.checkRoleAllowed(role);
        roleService.checkRoleDataScope(role.getRoleId());
        role.setUpdateBy(SecurityUtils.getUsername());
        return R.ok(roleService.updateRoleStatus(role));
    }

    /**
     * 删除角色
     */
    @DeleteMapping("/{roleIds}")
    public R remove(@PathVariable Long[] roleIds)
    {
        return R.ok(roleService.deleteRoleByIds(roleIds));
    }
}
