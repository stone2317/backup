package com.heeexy.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.heeexy.example.domain.SysRolePermission;

/**
* @author tc
* @date 2019-06-23
*/
public interface SysRolePermissionRepository extends JpaRepository<SysRolePermission, Integer>, JpaSpecificationExecutor {
	List<SysRolePermission> findByRoleId(Integer id);

	@Modifying
	@Transactional
	@Query("delete from SysRolePermission es where es.roleId = ?1 and es.permissionId = ?2")
	int deleteByRoleidAndPermissionid(Integer roleId,Integer permissionId);
}