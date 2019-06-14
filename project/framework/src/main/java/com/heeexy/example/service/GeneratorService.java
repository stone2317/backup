package com.heeexy.example.service;

import java.util.List;

import com.heeexy.example.domain.ColumnInfo;
import com.heeexy.example.domain.GenConfig;

/**
 * @author jie
 * @date 2019-01-02
 */
public interface GeneratorService {

    /**
     * 查询数据库元数据
     * @param name
     * @param startEnd
     * @return
     */
//    Object getTables(String name, int[] startEnd);

    /**
     * 得到数据表的元数据
     * @param name
     * @return
     */
	List<ColumnInfo> getColumns(String name);

    /**
     * 生成代码
     * @param columnInfos
     * @param genConfig
     * @param tableName
     */
    void generator(List<ColumnInfo> columnInfos, GenConfig genConfig, String tableName);
}
