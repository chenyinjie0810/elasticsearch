package com.zfsoft.elasticsearch.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Author chenyj
 * @Description
 * @Date create by 2019/10/22 22:57
 * 陈银杰专属测试
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {
    /**
     * 总记录数
     */
    private long tatal;
    /**
     * 当前页
     */
    private Integer pageNumber;
    /**
     * 每页记录数
     */
    private Integer pageSize;
    private List<T> rows;

    public PageResult(long tatal, List rows){
        this.pageNumber= Math.toIntExact(tatal);
        this.rows=rows;
    }
    public PageResult(Page page){
       if (null!=page){
           this.pageNumber=page.getNumberOfElements();
           this.tatal=page.getTotalElements();
           this.rows=page.getContent();
       }
    }
}
