package com.heeexy.example.rest;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.heeexy.example.domain.SysUser;
import com.heeexy.example.service.SysUserService;
import com.heeexy.example.service.dto.SysUserDTO;
import com.heeexy.example.service.dto.SysUserQueryCriteria;
import com.heeexy.example.service.mapper.SysUserMapper;


/**
* @author tc
* @date 2019-06-23
*/
@RestController
@RequestMapping("user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserMapper sysUserMapper;

	@RequiresPermissions("user:list")
    @GetMapping(value = "/list")
    public ResponseEntity getSysUsers(SysUserQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(sysUserService.queryAll(criteria,pageable),HttpStatus.OK);
    }

	@RequiresPermissions("user:add")
    @PostMapping(value = "/addUser")
    public ResponseEntity create(@Validated @RequestBody SysUserDTO resources){
		SysUser user = sysUserMapper.DtotoUser(resources);
        return new ResponseEntity(sysUserService.create(user),HttpStatus.CREATED);
    }

	@RequiresPermissions("user:update")
	@PostMapping("/updateUser")
    public ResponseEntity update(@Validated @RequestBody SysUserDTO resources){
		SysUser user = sysUserMapper.DtotoUser(resources);
        sysUserService.update(user);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/deleteUser/{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        sysUserService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
    
}