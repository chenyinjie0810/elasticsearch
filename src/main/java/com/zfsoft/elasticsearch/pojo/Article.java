package com.zfsoft.elasticsearch.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @Author chenyj
 * @Description
 * @Date create by 2019/11/9 0:02
 * 陈银杰专属测试
 */
@Document(indexName = "chenyj_article01", type = "article")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article implements Serializable {

    @Id
    private String id;

    @Field(index = true, type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String title;

     @Field(index = true, type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String content;

    private String state;
}
