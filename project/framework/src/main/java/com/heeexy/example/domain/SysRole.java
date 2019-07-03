package com.heeexy.example.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* @author tc
* @date 2019-06-23
*/
@Entity
@EqualsAndHashCode(exclude={"permissions","users"})
@Data
@Table(name="sys_role")
public class SysRole implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 角色名
     */
    @Column(name = "role_name")
    private String roleName;

    @Column(name = "create_time")
    @CreationTimestamp
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Timestamp createTime;

    @Column(name = "update_time")
    @CreationTimestamp
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
   private Timestamp updateTime;

    /**
     * 是否有效  1有效  2无效
     */
    @Column(name = "delete_status")
    private String deleteStatus;
    
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "sys_role_permission", joinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "permission_id",referencedColumnName = "id")})
    private Set<SysPermission> permissions;
    
    @OneToMany(mappedBy="role",cascade = CascadeType.REMOVE)
    private Set<SysUser> users;

}