package com.zfsoft.elasticsearch.service;

import com.zfsoft.elasticsearch.pojo.Article;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Author chenyj
 * @Description
 * @Date create by 2019/11/9 0:08
 * 陈银杰专属测试
 */
public interface ArticleService {

    void save(Article article);

    Page<Article> findByTitleOrContentLike(String keywords, int pageNumber, int pageSize);


    List<Article> findByContent(String content);
}
