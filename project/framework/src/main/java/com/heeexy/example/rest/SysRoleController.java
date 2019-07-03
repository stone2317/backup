package com.heeexy.example.rest;

import java.awt.image.VolatileImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.domain.SysPermission;
import com.heeexy.example.domain.SysRole;
import com.heeexy.example.domain.SysRolePermission;
import com.heeexy.example.domain.SysUser;
import com.heeexy.example.repository.SysRolePermissionRepository;
import com.heeexy.example.repository.SysRoleRepository;
import com.heeexy.example.service.SysRoleService;
import com.heeexy.example.service.dto.SysRoleDTO;
import com.heeexy.example.service.dto.SysRoleQueryCriteria;
import com.heeexy.example.util.CommonUtil;


/**
* @author tc
* @date 2019-06-23
*/
@RestController
@RequestMapping("user")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;
    
    @Autowired
    private SysRoleRepository sysRoleRepository;
    @Autowired SysRolePermissionRepository sysRolePermissionRepository;

	@RequiresPermissions(value = {"user:add", "user:update"}, logical = Logical.OR)
	@GetMapping("/getAllRoles")
    public ResponseEntity getSysRoles(SysRoleQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(sysRoleService.queryAll(criteria,pageable),HttpStatus.OK);
    }

//    @PostMapping(value = "/sysRole")
//    public ResponseEntity create(@Validated @RequestBody SysRole resources){
//        return new ResponseEntity(sysRoleService.create(resources),HttpStatus.CREATED);
//    }

    @PutMapping(value = "/sysRole")
    public ResponseEntity update(@Validated @RequestBody SysRole resources){
        sysRoleService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/sysRole/{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        sysRoleService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
    
	/**
	 * 角色列表
	 */
	@RequiresPermissions("role:list")
	@GetMapping("/listRole")
	public JSONObject listRole() {
		List<SysRole> roles = sysRoleRepository.findAll();
		List<JSONObject> ret = new ArrayList<>();
		for (SysRole r : roles) {
			JSONObject item = new JSONObject();
			item.put("roleId", r.getId());
			item.put("roleName", r.getRoleName());
			item.put("users",r.getUsers());
			JSONArray menus = new JSONArray();
			Map<String, JSONObject> menuCode2permission = new HashMap<>();
			for (SysPermission p: r.getPermissions()) {
				if (!menuCode2permission.containsKey(p.getMenuCode())) {
					JSONObject permission = new JSONObject();
					permission.put("menuCode", p.getMenuCode());
					permission.put("menuName", p.getMenuName());
					JSONArray permissionIds = new JSONArray();
					permission.put("permissions", permissionIds);
					menus.add(permission);
					menuCode2permission.put(p.getMenuCode(), permission);
				}
				JSONArray permissionIds = menuCode2permission.get(p.getMenuCode()).getJSONArray("permissions");
				JSONObject permissionId = new JSONObject();
				permissionId.put("permissionName", p.getPermissionName());
				permissionId.put("permissionId", p.getId());
				permissionIds.add(permissionId);
			}
			item.put("menus", menus);
			ret.add(item);
		}
		return CommonUtil.successPage(ret);
	}
	
	/**
	 * 新增角色
	 */
	@RequiresPermissions("role:add")
	@PostMapping("/addRole")
	public JSONObject addRole(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "roleName,permissions");
		SysRole role = new SysRole();
		role.setRoleName(requestJson.get("roleName").toString());
		SysRoleDTO RoleDTO = sysRoleService.create(role);
		
		List<Integer> ps = (List<Integer>) requestJson.get("permissions");
		for (Integer pId : ps) {
			SysRolePermission rolePerm = new SysRolePermission();
			rolePerm.setRoleId(RoleDTO.getRoleId());
			rolePerm.setPermissionId(pId);
			sysRolePermissionRepository.save(rolePerm);
		}
		return CommonUtil.successJson();
	}
	/**
	 * 修改角色
	 */
	@RequiresPermissions("role:update")
	@PostMapping("/updateRole")
	public JSONObject updateRole(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "roleId,roleName,permissions");
		Integer roleId = requestJson.getInteger("roleId");
		List<Integer> newPerms = (List<Integer>) requestJson.get("permissions");
		List<SysRolePermission> sysRolePermissions = sysRolePermissionRepository.findByRoleId(roleId);
		List<Integer> oldPerms= new ArrayList<>();
		sysRolePermissions.forEach((v) -> {
			oldPerms.add(v.getPermissionId());
		});
		newPerms.forEach(v -> {
			if (!oldPerms.contains(v)) {
				SysRolePermission add = new SysRolePermission();
				add.setRoleId(roleId);
				add.setPermissionId(v);
				add.setDeleteStatus("1");
				sysRolePermissionRepository.save(add);
			}
		});
		oldPerms.forEach(v -> {
			if (!newPerms.contains(v)) {
				sysRolePermissionRepository.deleteByRoleidAndPermissionid(roleId, v);
			}
		});
		
		String roleName = requestJson.getString("roleName");
		SysRole role = sysRoleRepository.findById(roleId).get();
		role.setRoleName(roleName);
		sysRoleRepository.save(role);
		
		return new JSONObject();
	}
	
	@RequiresPermissions("role:delete")
	@PostMapping("/deleteRole")
	public JSONObject deleteRole(@RequestBody JSONObject requestJson) {
		CommonUtil.hasAllRequired(requestJson, "roleId");
		sysRoleRepository.deleteById(requestJson.getInteger("roleId"));
		return new JSONObject();
	}
}