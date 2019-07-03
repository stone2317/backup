package com.heeexy.example.service.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import com.heeexy.example.domain.SysUser;
import com.heeexy.example.service.dto.SysUserDTO;

/**
* @author tc
* @date 2019-06-23
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysUserMapper  {

	@Mappings({
		@Mapping(source = "role.id", target = "roleId"), 
		@Mapping(source = "role.roleName", target = "roleName"),
		@Mapping(source = "id",target = "userId")
	})
	public SysUserDTO UsertoDto(SysUser entity);
	@InheritInverseConfiguration(name="UsertoDto")
	public SysUser DtotoUser(SysUserDTO dto) ;
}