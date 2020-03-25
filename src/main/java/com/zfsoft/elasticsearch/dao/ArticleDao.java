package com.zfsoft.elasticsearch.dao;

import com.zfsoft.elasticsearch.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @Author chenyj
 * @Description
 * @Date create by 2019/11/9 0:07
 * 陈银杰专属测试
 */
public interface ArticleDao  extends ElasticsearchRepository<Article, String> {
    
    Page<Article> findByTitleOrContentLike(String title, String content, Pageable pageable);

    List<Article> findByContentLike(String content);
}
