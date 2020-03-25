package com.zfsoft.elasticsearch.service;

import com.github.pagehelper.PageInfo;
import com.zfsoft.elasticsearch.pojo.FwPublicRegistrationData;
import com.zfsoft.elasticsearch.vo.FwPublicRegistrationQueryVo;

/**
 * @author chenjian
 * 窗口登记的表单数据入库服务
 */
public interface FwPublicRegistrationDataService {

    /**
     * 窗口登记的表单数据入库
     * @param fwPublicRegistrationData
     * @return
     */
    String insert(FwPublicRegistrationData fwPublicRegistrationData);

    /**
     * 查询窗口登记的表单数据对象
     * @return
     * @param fwPublicRegistrationQueryVo
     */
    PageInfo queryFwPublicRegistrationDataList(FwPublicRegistrationQueryVo fwPublicRegistrationQueryVo);

}
