package com.zfsoft.elasticsearch.service.impl;

import com.zfsoft.elasticsearch.dao.ArticleDao;
import com.zfsoft.elasticsearch.pojo.Article;
import com.zfsoft.elasticsearch.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author chenyj
 * @Description
 * @Date create by 2019/11/9 0:09
 * 陈银杰专属测试
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;


    @Override
    public void save(Article article) {
        articleDao.save(article);
    }

    @Override
    public Page<Article> findByTitleOrContentLike(String keywords, int pageNumber, int pageSize) {
        Pageable pageable=PageRequest.of(pageNumber-1, pageSize);
        return articleDao.findByTitleOrContentLike(keywords, keywords ,pageable);
    }

    @Override
    public List<Article> findByContent(String content) {
        return  articleDao.findByContentLike(content);
    }


}
