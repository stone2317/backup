package com.heeexy.example.repository;

import com.heeexy.example.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author tc
* @date 2019-06-13
*/
public interface ArticleRepository extends JpaRepository<Article, Integer>, JpaSpecificationExecutor {
}