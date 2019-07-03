package com.heeexy.example.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import com.heeexy.example.domain.SysPermission;
import com.heeexy.example.service.dto.SysPermissionDTO;

/**
* @author tc
* @date 2019-06-23
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysPermissionMapper extends EntityMapper<SysPermissionDTO, SysPermission> {
	
	@Mappings({
		@Mapping(source="requiredPermission",target="requiredPerm")
	})
	public SysPermissionDTO toDto(SysPermission p) ;
}