package com.heeexy.example.repository;

import com.heeexy.example.domain.SysPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author tc
* @date 2019-06-23
*/
public interface SysPermissionRepository extends JpaRepository<SysPermission, Integer>, JpaSpecificationExecutor {
}