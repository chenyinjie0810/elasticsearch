package com.zfsoft.elasticsearch.service;

import com.zfsoft.elasticsearch.pojo.ElecDirClaimData;
import org.springframework.data.domain.Page;

/**
 * @Author chenyj
 * @Description
 * @Date create by 2020/3/3 16:45
 * 陈银杰专属测试
 */
public interface IElecDirClaimDataService {

    /**
     * @desc: 根据Oid查询
     * @author: chenyj
     * @date: 2020/3/3
     * @param oid
     * @return
     * @throws Exception
     */
    ElecDirClaimData getElecDirClaimDataByOid(String oid) throws Exception;
    /**
     * @desc: 添加、修改
     * @author: chenyj
     * @date: 2020/3/3
     * @param dirClaimData
     * @throws Exception
     */
    void saveElecDirClaimData(ElecDirClaimData dirClaimData) throws Exception;

    /**
     * @desc: 删除
     * @author: chenyj
     * @date: 2020/3/3
     * @param oid
     * @throws Exception
     */
    void deleteElecDirClaimDataByOid(String oid) throws Exception;

    /**
     * @desc: 检索
     * @author: chenyj
     * @date: 2020/3/3
     * @return
     * @throws Exception
     */
    Page<ElecDirClaimData> query(ElecDirClaimData elecDirClaimData, int pageNumber, int pageSize) throws Exception;
}
