package com.heeexy.example.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* @author tc
* @date 2019-06-23
*/
@Entity
@EqualsAndHashCode(exclude={"role"})
@Data
@Table(name="sys_user")
public class SysUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 用户名
     */
    @Column(name = "username")
    private String username;

    /**
     * 密码
     */
    @Column(name = "password")
    private String password;

    /**
     * 昵称
     */
    @Column(name = "nickname")
    private String nickname;

    /**
     * 角色ID
     */
    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "role_id")
    SysRole role;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @UpdateTimestamp
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Timestamp createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    @UpdateTimestamp
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Timestamp updateTime;

    /**
     * 是否有效  1有效  2无效
     */
    @Column(name = "delete_status")
    private String deleteStatus;
}