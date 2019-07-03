package com.heeexy.example.service;

import com.heeexy.example.domain.SysPermission;
import com.heeexy.example.service.dto.SysPermissionDTO;
import com.heeexy.example.service.dto.SysPermissionQueryCriteria;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

/**
* @author tc
* @date 2019-06-23
*/
@CacheConfig(cacheNames = "sysPermission")
public interface SysPermissionService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    @Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(SysPermissionQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(SysPermissionQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    SysPermissionDTO findById(Integer id);

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    SysPermissionDTO create(SysPermission resources);

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(SysPermission resources);

    /**
     * delete
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Integer id);
}