package com.heeexy.example.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;


/**
* @author tc
* @date 2019-06-23
*/
@Data
public class SysRolePermissionDTO implements Serializable {

    private Integer id;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 权限id
     */
    private Integer permissionId;

    private Timestamp createTime;

    private Timestamp updateTime;

    /**
     * 是否有效 1有效     2无效
     */
    private String deleteStatus;
}