package com.heeexy.example.service;

import com.heeexy.example.domain.SysRolePermission;
import com.heeexy.example.service.dto.SysRolePermissionDTO;
import com.heeexy.example.service.dto.SysRolePermissionQueryCriteria;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

/**
* @author tc
* @date 2019-06-23
*/
@CacheConfig(cacheNames = "sysRolePermission")
public interface SysRolePermissionService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    @Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(SysRolePermissionQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(SysRolePermissionQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    SysRolePermissionDTO findById(Integer id);

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    SysRolePermissionDTO create(SysRolePermission resources);

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(SysRolePermission resources);

    /**
     * delete
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Integer id);
}