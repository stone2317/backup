package com.heeexy.example.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.heeexy.example.domain.Article;
import com.heeexy.example.repository.ArticleRepository;
import com.heeexy.example.service.ArticleService;
import com.heeexy.example.service.dto.ArticleDTO;
import com.heeexy.example.service.dto.ArticleQueryCriteria;
import com.heeexy.example.service.mapper.ArticleMapper;
import com.heeexy.example.util.PageUtil;
import com.heeexy.example.util.QueryHelp;
import com.heeexy.example.util.ValidationUtil;

/**
* @author tc
* @date 2019-06-13
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public Object queryAll(ArticleQueryCriteria criteria, Pageable pageable){
        Page<Article> page = articleRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(articleMapper::toDto));
    }

    @Override
    public Object queryAll(ArticleQueryCriteria criteria){
        return articleMapper.toDto(articleRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public ArticleDTO findById(Integer id) {
        Optional<Article> article = articleRepository.findById(id);
        ValidationUtil.isNull(article,"Article","id",id);
        return articleMapper.toDto(article.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ArticleDTO create(Article resources) {
        return articleMapper.toDto(articleRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Article resources) {
        Optional<Article> optionalArticle = articleRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalArticle,"Article","id",resources.getId());

        Article article = optionalArticle.get();
        // 此处需自己修改
        resources.setId(article.getId());
        articleRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        articleRepository.deleteById(id);
    }
}