package com.fjyt.system.controller;

import com.fjyt.common.domain.R;
import com.fjyt.common.utils.StringUtils;
import com.fjyt.common.domain.SysDictData;
import com.fjyt.system.service.ISysDictDataService;
import com.fjyt.system.service.ISysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author keQiLong
 * @date 2023年04月25日 9:51
 * 数据字典信息
 */
@RestController
@RequestMapping("/dict/data")
public class SysDictDataController {

    @Autowired
    private ISysDictDataService iSysDictDataService;
    @Autowired
    private ISysDictTypeService dictTypeService;

    /**
     * 根据字典类型查询字典数据信息
     */
    @GetMapping(value = "/type/{dictType}")
    public R dictType(@PathVariable String dictType)
    {
        List<SysDictData> data = dictTypeService.selectDictDataByType(dictType);
        if (StringUtils.isNull(data))
        {
            data = new ArrayList<SysDictData>();
        }
        return R.ok(data);
    }
}
