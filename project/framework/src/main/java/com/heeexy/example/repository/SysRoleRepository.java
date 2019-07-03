package com.heeexy.example.repository;

import com.heeexy.example.domain.SysRole;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author tc
* @date 2019-06-23
*/
public interface SysRoleRepository extends JpaRepository<SysRole, Integer>, JpaSpecificationExecutor {
}