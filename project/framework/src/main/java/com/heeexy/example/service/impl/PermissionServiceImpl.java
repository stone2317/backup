package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.PermissionDao;
import com.heeexy.example.domain.SysPermission;
import com.heeexy.example.domain.SysRole;
import com.heeexy.example.domain.SysUser;
import com.heeexy.example.repository.SysPermissionRepository;
import com.heeexy.example.repository.SysRoleRepository;
import com.heeexy.example.repository.SysUserRepository;
import com.heeexy.example.service.PermissionService;
import com.heeexy.example.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: hxy
 * @date: 2017/10/30 13:15
 */
@Service
public class PermissionServiceImpl implements PermissionService {

//	@Autowired
//	private PermissionDao permissionDao;
    @Autowired
    private SysUserRepository sysUserRepository;
    @Autowired
    private SysPermissionRepository	 sysPermissionRepository;
	/**
	 * 查询某用户的 角色  菜单列表   权限列表
	 */
	@Override
	public JSONObject getUserPermission(String username) {
		JSONObject userPermission = getUserPermissionFromDB(username);
		return userPermission;
	}

	/**
	 * 从数据库查询用户权限信息
	 */
	private JSONObject getUserPermissionFromDB(String username) {
		SysUser user = sysUserRepository.findByUsername(username);
		JSONObject userPermission = new JSONObject();
		userPermission.put("nickname", user.getNickname());
		userPermission.put("userId", user.getId());
		userPermission.put("roleName", user.getRole().getRoleName());
		userPermission.put("roleId", user.getRole().getId());
		Set<String> menuList = new HashSet<>();
		for (SysPermission p: user.getRole().getPermissions()) {
			menuList.add(p.getMenuCode());
		}
		Set<String> permissionList = new HashSet<>();
		for (SysPermission p: user.getRole().getPermissions()) {
			permissionList.add(p.getPermissionCode());
		}

		userPermission.put("menuList", menuList);
		userPermission.put("permissionList", permissionList);
		//管理员角色ID为1
		int adminRoleId = 1;
		//如果是管理员
		String roleIdKey = "roleId";
		if (adminRoleId == userPermission.getIntValue(roleIdKey)) {
			//查询所有菜单  所有权限
			List<SysPermission> permissions = sysPermissionRepository.findAll();
			for (SysPermission p: permissions) {
				menuList.add(p.getMenuCode());
			}
			for (SysPermission p: permissions) {
				permissionList.add(p.getPermissionCode());
			}

			userPermission.put("menuList", menuList);
			userPermission.put("permissionList", permissionList);
		}
		return userPermission;
		
		
//		JSONObject userPermission = permissionDao.getUserPermission(username);
//		//管理员角色ID为1
//		int adminRoleId = 1;
//		//如果是管理员
//		String roleIdKey = "roleId";
//		if (adminRoleId == userPermission.getIntValue(roleIdKey)) {
//			//查询所有菜单  所有权限
//			Set<String> menuListAll = permissionDao.getAllMenu();
//			Set<String> permissionListAll = permissionDao.getAllPermission();
//			userPermission.put("menuList", menuListAll);
//			userPermission.put("permissionList", permissionListAll);
//		}
//		return userPermission;
	}
}
