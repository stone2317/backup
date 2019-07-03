package com.heeexy.example.domain;

import lombok.Data;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author tc
* @date 2019-06-13
*/
@Entity
@Data
@Table(name="article")
public class Article implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 文章内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
  //  @CreationTimestamp
    @UpdateTimestamp
    private Timestamp createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
//    @CreationTimestamp
    @UpdateTimestamp
    private Timestamp updateTime;

    /**
     * 是否有效  1.有效  2无效
     */
    @Column(name = "delete_status")
    private String deleteStatus;
}