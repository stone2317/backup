package com.heeexy.example.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;


/**
* @author tc
* @date 2019-06-23
*/
@Data
public class SysRoleDTO implements Serializable {

    private Integer roleId;

    /**
     * 角色名
     */
    private String roleName;

    private Timestamp createTime;

    private Timestamp updateTime;

    /**
     * 是否有效  1有效  2无效
     */
    private String deleteStatus;
}