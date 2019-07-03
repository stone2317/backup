package com.heeexy.example.service.dto;

import lombok.Data;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;


/**
* @author tc
* @date 2019-06-13
*/
@Data
public class ArticleDTO implements Serializable {

    private Integer id;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp createTime;

    /**
     * 更新时间
     */
    private Timestamp updateTime;

    /**
     * 是否有效  1.有效  2无效
     */
    private String deleteStatus;
}