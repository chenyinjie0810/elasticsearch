package com.zfsoft.elasticsearch.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import com.zfsoft.elasticsearch.constans.KafkaConstans;
import com.zfsoft.elasticsearch.entity.PageResult;
import com.zfsoft.elasticsearch.entity.Result;
import com.zfsoft.elasticsearch.enums.StatusCodeEnum;
import com.zfsoft.elasticsearch.pojo.ElecLicenseDirMainData;
import com.zfsoft.elasticsearch.pojo.ElecLicenseMainData;
import com.zfsoft.elasticsearch.service.ElecLicenseMainDataService;
import com.zfsoft.elasticsearch.thread.InsertEsDataThread;
import com.zfsoft.elasticsearch.util.RSAEncrypt;
import com.zfsoft.elasticsearch.util.SnowFlake;
import com.zfsoft.elasticsearch.vo.ElecLicenseMainQueryVo;
import com.zfsoft.elasticsearch.vo.EsElecLicneseQueryVo;
import com.zfsoft.elasticsearch.vo.EsVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * @author chenjian
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/elasticsearch/")
public class EsController {

    Logger logger= LoggerFactory.getLogger(getClass());

    @Autowired
    private ElecLicenseMainDataService elecLicenseMainDataService;

    @Autowired
    private KafkaConstans kafkaConstans;

    @PostMapping(value = "search/{pageNumber}/{pageSize}")
    public Result getName(@PathVariable("pageNumber")int pageNumber,
                          @PathVariable("pageSize")int pageSize,
                          @RequestBody ElecLicenseDirMainData map,
                          HttpServletRequest request)  {
        System.out.println(pageNumber);
        System.out.println(pageSize);
        System.out.println(map);
        return new Result(StatusCodeEnum.SUCCESS);
    }

    /**
     * 保存窗口登记流程的表单数据
     * @param query
     * @return
     */
    @ApiOperation(value = "将表单数据保存到es中", notes = "需要传入json对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "请求数据", required = true, dataType = "String", paramType = "data")
    })
    @RequestMapping(value = "addElecLicenseMainData", method = RequestMethod.POST)
    public String addElecLicenseMainData(EsVo query) {
        String data = query.getData();
        if(data != null) {
            RSAEncrypt.setKeyMap(kafkaConstans.YXPT_PUBLIC_KEY_STRING, kafkaConstans.YXPT_PRIVATE_KEY_STRING);
            try {
                // 更新证照主表的状态为待审核
                ElecLicenseMainData elecLicenseMainData = JSONObject.parseObject(RSAEncrypt.decrypt(data, kafkaConstans.YXPT_PRIVATE_KEY_STRING), ElecLicenseMainData.class);

                if(StringUtil.isEmpty(elecLicenseMainData.getOid())) {
                    SnowFlake snowFlake = new SnowFlake(kafkaConstans.DATACENTER_ID, kafkaConstans.MACHINE_ID);
                    elecLicenseMainData.setOid(String.valueOf(snowFlake.nextId()));
                }
                return elecLicenseMainDataService.insert(elecLicenseMainData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 查询电子证照对象
     * @return
     */
    @ApiOperation(value = "查询电子证照对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "请求数据", required = true, dataType = "String", paramType = "data")
    })
    @RequestMapping(value = "queryElecLicenseMainDataList", method = RequestMethod.POST)
    public PageInfo queryElecLicenseMainDataList(EsVo query){
        String data = query.getData();

        if(data != null) {
            RSAEncrypt.setKeyMap(kafkaConstans.YXPT_PUBLIC_KEY_STRING, kafkaConstans.YXPT_PRIVATE_KEY_STRING);
            try {
                ElecLicenseMainQueryVo elecLicenseMainQueryVo = JSONObject.parseObject(RSAEncrypt.decrypt(data, kafkaConstans.YXPT_PRIVATE_KEY_STRING), ElecLicenseMainQueryVo.class);
                return elecLicenseMainDataService.queryElecLicenseMainDataList(elecLicenseMainQueryVo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 查询证照审核列表
     * @return
     */
    @ApiOperation(value = "查询证照审核列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "请求数据", required = true, dataType = "String", paramType = "data")
    })
    @RequestMapping(value = "queryElecLicenseMainDataAuditList", method = RequestMethod.POST)
    public PageInfo queryElecLicenseMainDataAuditList(EsVo query){
        String data = query.getData();
        if(data != null) {
            RSAEncrypt.setKeyMap(kafkaConstans.YXPT_PUBLIC_KEY_STRING, kafkaConstans.YXPT_PRIVATE_KEY_STRING);
            try {
                ElecLicenseMainQueryVo elecLicenseMainQueryVo = JSONObject.parseObject(RSAEncrypt.decrypt(data, kafkaConstans.YXPT_PRIVATE_KEY_STRING), ElecLicenseMainQueryVo.class);
                return elecLicenseMainDataService.queryElecLicenseMainDataAuditList(elecLicenseMainQueryVo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * @desc: 查询证照审核列表
     * @author: chenyj
     * @date: 2020/3/5
     * @param pageNumber
     * @param pageSize
     * @param elecLicenseMainData
     * @return
     */
    @ApiOperation(value = "查询证照审核列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "请求数据", required = true, dataType = "String", paramType = "data")
    })
    @PostMapping("queryAllAuditElecLicenseMainLice/{auditObj}/{pageNumber}/{pageSize}")
    public Result queryAllAuditElecLicenseMainLice(@PathVariable String auditObj,
                                                   @PathVariable int pageNumber,
                                                   @PathVariable int pageSize,
                                                   @RequestBody ElecLicenseMainData elecLicenseMainData){
        try {
            logger.info("入参：auditObj="+ auditObj+"  elecLicenseMainData="+elecLicenseMainData);
            Page<ElecLicenseMainData> page= elecLicenseMainDataService.queryAllAuditElecLicenseMainLice(elecLicenseMainData ,auditObj, pageNumber, pageSize);
            return new Result(StatusCodeEnum.SUCCESS,new PageResult<>(page));
        }catch (Exception e){
            e.printStackTrace();
        }
        return  new Result(StatusCodeEnum.FAIL);
    }

    /**
     * 证照维护列表查询
     * @return
     */
    @ApiOperation(value = "证照维护列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "请求数据", required = true, dataType = "String", paramType = "data")
    })
    @RequestMapping(value = "queryElecLicenseMainDataWhList", method = RequestMethod.POST)
    public PageInfo queryElecLicenseMainDataWhList(EsVo query){
        String data = query.getData();
        if(data != null) {
            RSAEncrypt.setKeyMap(kafkaConstans.YXPT_PUBLIC_KEY_STRING, kafkaConstans.YXPT_PRIVATE_KEY_STRING);
            try {
                ElecLicenseMainQueryVo elecLicenseMainQueryVo = JSONObject.parseObject(RSAEncrypt.decrypt(data, kafkaConstans.YXPT_PRIVATE_KEY_STRING), ElecLicenseMainQueryVo.class);
                return elecLicenseMainDataService.queryElecLicenseMainDataWhList(elecLicenseMainQueryVo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 证照查询列表查询
     * @return
     */
    @ApiOperation(value = "证照查询列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "请求数据", required = true, dataType = "String", paramType = "data")
    })
    @RequestMapping(value = "queryElecLicenseQueryList", method = RequestMethod.POST)
    public PageInfo queryElecLicenseQueryList(EsVo query){

        String data = query.getData();
        if(data != null) {
            RSAEncrypt.setKeyMap(kafkaConstans.YXPT_PUBLIC_KEY_STRING, kafkaConstans.YXPT_PRIVATE_KEY_STRING);
            try {
                EsElecLicneseQueryVo esElecLicneseQueryVo = JSONObject.parseObject(RSAEncrypt.decrypt(data, kafkaConstans.YXPT_PRIVATE_KEY_STRING), EsElecLicneseQueryVo.class);
                return elecLicenseMainDataService.queryElecLicenseQueryList(esElecLicneseQueryVo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 批文维护列表查询
     * @return
     */
    @ApiOperation(value = "批文维护列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "请求数据", required = true, dataType = "String", paramType = "data")
    })
    @RequestMapping(value = "queryElecLicenseMainDataPwWhList", method = RequestMethod.POST)
    public PageInfo queryElecLicenseMainDataPwWhList(EsVo query){
        String data = query.getData();
        if(data != null) {
            RSAEncrypt.setKeyMap(kafkaConstans.YXPT_PUBLIC_KEY_STRING, kafkaConstans.YXPT_PRIVATE_KEY_STRING);
            try {
                ElecLicenseMainQueryVo elecLicenseMainQueryVo = JSONObject.parseObject(RSAEncrypt.decrypt(data, kafkaConstans.YXPT_PRIVATE_KEY_STRING), ElecLicenseMainQueryVo.class);
                return elecLicenseMainDataService.queryElecLicenseMainDataPwWhList(elecLicenseMainQueryVo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 删除证照信息
     * @author yuy
     * @date 2019-9-29
     * @param query
     * @return
     */
    @ApiOperation(value = "删除es中数据", notes = "需要传入json对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "请求数据", required = true, dataType = "String", paramType = "data")
    })
    @RequestMapping(value = "deleteElecLicenseMainData", method = RequestMethod.POST)
    public String deleteElecLicenseMainData(EsVo query) {
        String data = query.getData();
        if(data != null) {
            RSAEncrypt.setKeyMap(kafkaConstans.YXPT_PUBLIC_KEY_STRING, kafkaConstans.YXPT_PRIVATE_KEY_STRING);
            try {
                ElecLicenseMainData elecLicenseMainData = JSONObject.parseObject(RSAEncrypt.decrypt(data, kafkaConstans.YXPT_PRIVATE_KEY_STRING), ElecLicenseMainData.class);
                if(!StringUtil.isEmpty(elecLicenseMainData.getOid())) {
                    String[] oids = elecLicenseMainData.getOid().split(",");
                    for (String oid : oids) {
                        if (!StringUtil.isEmpty(oid)) {
                            elecLicenseMainDataService.deleteElecLicenseMainDataByOid(oid);
                        }
                    }
                }

                return "1";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 查看证照信息
     * @author yuy
     * @date 2019-9-29
     * @param query
     * @return
     */
    @ApiOperation(value = "查看es中数据", notes = "需要传入json对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "请求数据", required = true, dataType = "String", paramType = "data")
    })
    @RequestMapping(value = "viewElecLicenseMainData", method = RequestMethod.POST)
    public ElecLicenseMainData viewElecLicenseMainData(EsVo query) {
        String data = query.getData();
        if(data != null) {
            RSAEncrypt.setKeyMap(kafkaConstans.YXPT_PUBLIC_KEY_STRING, kafkaConstans.YXPT_PRIVATE_KEY_STRING);
            try {
                String  elecLicenseOid = RSAEncrypt.decrypt(data, kafkaConstans.YXPT_PRIVATE_KEY_STRING);
                if (!StringUtil.isEmpty(elecLicenseOid)){
                    return elecLicenseMainDataService.viewElecLicenseMainDataByOid(elecLicenseOid);
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 批量审核
     * @author yuy
     * @date 2019-9-30
     * @param query
     * @return
     */
    @ApiOperation(value = "批量审核", notes = "需要传入json对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "请求数据", required = true, dataType = "String", paramType = "data")
    })
    @RequestMapping(value = "batchAuditElecLicenseMainData", method = RequestMethod.POST)
    public String batchAuditElecLicenseMainData(EsVo query) {
        String data = query.getData();
        if(data != null) {
            RSAEncrypt.setKeyMap(kafkaConstans.YXPT_PUBLIC_KEY_STRING, kafkaConstans.YXPT_PRIVATE_KEY_STRING);
            try {
                String successElecOids = "";
                ElecLicenseMainData elecLicenseMainData = JSONObject.parseObject(RSAEncrypt.decrypt(data, kafkaConstans.YXPT_PRIVATE_KEY_STRING), ElecLicenseMainData.class);
                if (!StringUtil.isEmpty(elecLicenseMainData.getElecLicenOid())) {
                    String[] elecOids = elecLicenseMainData.getElecLicenOid().split(",");
                    for (String elecOid : elecOids) {
                        if (!StringUtil.isEmpty(elecOid)) {
                            ElecLicenseMainData info = elecLicenseMainDataService.getElecLicenseMainDataByElecOid(elecOid);
                            if (info != null) {
                                if (!StringUtil.isEmpty(elecLicenseMainData.getLicenseStatus())) {// 证照新增审核
                                    info.setLicenseStatus(elecLicenseMainData.getLicenseStatus());
                                } else if (!StringUtil.isEmpty(elecLicenseMainData.getZxAuditFlag())) {// 证照注销审核
                                    info.setZxAuditFlag(elecLicenseMainData.getZxAuditFlag());
                                    info.setZxCancelFlag("1");
                                } else if (!StringUtil.isEmpty(elecLicenseMainData.getDxAuditFlag())) {// 证照吊销审核
                                    info.setDxAuditFlag(elecLicenseMainData.getDxAuditFlag());
                                    info.setDxCancelFlag("1");
                                } else if (!StringUtil.isEmpty(elecLicenseMainData.getYxAuditFlag())) {// 证照延续审核
                                    info.setYxAuditFlag(elecLicenseMainData.getYxAuditFlag());
                                    info.setYxDelFlag("N");
                                } else if (!StringUtil.isEmpty(elecLicenseMainData.getJcAuditFlag())) {// 证照纠错审核
                                    info.setJcAuditFlag(elecLicenseMainData.getJcAuditFlag());
                                    info.setJcDelFlag("N");
                                } else if (!StringUtil.isEmpty(elecLicenseMainData.getNjAuditFlag())) {// 证照年检审核
                                    info.setNjAuditFlag(elecLicenseMainData.getNjAuditFlag());
                                    info.setNjDelFlag("N");
                                } else if (!StringUtil.isEmpty(elecLicenseMainData.getBgAuditFlag())) {// 证照变更审核
                                    info.setBgAuditFlag(elecLicenseMainData.getBgAuditFlag());
                                    info.setLicenseStatus(elecLicenseMainData.getBgAuditFlag());
                                }
                                String oid = elecLicenseMainDataService.insert(info);
                                if (!StringUtil.isEmpty(oid)) {
                                    successElecOids += info.getElecLicenOid() + ",";
                                }
                            }
                        }
                    }
                    if (!StringUtil.isEmpty(successElecOids)) {
                        return successElecOids.substring(0, successElecOids.length() - 1);
                    }
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 证照维护批量提交
     * @author yuy
     * @date 2019-10-13
     * @param query
     * @return
     */
    @ApiOperation(value = "证照维护批量提交", notes = "需要传入json对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "请求数据", required = true, dataType = "String", paramType = "data")
    })
    @RequestMapping(value = "batchSubmitWhElecLicenseMainData", method = RequestMethod.POST)
    public String batchSubmitWhElecLicenseMainData(EsVo query) {
        String data = query.getData();
        if(data != null) {
            RSAEncrypt.setKeyMap(kafkaConstans.YXPT_PUBLIC_KEY_STRING, kafkaConstans.YXPT_PRIVATE_KEY_STRING);
            try {
                String successElecOids = "";
                ElecLicenseMainData elecLicenseMainData = JSONObject.parseObject(RSAEncrypt.decrypt(data, kafkaConstans.YXPT_PRIVATE_KEY_STRING), ElecLicenseMainData.class);
                if (!StringUtil.isEmpty(elecLicenseMainData.getElecLicenOid())) {
                    String[] elecOids = elecLicenseMainData.getElecLicenOid().split(",");
                    for (String elecOid : elecOids) {
                        if (!StringUtil.isEmpty(elecOid)) {
                            ElecLicenseMainData info = elecLicenseMainDataService.getElecLicenseMainDataByElecOid(elecOid);
                            if (info != null) {
                                if (!StringUtil.isEmpty(elecLicenseMainData.getZxAuditFlag())) {// 证照注销
                                    info.setZxAuditFlag(elecLicenseMainData.getZxAuditFlag());
                                    info.setZxCancelFlag(elecLicenseMainData.getZxCancelFlag());
                                } else if (!StringUtil.isEmpty(elecLicenseMainData.getDxAuditFlag())) {// 证照吊销
                                    info.setDxAuditFlag(elecLicenseMainData.getDxAuditFlag());
                                    info.setDxCancelFlag(elecLicenseMainData.getDxCancelFlag());
                                } else if (!StringUtil.isEmpty(elecLicenseMainData.getYxAuditFlag())) {// 证照延续
                                    info.setYxAuditFlag(elecLicenseMainData.getYxAuditFlag());
                                    info.setYxDelFlag(elecLicenseMainData.getYxDelFlag());
                                } else if (!StringUtil.isEmpty(elecLicenseMainData.getJcAuditFlag())) {// 证照纠错
                                    info.setJcAuditFlag(elecLicenseMainData.getJcAuditFlag());
                                    info.setJcDelFlag(elecLicenseMainData.getJcDelFlag());
                                } else if (!StringUtil.isEmpty(elecLicenseMainData.getBgAuditFlag())) {// 证照变更
                                    info.setBgAuditFlag(elecLicenseMainData.getBgAuditFlag());
                                    info.setBgNextAuditFlag(elecLicenseMainData.getBgAuditFlag());
                                    if (StringUtil.isNotEmpty(info.getBgNextElecOid())) {
                                        ElecLicenseMainData infoNew = elecLicenseMainDataService.getElecLicenseMainDataByElecOid(info.getBgNextElecOid());
                                        if (infoNew != null) {
                                            // 更新变更后证照状态
                                            infoNew.setBgAuditFlag(elecLicenseMainData.getBgAuditFlag());
                                            infoNew.setLicenseStatus(elecLicenseMainData.getBgAuditFlag());
                                            elecLicenseMainDataService.insert(infoNew);
                                        }
                                    }
                                }
                                String oid = elecLicenseMainDataService.insert(info);
                                if (!StringUtil.isEmpty(oid)) {
                                    successElecOids += info.getElecLicenOid() + ",";
                                }
                            }
                        }
                    }
                    if (!StringUtil.isEmpty(successElecOids)) {
                        return successElecOids.substring(0, successElecOids.length() - 1);
                    }
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 查看并更新证照信息
     * @author yuy
     * @date 2019-10-9
     * @param query
     * @return
     */
    @ApiOperation(value = "查看并更新es证照信息", notes = "需要传入json对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "请求数据", required = true, dataType = "String", paramType = "data")
    })
    @RequestMapping(value = "viewAndSaveElecLicenseMainData", method = RequestMethod.POST)
    public String viewAndSaveElecLicenseMainData(EsVo query) {
        String data = query.getData();
        if(data != null) {
            RSAEncrypt.setKeyMap(kafkaConstans.YXPT_PUBLIC_KEY_STRING, kafkaConstans.YXPT_PRIVATE_KEY_STRING);
            try {
                ElecLicenseMainData elecLicenseMainQueryVo = JSONObject.parseObject(RSAEncrypt.decrypt(data, kafkaConstans.YXPT_PRIVATE_KEY_STRING), ElecLicenseMainData.class);
                if (!StringUtil.isEmpty(elecLicenseMainQueryVo.getOid())) {
                    ElecLicenseMainData info = elecLicenseMainDataService.viewElecLicenseMainDataByOid(elecLicenseMainQueryVo.getOid());
                    if (info != null) {
                        if (!StringUtil.isEmpty(elecLicenseMainQueryVo.getLicenseStatus())) {// 证照新增审核
                            info.setLicenseStatus(elecLicenseMainQueryVo.getLicenseStatus());
                            info.setOrganOid(elecLicenseMainQueryVo.getOrganOid());
                            info.setElecLicenseIssueOid(elecLicenseMainQueryVo.getElecLicenseIssueOid());
                            info.setFinalStatus(elecLicenseMainQueryVo.getFinalStatus());
                        } else if (!StringUtil.isEmpty(elecLicenseMainQueryVo.getZxAuditFlag())) {// 证照注销审核
                            info.setZxCancelFlag(elecLicenseMainQueryVo.getZxAuditFlag());
                            info.setZxCancelFlag("1");
                        } else if (!StringUtil.isEmpty(elecLicenseMainQueryVo.getDxAuditFlag())) {// 证照吊销审核
                            info.setDxAuditFlag(elecLicenseMainQueryVo.getDxAuditFlag());
                            info.setDxCancelFlag("1");
                        } else if (!StringUtil.isEmpty(elecLicenseMainQueryVo.getYxAuditFlag())) {// 证照延续审核
                            info.setYxAuditFlag(elecLicenseMainQueryVo.getYxAuditFlag());
                            info.setYxDelFlag("N");
                        } else if (!StringUtil.isEmpty(elecLicenseMainQueryVo.getJcAuditFlag())) {// 证照纠错审核
                            info.setJcAuditFlag(elecLicenseMainQueryVo.getJcAuditFlag());
                            info.setJcDelFlag("N");
                        } else if (!StringUtil.isEmpty(elecLicenseMainQueryVo.getNjAuditFlag())) {// 证照年检审核
                            info.setNjAuditFlag(elecLicenseMainQueryVo.getNjAuditFlag());
                            info.setNjDelFlag("N");
                        } else if (!StringUtil.isEmpty(elecLicenseMainQueryVo.getBgAuditFlag())) {// 证照变更审核
                            info.setBgAuditFlag(elecLicenseMainQueryVo.getBgAuditFlag());
                        }
                        return elecLicenseMainDataService.insert(info);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 批量更新审核数据
     * @author yuy
     * @date 2019-10-10
     * @param query
     * @return
     */
    @ApiOperation(value = "批量更新审核数据", notes = "需要传入json对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "请求数据", required = true, dataType = "String", paramType = "data")
    })
    @RequestMapping(value = "batchUpdateQfElecLicenseMainData", method = RequestMethod.POST)
    public String batchUpdateQfElecLicenseMainData(EsVo query) {
        String data = query.getData();
        if(data != null) {
            RSAEncrypt.setKeyMap(kafkaConstans.YXPT_PUBLIC_KEY_STRING, kafkaConstans.YXPT_PRIVATE_KEY_STRING);
            try {
                ElecLicenseMainData elecLicenseMainData = JSONObject.parseObject(RSAEncrypt.decrypt(data, kafkaConstans.YXPT_PRIVATE_KEY_STRING), ElecLicenseMainData.class);
                if (!StringUtil.isEmpty(elecLicenseMainData.getOid())) {
                    String[] oids = elecLicenseMainData.getOid().split(",");
                    String[] organOids = elecLicenseMainData.getOrganOid().split(",");
                    String[] elecLicenseIssueOids = elecLicenseMainData.getElecLicenseIssueOid().split(",");
                    String[] finalStatus = elecLicenseMainData.getFinalStatus().split(",");
                    for (int i=0; i<oids.length; i++) {
                        if (!StringUtil.isEmpty(oids[i])) {
                            ElecLicenseMainData info = elecLicenseMainDataService.getElecLicenseMainDataByOid(oids[i]);
                            if (info != null) {
                                info.setOrganOid(organOids[i]);
                                info.setElecLicenseIssueOid(elecLicenseIssueOids[i]);
                                info.setFinalStatus(finalStatus[i]);
                                elecLicenseMainDataService.insert(info);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 签发证照
     * @author yuy
     * @date 2019-10-11
     * @param query
     * @return
     */
    @ApiOperation(value = "签发证照", notes = "需要传入json对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "请求数据", required = true, dataType = "String", paramType = "data")
    })
    @RequestMapping(value = "issueElecLicenseMainData", method = RequestMethod.POST)
    public String issueElecLicenseMainData(EsVo query) {
        String data = query.getData();
        if(data != null) {
            RSAEncrypt.setKeyMap(kafkaConstans.YXPT_PUBLIC_KEY_STRING, kafkaConstans.YXPT_PRIVATE_KEY_STRING);
            try {
                ElecLicenseMainData elecLicenseMainData = JSONObject.parseObject(RSAEncrypt.decrypt(data, kafkaConstans.YXPT_PRIVATE_KEY_STRING), ElecLicenseMainData.class);
                if (!StringUtil.isEmpty(elecLicenseMainData.getOid())) {
                    ElecLicenseMainData info = elecLicenseMainDataService.getElecLicenseMainDataByOid(elecLicenseMainData.getOid());
                    if (info != null) {
                        String[] elecLicenseIssueOids = info.getElecLicenseIssueOid().split("-");
                        String[] finalStatuArr = info.getFinalStatus().split("-");
                        String status = "";
                        if (!StringUtil.isEmpty(elecLicenseMainData.getLicenseStatus())) {//签发全部完成或不通过
                            for (int i=0; i<elecLicenseIssueOids.length; i++) {
                                finalStatuArr[i] = elecLicenseMainData.getLicenseStatus();
                            }
                            for (String str : finalStatuArr) {
                                status += str + "-";
                            }
                        } else {
                            for (int i=0; i<elecLicenseIssueOids.length; i++) {
                                if (elecLicenseIssueOids[i].equals(elecLicenseMainData.getElecLicenseIssueOid())) {
                                    finalStatuArr[i] = elecLicenseMainData.getFinalStatus();
                                }
                            }
                            for (String str : finalStatuArr) {
                                status += str + "-";
                            }
                        }
                        info.setFinalStatus(status.substring(0, status.length() - 1));
                        info.setLicenseStatus(elecLicenseMainData.getFinalStatus());
                        return elecLicenseMainDataService.insert(info);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 证照维护
     * @author yuy
     * @date 2019-10-13
     * @param query
     * @return
     */
    @ApiOperation(value = "证照维护更新es证照信息", notes = "需要传入json对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "请求数据", required = true, dataType = "String", paramType = "data")
    })
    @RequestMapping(value = "whElecLicenseMainData", method = RequestMethod.POST)
    public String whElecLicenseMainData(EsVo query) {
        String data = query.getData();
        if(data != null) {
            RSAEncrypt.setKeyMap(kafkaConstans.YXPT_PUBLIC_KEY_STRING, kafkaConstans.YXPT_PRIVATE_KEY_STRING);
            try {
                ElecLicenseMainData elecLicenseMainQueryVo = JSONObject.parseObject(RSAEncrypt.decrypt(data, kafkaConstans.YXPT_PRIVATE_KEY_STRING), ElecLicenseMainData.class);
                if (!StringUtil.isEmpty(elecLicenseMainQueryVo.getElecLicenOid())) {
                    ElecLicenseMainData info = elecLicenseMainDataService.getElecLicenseMainDataByElecOid(elecLicenseMainQueryVo.getElecLicenOid());
                    if (info != null) {
                        if (StringUtil.isNotEmpty(elecLicenseMainQueryVo.getLicenseStatus())){
                            info.setLicenseStatus(elecLicenseMainQueryVo.getLicenseStatus());
                        }
                        if (!StringUtil.isEmpty(elecLicenseMainQueryVo.getZxAuditFlag())) {// 证照注销
                            info.setZxAuditFlag(elecLicenseMainQueryVo.getZxAuditFlag());
                            info.setZxCancelFlag(elecLicenseMainQueryVo.getZxCancelFlag());
                        } else if (!StringUtil.isEmpty(elecLicenseMainQueryVo.getDxAuditFlag())) {// 证照吊销
                            info.setDxAuditFlag(elecLicenseMainQueryVo.getDxAuditFlag());
                            info.setDxCancelFlag("1");
                        } else if (!StringUtil.isEmpty(elecLicenseMainQueryVo.getYxAuditFlag())) {// 证照延续
                            info.setYxAuditFlag(elecLicenseMainQueryVo.getYxAuditFlag());
                            info.setYxDelFlag(elecLicenseMainQueryVo.getYxDelFlag());
                        } else if (!StringUtil.isEmpty(elecLicenseMainQueryVo.getJcAuditFlag())) {// 证照纠错
                            info.setJcAuditFlag(elecLicenseMainQueryVo.getJcAuditFlag());
                            info.setJcDelFlag(elecLicenseMainQueryVo.getJcDelFlag());
                        } else if (!StringUtil.isEmpty(elecLicenseMainQueryVo.getNjAuditFlag())) {// 证照年检审核
                            info.setNjAuditFlag(elecLicenseMainQueryVo.getNjAuditFlag());
                            info.setNjDelFlag(elecLicenseMainQueryVo.getNjDelFlag());
                        } else if (!StringUtil.isEmpty(elecLicenseMainQueryVo.getBgAuditFlag())) {// 批文变更
                            info.setBgAuditFlag(elecLicenseMainQueryVo.getBgAuditFlag());
                            info.setLicenseStatus(elecLicenseMainQueryVo.getBgAuditFlag());
                        }
                        return elecLicenseMainDataService.insert(info);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 插入并更新证照信息
     * @author yuy
     * @date 2019-10-14
     * @param query
     * @return
     */
    @ApiOperation(value = "插入并更新es证照信息", notes = "需要传入json对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "请求数据", required = true, dataType = "String", paramType = "data")
    })
    @RequestMapping(value = "insertAndUpdateElecLicenseMainData", method = RequestMethod.POST)
    public String insertAndUpdateElecLicenseMainData(EsVo query) {
        String data = query.getData();
        if(data != null) {
            RSAEncrypt.setKeyMap(kafkaConstans.YXPT_PUBLIC_KEY_STRING, kafkaConstans.YXPT_PRIVATE_KEY_STRING);
            try {
                ElecLicenseMainData elecLicenseMainData = JSONObject.parseObject(RSAEncrypt.decrypt(data, kafkaConstans.YXPT_PRIVATE_KEY_STRING), ElecLicenseMainData.class);
                if(StringUtil.isEmpty(elecLicenseMainData.getOid())) {
                    SnowFlake snowFlake = new SnowFlake(kafkaConstans.DATACENTER_ID, kafkaConstans.MACHINE_ID);
                    elecLicenseMainData.setOid(String.valueOf(snowFlake.nextId()));
                }
                String oid = elecLicenseMainDataService.insert(elecLicenseMainData);
                if (StringUtil.isNotEmpty(oid)) {
                    // 查询上一次证照并更新
                    ElecLicenseMainData info = elecLicenseMainDataService.getElecLicenseMainDataByElecOid(elecLicenseMainData.getBgPreElecOid());
                    if (info != null) {
                        info.setBgNextElecOid(elecLicenseMainData.getElecLicenOid());
                        info.setBgNextAuditFlag(elecLicenseMainData.getLicenseStatus());
                        elecLicenseMainDataService.insert(info);
                        return oid;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 更新两条证照信息
     * @author yuy
     * @date 2019-10-14
     * @param query
     * @return
     */
    @ApiOperation(value = "更新两条证照信息", notes = "需要传入json对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "请求数据", required = true, dataType = "String", paramType = "data")
    })
    @RequestMapping(value = "updateTwoElecLicenseMainData", method = RequestMethod.POST)
    public String updateTwoElecLicenseMainData(EsVo query) {
        String data = query.getData();
        if(data != null) {
            RSAEncrypt.setKeyMap(kafkaConstans.YXPT_PUBLIC_KEY_STRING, kafkaConstans.YXPT_PRIVATE_KEY_STRING);
            try {
                ElecLicenseMainData elecLicenseMainData = JSONObject.parseObject(RSAEncrypt.decrypt(data, kafkaConstans.YXPT_PRIVATE_KEY_STRING), ElecLicenseMainData.class);
                ElecLicenseMainData infoOld = elecLicenseMainDataService.getElecLicenseMainDataByElecOid(elecLicenseMainData.getElecLicenOid());
                if (infoOld != null) {
                    // 更新上一次证照状态
                    infoOld.setBgAuditFlag(elecLicenseMainData.getBgAuditFlag());
                    infoOld.setBgNextAuditFlag(elecLicenseMainData.getBgAuditFlag());
                    elecLicenseMainDataService.insert(infoOld);
                    ElecLicenseMainData infoNew = elecLicenseMainDataService.getElecLicenseMainDataByElecOid(infoOld.getBgNextElecOid());
                    if (infoNew != null) {
                        // 更新变更后证照状态
                        infoNew.setBgAuditFlag(elecLicenseMainData.getBgAuditFlag());
                        infoNew.setLicenseStatus(elecLicenseMainData.getBgAuditFlag());
                        return elecLicenseMainDataService.insert(infoNew);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 更新证照信息的状态（证照签发）
     * @param query
     * @return
     */
    @ApiOperation(value = "更新证照信息的状态", notes = "需要传入json对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "请求数据", required = true, dataType = "String", paramType = "data")
    })
    @RequestMapping(value = "updateElecLicenseMainDataIssue", method = RequestMethod.POST)
    public String updateElecLicenseMainDataIssue(EsVo query) {
        String data = query.getData();
        if(data != null) {
            RSAEncrypt.setKeyMap(kafkaConstans.YXPT_PUBLIC_KEY_STRING, kafkaConstans.YXPT_PRIVATE_KEY_STRING);
            try {
                ElecLicenseMainData elecLicenseMainData = JSONObject.parseObject(RSAEncrypt.decrypt(data, kafkaConstans.YXPT_PRIVATE_KEY_STRING), ElecLicenseMainData.class);
                ElecLicenseMainData infoOld = elecLicenseMainDataService.getElecLicenseMainDataByElecOid(elecLicenseMainData.getElecLicenOid());
                if (infoOld != null) {
                    // 更新上一次证照状态
                    infoOld.setFinalStatus(elecLicenseMainData.getFinalStatus());
                    infoOld.setLicenseStatus(elecLicenseMainData.getLicenseStatus());
                    infoOld.setElecLicenseIssueOid(elecLicenseMainData.getElecLicenseIssueOid());
                    elecLicenseMainDataService.insert(infoOld);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    /**
     * 查询待审核数量
     * @author yuy
     * @date 2019-10-15
     * @param query
     * @return
     */
    @ApiOperation(value = "查询待审核数量", notes = "需要传入json对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "请求数据", required = true, dataType = "String", paramType = "data")
    })
    @RequestMapping(value = "queryElecLicenseMainDataAuditNum", method = RequestMethod.POST)
    public String queryElecLicenseMainDataAuditNum(EsVo query) {
        String data = query.getData();
        if(data != null) {
            RSAEncrypt.setKeyMap(kafkaConstans.YXPT_PUBLIC_KEY_STRING, kafkaConstans.YXPT_PRIVATE_KEY_STRING);
            try {
                String jsonStr = "{";
                ElecLicenseMainQueryVo elecLicenseMainQueryVo = JSONObject.parseObject(RSAEncrypt.decrypt(data, kafkaConstans.YXPT_PRIVATE_KEY_STRING), ElecLicenseMainQueryVo.class);
                String[] auditTypeArr = elecLicenseMainQueryVo.getElecLicenseIssueOid().split(",");
                for (String auditType : auditTypeArr) {
                    long num = elecLicenseMainDataService.queryElecLicenseMainDataAuditNum(elecLicenseMainQueryVo, auditType);
                    jsonStr += "'" + auditType + "':" + num + ",";
                }
                if (jsonStr.length() > 1) {
                    jsonStr = jsonStr.substring(0, jsonStr.length() - 1);
                }
                jsonStr += "}";
                return jsonStr;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 批量导入json数据
     * @author yuy
     * @date 2019-11-05
     * @return
     */
    @ApiOperation(value = "批量导入json数据到es中", notes = "需要传入文件路径")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "请求数据", required = true, dataType = "String", paramType = "data")
    })
    @RequestMapping(value = "importElecLicenseMainDataJson.do", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String importElecLicenseMainDataJson(String path) {
        try {
            if (StringUtil.isNotEmpty(path)) {
                path = path.replace("-", "\\");
                new Thread(new InsertEsDataThread(elecLicenseMainDataService, path)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "1";
    }

    /**
     * 清空数据
     * @author yuy
     * @date 2019-11-18
     * @return
     */
    @ApiOperation(value = "清空数据", notes = "需要传入文件路径")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "请求数据", required = true, dataType = "String", paramType = "data")
    })
    @RequestMapping(value = "deleteEsDate.do", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String deleteEsDate(String indexName) {
        try {
            elecLicenseMainDataService.deleteElecLicenseMainDataIndex(indexName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "1";
    }

    /**
     * @desc: 查询
     * @author: chenyj
     * @date: 2020/3/9
     * @param oid
     * @return
     */
    @GetMapping("{oid}")
    public Result get(@PathVariable String oid){
        try {
            logger.info("入参,查询Oid="+oid);
            ElecLicenseMainData elecLicenseMainData=new ElecLicenseMainData();
            elecLicenseMainData.setOid(oid);
            elecLicenseMainData= elecLicenseMainDataService.viewElecLicenseMainDataByOid(oid);
            return new Result(StatusCodeEnum.SUCCESS,elecLicenseMainData);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new Result(StatusCodeEnum.FAIL);
    }

    /**
     * @desc: 保存
     * @author: chenyj
     * @date: 2020/3/10
     * @param oid
     * @param elecLicenseMainData
     * @return
     */
    @PostMapping("{oid}")
    public Result upd(@PathVariable String oid,
                      @RequestBody ElecLicenseMainData elecLicenseMainData){
        try {
            elecLicenseMainData.setOid(oid);
            oid=elecLicenseMainDataService.insert(elecLicenseMainData);
            elecLicenseMainData = elecLicenseMainDataService.getElecLicenseMainDataByElecOid(oid);
            return new Result(StatusCodeEnum.SUCCESS,elecLicenseMainData);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new Result(StatusCodeEnum.FAIL);
    }

}
