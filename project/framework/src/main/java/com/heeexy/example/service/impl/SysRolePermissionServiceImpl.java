package com.heeexy.example.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.heeexy.example.domain.SysRolePermission;
import com.heeexy.example.repository.SysRolePermissionRepository;
import com.heeexy.example.service.SysRolePermissionService;
import com.heeexy.example.service.dto.SysRolePermissionDTO;
import com.heeexy.example.service.dto.SysRolePermissionQueryCriteria;
import com.heeexy.example.service.mapper.SysRolePermissionMapper;
import com.heeexy.example.util.PageUtil;
import com.heeexy.example.util.QueryHelp;
import com.heeexy.example.util.ValidationUtil;

/**
* @author tc
* @date 2019-06-23
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SysRolePermissionServiceImpl implements SysRolePermissionService {

    @Autowired
    private SysRolePermissionRepository sysRolePermissionRepository;

    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Override
    public Object queryAll(SysRolePermissionQueryCriteria criteria, Pageable pageable){
        Page<SysRolePermission> page = sysRolePermissionRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(sysRolePermissionMapper::toDto));
    }

    @Override
    public Object queryAll(SysRolePermissionQueryCriteria criteria){
        return sysRolePermissionMapper.toDto(sysRolePermissionRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public SysRolePermissionDTO findById(Integer id) {
        Optional<SysRolePermission> sysRolePermission = sysRolePermissionRepository.findById(id);
        ValidationUtil.isNull(sysRolePermission,"SysRolePermission","id",id);
        return sysRolePermissionMapper.toDto(sysRolePermission.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysRolePermissionDTO create(SysRolePermission resources) {
        return sysRolePermissionMapper.toDto(sysRolePermissionRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysRolePermission resources) {
        Optional<SysRolePermission> optionalSysRolePermission = sysRolePermissionRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalSysRolePermission,"SysRolePermission","id",resources.getId());

        SysRolePermission sysRolePermission = optionalSysRolePermission.get();
        // 此处需自己修改
        resources.setId(sysRolePermission.getId());
        sysRolePermissionRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        sysRolePermissionRepository.deleteById(id);
    }
}