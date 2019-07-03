package com.heeexy.example.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;


/**
* @author tc
* @date 2019-06-23
*/
@Data
public class SysUserDTO implements Serializable {

    private Integer userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 角色ID
     */
    private Integer roleId;

    private String roleName;
    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 修改时间
     */
    private Timestamp updateTime;

    /**
     * 是否有效  1有效  2无效
     */
    private String deleteStatus;
}