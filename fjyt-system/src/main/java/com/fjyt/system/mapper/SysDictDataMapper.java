package com.fjyt.system.mapper;

import com.fjyt.common.domain.SysDictData;
import com.fjyt.common.domain.SysDictType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author keQiLong
 * @date 2023年04月25日 9:52
 */
@Mapper
public interface SysDictDataMapper {
    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    public List<SysDictData> selectDictDataByType(String dictType);
}
