package com.heeexy.example.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.heeexy.example.domain.Article;
import com.heeexy.example.service.dto.ArticleDTO;

/**
* @author tc
* @date 2019-06-13
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ArticleMapper extends EntityMapper<ArticleDTO, Article> {

}