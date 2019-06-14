package com.heeexy.example.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.heeexy.example.MyApplication;
import com.heeexy.example.domain.ColumnInfo;
import com.heeexy.example.domain.GenConfig;
import com.heeexy.example.service.GeneratorService;
import com.heeexy.example.util.GenUtil;



/**
 * @author jie
 * @date 2019-01-02
 */
@Service
public class GeneratorServiceImpl implements GeneratorService {
	

    @PersistenceContext
    private EntityManager em;
//
//    @Override
//    public Object getTables(String name, int[] startEnd) {
//        StringBuilder sql = new StringBuilder("select table_name tableName,create_time createTime from information_schema.tables where table_schema = (select database()) ");
//        if(!ObjectUtils.isEmpty(name)){
//            sql.append("and table_name like '%"+name+"%' ");
//        }
//        sql.append("order by table_name");
//        Query query = em.createNativeQuery(sql.toString());
//        query.setFirstResult(startEnd[0]);
//        query.setMaxResults(startEnd[1]-startEnd[0]);
//
//        System.out.println(sql.toString());
//        List<Object[]> result = query.getResultList();
//        List<TableInfo> tableInfos = new ArrayList<>();
//        for (Object[] obj : result) {
//            tableInfos.add(new TableInfo(obj[0],obj[1]));
//        }
//        Query query1 = em.createNativeQuery("SELECT COUNT(*) from information_schema.tables where table_schema = (select database())");
//        Object totalElements = query1.getSingleResult();
//        return PageUtil.toPage(tableInfos,totalElements);
//    }

    @Override
    public List<ColumnInfo> getColumns(String name) {
        StringBuilder sql = new StringBuilder("select column_name, is_nullable, data_type, column_comment, column_key, extra from information_schema.columns where ");
        if(!ObjectUtils.isEmpty(name)){
            sql.append("table_name = '"+name+"' ");
        }
        sql.append("and table_schema = (select database()) order by ordinal_position");
        Query query = em.createNativeQuery(sql.toString());
        List<Object[]> result = query.getResultList();
        List<ColumnInfo> columnInfos = new ArrayList<>();
        for (Object[] obj : result) {
            columnInfos.add(new ColumnInfo(obj[0],obj[1],obj[2],obj[3],obj[4],obj[5],null,"true"));
        }
        return columnInfos;
    }

    @Override
    public void generator(List<ColumnInfo> columnInfos, GenConfig genConfig, String tableName) {
//        if(genConfig.getId() == null){
//            throw new BadRequestException("请先配置生成器");
//        }
        try {
            GenUtil.generatorCode(columnInfos,genConfig,tableName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
