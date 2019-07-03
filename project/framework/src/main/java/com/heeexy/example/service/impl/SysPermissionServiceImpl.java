package com.heeexy.example.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.heeexy.example.domain.SysPermission;
import com.heeexy.example.repository.SysPermissionRepository;
import com.heeexy.example.service.SysPermissionService;
import com.heeexy.example.service.dto.SysPermissionDTO;
import com.heeexy.example.service.dto.SysPermissionQueryCriteria;
import com.heeexy.example.service.mapper.SysPermissionMapper;
import com.heeexy.example.util.PageUtil;
import com.heeexy.example.util.QueryHelp;
import com.heeexy.example.util.ValidationUtil;

/**
* @author tc
* @date 2019-06-23
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SysPermissionServiceImpl implements SysPermissionService {

    @Autowired
    private SysPermissionRepository sysPermissionRepository;

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Override
    public Object queryAll(SysPermissionQueryCriteria criteria, Pageable pageable){
        Page<SysPermission> page = sysPermissionRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(sysPermissionMapper::toDto));
    }

    @Override
    public Object queryAll(SysPermissionQueryCriteria criteria){
        return sysPermissionMapper.toDto(sysPermissionRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public SysPermissionDTO findById(Integer id) {
        Optional<SysPermission> sysPermission = sysPermissionRepository.findById(id);
        ValidationUtil.isNull(sysPermission,"SysPermission","id",id);
        return sysPermissionMapper.toDto(sysPermission.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysPermissionDTO create(SysPermission resources) {
        return sysPermissionMapper.toDto(sysPermissionRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysPermission resources) {
        Optional<SysPermission> optionalSysPermission = sysPermissionRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalSysPermission,"SysPermission","id",resources.getId());

        SysPermission sysPermission = optionalSysPermission.get();
        // 此处需自己修改
        resources.setId(sysPermission.getId());
        sysPermissionRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        sysPermissionRepository.deleteById(id);
    }
}