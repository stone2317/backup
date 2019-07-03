package com.heeexy.example.service;

import com.heeexy.example.domain.SysRole;
import com.heeexy.example.service.dto.SysRoleDTO;
import com.heeexy.example.service.dto.SysRoleQueryCriteria;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

/**
* @author tc
* @date 2019-06-23
*/
@CacheConfig(cacheNames = "sysRole")
public interface SysRoleService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    @Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(SysRoleQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(SysRoleQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    SysRoleDTO findById(Integer id);

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    SysRoleDTO create(SysRole resources);

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(SysRole resources);

    /**
     * delete
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Integer id);
}