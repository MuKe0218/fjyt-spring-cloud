package com.fjyt.system.service;

import com.fjyt.common.domain.SysDictData;

import java.util.List;

/**
 * @author keQiLong
 * @date 2023年04月25日 9:48
 * 字典 业务层
 */
public interface ISysDictTypeService {
    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    public List<SysDictData> selectDictDataByType(String dictType);
}
