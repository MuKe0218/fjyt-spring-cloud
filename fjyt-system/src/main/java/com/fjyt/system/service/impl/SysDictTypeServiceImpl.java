package com.fjyt.system.service.impl;

import com.fjyt.common.utils.StringUtils;
import com.fjyt.common.domain.SysDictData;
import com.fjyt.system.mapper.SysDictDataMapper;
import com.fjyt.system.service.ISysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author keQiLong
 * @date 2023年04月25日 9:49
 * 字典 业务层处理
 */
@Service
public class SysDictTypeServiceImpl implements ISysDictTypeService {

    @Autowired
    private SysDictDataMapper dictDataMapper;

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    @Override
    public List<SysDictData> selectDictDataByType(String dictType) {
        List<SysDictData> dictDatas = dictDataMapper.selectDictDataByType(dictType);
        if (StringUtils.isNotEmpty(dictDatas))
        {
            return dictDatas;
        }
        return null;
    }
}
