package com.zfsoft.elasticsearch.controller;

import com.zfsoft.elasticsearch.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * @Author chenyj
 * @Description
 * @Date create by 2020/3/9 11:23
 * 陈银杰专属测试
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/elasticsearchIk/")
public class ElasticsearchIkController {
    Logger logger= LoggerFactory.getLogger(getClass());

    /**
     * @desc: 读取远程自定义分词
     * @author: chenyj
     * @date: 2020/3/9
     * @param response
     */
    @GetMapping("ik")
    public void get(HttpServletResponse response) {
        logger.info("elasticsearch读取远程分词配置");

        try {
            // 读取字典文件
            String path = this.getClass().getClassLoader().getResource("word.txt").getPath();
            String content= FileUtil.getContentByFile(new File(path));
            // 返回数据
            response.setHeader("Last-Modified", String.valueOf(System.currentTimeMillis()));
            response.setHeader("ETag", String.valueOf(System.currentTimeMillis()));
            response.setContentType("text/plain; charset=utf-8");
            response.getWriter().write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



