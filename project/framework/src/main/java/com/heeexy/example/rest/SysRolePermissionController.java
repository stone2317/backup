package com.heeexy.example.rest;

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

import com.heeexy.example.domain.SysRolePermission;
import com.heeexy.example.service.SysRolePermissionService;
import com.heeexy.example.service.dto.SysRolePermissionQueryCriteria;


/**
* @author tc
* @date 2019-06-23
*/
@RestController
@RequestMapping("api")
public class SysRolePermissionController {

    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    @GetMapping(value = "/sysRolePermission")
    public ResponseEntity getSysRolePermissions(SysRolePermissionQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(sysRolePermissionService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping(value = "/sysRolePermission")
    public ResponseEntity create(@Validated @RequestBody SysRolePermission resources){
        return new ResponseEntity(sysRolePermissionService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping(value = "/sysRolePermission")
    public ResponseEntity update(@Validated @RequestBody SysRolePermission resources){
        sysRolePermissionService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/sysRolePermission/{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        sysRolePermissionService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}