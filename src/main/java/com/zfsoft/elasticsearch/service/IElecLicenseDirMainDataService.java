package com.zfsoft.elasticsearch.service;

import com.zfsoft.elasticsearch.pojo.ElecLicenseDirMainData;
import org.springframework.data.domain.Page;

/**
 * @Author chenyj
 * @Description
 * @Date create by 2020/3/2 16:13
 * 陈银杰专属测试
 */
public interface IElecLicenseDirMainDataService {
    /**
     * @desc: 保存/修改方法
     * @author: chenyj
     * @date: 2020/3/2
     * @param elecLicenseDirMainData
     */
    void save(String oid, ElecLicenseDirMainData elecLicenseDirMainData) throws Exception;

    /**
     * @desc: 根据目录名称相似搜索
     * @author: chenyj
     * @date: 2020/3/2
     * @param directoryName
     * @param pageNumber
     * @param pageSize
     * @return
     */
    Page<ElecLicenseDirMainData> findByDirectoryNameLike(String directoryName, int pageNumber, int pageSize);

    /**
     * @desc: 根据oid查询
     * @author: chenyj
     * @date: 2020/3/3
     * @param oid
     * @return
     * @throws Exception
     */
    ElecLicenseDirMainData getElecLicenseDirMainByOid(String oid) throws Exception;
}
