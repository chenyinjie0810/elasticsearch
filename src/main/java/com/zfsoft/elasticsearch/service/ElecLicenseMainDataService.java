package com.zfsoft.elasticsearch.service;

import com.github.pagehelper.PageInfo;
import com.zfsoft.elasticsearch.pojo.ElecLicenseMainData;
import com.zfsoft.elasticsearch.vo.ElecLicenseMainQueryVo;
import com.zfsoft.elasticsearch.vo.EsElecLicneseQueryVo;
import org.springframework.data.domain.Page;

import java.net.UnknownHostException;

/**
 * @author chenjian
 * 电子证照信息录入
 */
public interface ElecLicenseMainDataService {

    /**
     * 电子证照的表单数据入库
     * @param elecLicenseMainData
     * @return
     */
    String insert(ElecLicenseMainData elecLicenseMainData);

    /**
     * 电子证照登记的表单数据对象
     * @return
     * @param elecLicenseMainQueryVo
     */
    PageInfo queryElecLicenseMainDataList(ElecLicenseMainQueryVo elecLicenseMainQueryVo) throws UnknownHostException;

    /**
     * 查询证照审核列表
     * @author yuy
     * @date 2019-10-15
     * @return
     * @param elecLicenseMainQueryVo
     */
    PageInfo queryElecLicenseMainDataAuditList(ElecLicenseMainQueryVo elecLicenseMainQueryVo);

    /**
     * 证照维护列表查询
     * @author yuy
     * @date 2019-10-12
     * @return
     * @param elecLicenseMainQueryVo
     */
    PageInfo queryElecLicenseMainDataWhList(ElecLicenseMainQueryVo elecLicenseMainQueryVo);

    /**
     * 批文维护列表查询
     * @author yuy
     * @date 2019-10-14
     * @return
     * @param elecLicenseMainQueryVo
     */
    PageInfo queryElecLicenseMainDataPwWhList(ElecLicenseMainQueryVo elecLicenseMainQueryVo);

    /**
     * 根据主键查看电子证照信息
     * @author yuy
     * @date 2019-9-29
     * @param elecLicenseOid
     * @throws Exception
     */
    ElecLicenseMainData viewElecLicenseMainDataByOid(String elecLicenseOid);

    /**
     * 删除信息
     * @author yuy
     * @date 2019-9-29
     * @param oid
     * @throws Exception
     */
    String deleteElecLicenseMainDataByOid(String oid);

    /**
     * 根据证照主键查看信息
     * @author yuy
     * @date 2019-9-30
     * @return
     * @param elecOid
     */
    ElecLicenseMainData getElecLicenseMainDataByElecOid(String elecOid);

    /**
     * 根据主键查看电子证照信息
     * @author yuy
     * @date 2019-9-29
     * @param oid
     * @throws Exception
     */
    ElecLicenseMainData getElecLicenseMainDataByOid(String oid);

    /**
     * 查询待审核数量
     * @author yuy
     * @date 2019-10-15
     * @return
     * @param elecLicenseMainQueryVo
     * @param auditType
     */
    long queryElecLicenseMainDataAuditNum(ElecLicenseMainQueryVo elecLicenseMainQueryVo, String auditType);

    /**
     * 删除信息
     * @author yuy
     * @date 2019-9-29
     * @param indexName
     * @throws Exception
     */
    String deleteElecLicenseMainDataIndex(String indexName);

    /**
     * @desc: 证照审核查询
     * @author: chenyj
     * @date: 2020/3/5
     * @param elecLicenseMainData
     * @param auditObj
     * @param pageNumber
     * @param pageSize
     * @return Page
     * @throws Exception
     */
    Page<ElecLicenseMainData> queryAllAuditElecLicenseMainLice(ElecLicenseMainData elecLicenseMainData, String auditObj, int pageNumber, int pageSize) throws Exception;

    PageInfo queryElecLicenseQueryList(EsElecLicneseQueryVo elecLicenseMainQueryVo);

}
