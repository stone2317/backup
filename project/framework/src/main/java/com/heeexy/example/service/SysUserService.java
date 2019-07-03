package com.heeexy.example.service;

import com.heeexy.example.domain.SysUser;
import com.heeexy.example.service.dto.SysUserDTO;
import com.heeexy.example.service.dto.SysUserQueryCriteria;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

/**
* @author tc
* @date 2019-06-23
*/
@CacheConfig(cacheNames = "sysUser")
public interface SysUserService {

    /**
    * queryAll 分页
    * @param criteria
    * @param pageable
    * @return
    */
    @Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(SysUserQueryCriteria criteria, Pageable pageable);

    /**
    * queryAll 不分页
    * @param criteria
    * @return
    */
//    @Cacheable(keyGenerator = "keyGenerator")
//    public Object queryAll(SysUserQueryCriteria criteria);

    /**
     * findById
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    SysUserDTO findById(Integer id);

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    SysUserDTO create(SysUser resources);

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(SysUser resources);

    /**
     * delete
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Integer id);
}