package com.heeexy.example.rest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.heeexy.example.domain.Article;
import com.heeexy.example.domain.GenConfig;
import com.heeexy.example.service.ArticleService;
import com.heeexy.example.service.GeneratorService;
import com.heeexy.example.service.dto.ArticleQueryCriteria;


/**
* @author tc
* @date 2019-06-13
*/
@RestController
@RequestMapping("article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
	@Autowired
	private GeneratorService generatorService;
	
    @RequiresPermissions("article:list")
    @GetMapping(value = "/listArticle")
    public ResponseEntity getArticles(ArticleQueryCriteria criteria, Pageable pageable){
//		GenConfig config = new GenConfig(1L,"com.heeexy.example","","c:\\front","c:\\front\\api","tc","",true);
//		generatorService.generator(generatorService.getColumns("sys_permission"), config, "sys_permission");
//		generatorService.generator(generatorService.getColumns("sys_role"), config, "sys_role");
//		generatorService.generator(generatorService.getColumns("sys_role_permission"), config, "sys_role_permission");
//		generatorService.generator(generatorService.getColumns("sys_user"), config, "sys_user");

        return new ResponseEntity(articleService.queryAll(criteria,pageable),HttpStatus.OK);
    }

	@RequiresPermissions("article:add")
    @PostMapping(value = "/article")
    public ResponseEntity create(@Validated @RequestBody Article resources){
        return new ResponseEntity(articleService.create(resources),HttpStatus.CREATED);
    }

	@RequiresPermissions("article:update")
    @PutMapping(value = "/article")
    public ResponseEntity update(@Validated @RequestBody Article resources){
        articleService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/article/{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        articleService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}