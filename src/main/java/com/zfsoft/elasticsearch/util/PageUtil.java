package com.zfsoft.elasticsearch.util;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * PageHelper 分页对象 辅助类
 *
 * @Author chenjian
 */
public class PageUtil {

    public static PageInfo  change(Integer pageNum,Integer pageSize, Integer total, List list) {
        Page page = new Page(pageNum,pageSize);
        if (total != null) {
            page.setTotal(total);
        }
        PageInfo pageInfo = new PageInfo(page);

        if (list != null && list.size() != 0) {
            pageInfo.setList(list);
        }
        return pageInfo;
    }

}
