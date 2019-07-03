package com.heeexy.example.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
* @author tc
* @date 2019-06-23
*/
@Entity
@Data
@Table(name="sys_role_permission")
public class SysRolePermission implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 角色id
     */
    @Column(name = "role_id")
    private Integer roleId;

    /**
     * 权限id
     */
    @Column(name = "permission_id")
    private Integer permissionId;

    @Column(name = "create_time")
    @UpdateTimestamp
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Timestamp createTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Timestamp updateTime;

    /**
     * 是否有效 1有效     2无效
     */
    @Column(name = "delete_status")
    private String deleteStatus;
}