package com.heeexy.example.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.heeexy.example.repository.SysPermissionRepository;
import com.heeexy.example.service.SysPermissionService;
import com.heeexy.example.service.dto.SysPermissionQueryCriteria;
import com.heeexy.example.util.CommonUtil;

import lombok.val;


/**
* @author tc
* @date 2019-06-23
*/
@RestController
@RequestMapping("user")
public class SysPermissionController {

    @Autowired
    private SysPermissionService sysPermissionService;
    
    @Autowired
    private SysPermissionRepository sysPermissionRepository;
    
	@RequiresPermissions("role:list")
    @GetMapping(value = "/listAllPermission")
    public JSONObject getSysPermissions(SysPermissionQueryCriteria criteria, Pageable pageable){
		List<SysPermission> perms = sysPermissionRepository.findAll();
		List<JSONObject> ret = new ArrayList<>();

		perms.forEach(p -> {
			JSONObject perm = new JSONObject();
			perm.put("id", p.getId());
			perm.put("permissionName", p.getPermissionName());
			perm.put("requiredPerm", p.getRequiredPermission());

			Optional<JSONObject> menu = ret.stream().filter(v ->{
				if (v.get("menuName").equals(p.getMenuName())) {
					v.getJSONArray("permissions").add(perm);
					return true;
				}else
					return false;
			}).findFirst();
			if (!menu.isPresent()) {
				JSONObject newMenu = new JSONObject();
				newMenu.put("menuName", p.getMenuName());
				JSONArray permissions = new JSONArray();
				permissions.add(perm);
				newMenu.put("permissions", permissions);
				ret.add(newMenu);
			}
		});
		return CommonUtil.successPage(ret);
	}
	
//	boolean contains(List<Object> list,)

//    @PostMapping(value = "/sysPermission")
//    public ResponseEntity create(@Validated @RequestBody SysPermission resources){
//        return new ResponseEntity(sysPermissionService.create(resources),HttpStatus.CREATED);
//    }
//
//    @PutMapping(value = "/sysPermission")
//    public ResponseEntity update(@Validated @RequestBody SysPermission resources){
//        sysPermissionService.update(resources);
//        return new ResponseEntity(HttpStatus.NO_CONTENT);
//    }
//
//    @DeleteMapping(value = "/sysPermission/{id}")
//    public ResponseEntity delete(@PathVariable Integer id){
//        sysPermissionService.delete(id);
//        return new ResponseEntity(HttpStatus.OK);
//    }
}