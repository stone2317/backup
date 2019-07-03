package com.heeexy.example.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.heeexy.example.domain.SysUser;
import com.heeexy.example.repository.SysUserRepository;
import com.heeexy.example.service.SysUserService;
import com.heeexy.example.service.dto.SysUserDTO;
import com.heeexy.example.service.dto.SysUserQueryCriteria;
import com.heeexy.example.service.mapper.SysUserMapper;
import com.heeexy.example.util.PageUtil;
import com.heeexy.example.util.QueryHelp;
import com.heeexy.example.util.ValidationUtil;

/**
* @author tc
* @date 2019-06-23
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public Object queryAll(SysUserQueryCriteria criteria, Pageable pageable){
        Page<SysUser> page = sysUserRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(sysUserMapper::UsertoDto));
    }

//    @Override
//    public Object queryAll(SysUserQueryCriteria criteria){
//        return sysUserMapper.toDto(sysUserRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
//    }

    @Override
    public SysUserDTO findById(Integer id) {
        Optional<SysUser> sysUser = sysUserRepository.findById(id);
        ValidationUtil.isNull(sysUser,"SysUser","id",id);
        return sysUserMapper.UsertoDto(sysUser.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUserDTO create(SysUser resources) {
        return sysUserMapper.UsertoDto(sysUserRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysUser resources) {
        Optional<SysUser> optionalSysUser = sysUserRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalSysUser,"SysUser","id",resources.getId());

        SysUser sysUser = optionalSysUser.get();
        // 此处需自己修改
        resources.setId(sysUser.getId());
        if (resources.getPassword() != null && !resources.getPassword().isEmpty()) {
        	sysUserRepository.save(resources);
		}else{
			resources.setPassword(sysUser.getPassword());
        	sysUserRepository.save(resources);
		}
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        sysUserRepository.deleteById(id);
    }
}