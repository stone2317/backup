package com.heeexy.example.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.heeexy.example.domain.SysRolePermission;
import com.heeexy.example.service.dto.SysRolePermissionDTO;

/**
* @author tc
* @date 2019-06-23
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysRolePermissionMapper extends EntityMapper<SysRolePermissionDTO, SysRolePermission> {

}