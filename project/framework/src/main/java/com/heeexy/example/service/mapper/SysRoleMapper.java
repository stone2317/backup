package com.heeexy.example.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import com.heeexy.example.domain.SysRole;
import com.heeexy.example.service.dto.SysRoleDTO;

/**
* @author tc
* @date 2019-06-23
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysRoleMapper extends EntityMapper<SysRoleDTO, SysRole> {
	@Mappings({
		@Mapping(source="id",target="roleId")
	})
	public SysRoleDTO toDto(SysRole role);
}