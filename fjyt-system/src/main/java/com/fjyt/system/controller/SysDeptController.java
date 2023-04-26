package com.fjyt.system.controller;

import com.fjyt.common.constant.UserConstants;
import com.fjyt.common.domain.R;
import com.fjyt.common.domain.SysDept;
import com.fjyt.common.utils.StringUtils;
import com.fjyt.common.utils.security.SecurityUtils;
import com.fjyt.system.service.ISysDeptService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author keQiLong
 * @date 2023年04月26日 10:46
 * 部门信息
 */
@RestController
@RequestMapping("/dept")
public class SysDeptController {

    @Autowired
    private ISysDeptService deptService;

    /**
     * 获取部门列表
     */
    @GetMapping("/list")
    public R list(SysDept dept)
    {
        List<SysDept> depts = deptService.selectDeptList(dept);
        return R.ok(depts);
    }
    /**
     * 查询部门列表（排除节点）
     */
    @GetMapping("/list/exclude/{deptId}")
    public R excludeChild(@PathVariable(value = "deptId", required = false) Long deptId)
    {
        List<SysDept> depts = deptService.selectDeptList(new SysDept());
        depts.removeIf(d -> d.getDeptId().intValue() == deptId || ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), deptId + ""));
        return R.ok(depts);
    }

    /**
     * 根据部门编号获取详细信息
     */
    @GetMapping(value = "/{deptId}")
    public R getInfo(@PathVariable Long deptId)
    {
        deptService.checkDeptDataScope(deptId);
        return R.ok(deptService.selectDeptById(deptId));
    }

    /**
     * 新增部门
     */
    @PostMapping
    public R add(@Validated @RequestBody SysDept dept)
    {
        if (!deptService.checkDeptNameUnique(dept))
        {
            return R.fail("新增部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        }
        dept.setCreateBy(SecurityUtils.getUsername());
        return R.ok(deptService.insertDept(dept));
    }

    /**
     * 修改部门
     */
    @PutMapping
    public R edit(@Validated @RequestBody SysDept dept)
    {
        Long deptId = dept.getDeptId();
        deptService.checkDeptDataScope(deptId);
        if (!deptService.checkDeptNameUnique(dept))
        {
            return R.fail("修改部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        }
        else if (dept.getParentId().equals(deptId))
        {
            return R.fail("修改部门'" + dept.getDeptName() + "'失败，上级部门不能是自己");
        }
        else if (StringUtils.equals(UserConstants.DEPT_DISABLE, dept.getStatus()) && deptService.selectNormalChildrenDeptById(deptId) > 0)
        {
            return R.fail("该部门包含未停用的子部门！");
        }
        dept.setUpdateBy(SecurityUtils.getUsername());
        return R.ok(deptService.updateDept(dept));
    }

    /**
     * 删除部门
     */
    @DeleteMapping("/{deptId}")
    public R remove(@PathVariable Long deptId)
    {
        if (deptService.hasChildByDeptId(deptId))
        {
            return R.fail("存在下级部门,不允许删除");
        }
        if (deptService.checkDeptExistUser(deptId))
        {
            return R.fail("部门存在用户,不允许删除");
        }
        deptService.checkDeptDataScope(deptId);
        return R.ok(deptService.deleteDeptById(deptId));
    }
}
