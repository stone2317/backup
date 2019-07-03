package com.heeexy.example.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.heeexy.example.domain.SysRole;
import com.heeexy.example.repository.SysRoleRepository;
import com.heeexy.example.service.SysRoleService;
import com.heeexy.example.service.dto.SysRoleDTO;
import com.heeexy.example.service.dto.SysRoleQueryCriteria;
import com.heeexy.example.service.mapper.SysRoleMapper;
import com.heeexy.example.util.PageUtil;
import com.heeexy.example.util.QueryHelp;
import com.heeexy.example.util.ValidationUtil;

/**
* @author tc
* @date 2019-06-23
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleRepository sysRoleRepository;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public Object queryAll(SysRoleQueryCriteria criteria, Pageable pageable){
        Page<SysRole> page = sysRoleRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(sysRoleMapper::toDto));
    }

    @Override
    public Object queryAll(SysRoleQueryCriteria criteria){
        return sysRoleMapper.toDto(sysRoleRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public SysRoleDTO findById(Integer id) {
        Optional<SysRole> sysRole = sysRoleRepository.findById(id);
        ValidationUtil.isNull(sysRole,"SysRole","id",id);
        return sysRoleMapper.toDto(sysRole.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysRoleDTO create(SysRole resources) {
        return sysRoleMapper.toDto(sysRoleRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysRole resources) {
        Optional<SysRole> optionalSysRole = sysRoleRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalSysRole,"SysRole","id",resources.getId());

        SysRole sysRole = optionalSysRole.get();
        // 此处需自己修改
        resources.setId(sysRole.getId());
        sysRoleRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        sysRoleRepository.deleteById(id);
    }
}