package com.zfsoft.elasticsearch.controller;

import com.zfsoft.elasticsearch.entity.PageResult;
import com.zfsoft.elasticsearch.entity.Result;
import com.zfsoft.elasticsearch.enums.StatusCodeEnum;
import com.zfsoft.elasticsearch.pojo.ElecLicenseDirMainData;
import com.zfsoft.elasticsearch.service.IElecLicenseDirMainDataService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @Author chenyj
 * @Description
 * @Date create by 2020/3/2 16:19
 * 陈银杰专属测试
 */
@Api(tags = {"ElecLicenseDirMain"}, description = "es")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/elecLicenseDirMain/")
public class ElecLicenseDirMainDataController {

    @Autowired
    private IElecLicenseDirMainDataService elecLicenseDirMainDataService;

    /**
     * @desc: 获取信息
     * @author: chenyj
     * @date: 2020/3/3
     * @return
     */
    @GetMapping("{oid}")
    public Result get(@PathVariable String oid){
        ElecLicenseDirMainData elecLicenseDirMain =null;
        try {
            elecLicenseDirMain = elecLicenseDirMainDataService.getElecLicenseDirMainByOid(oid);
        } catch (Exception e) {
            e.printStackTrace();
            new Result(StatusCodeEnum.FAIL);
        }
        return new Result(StatusCodeEnum.SUCCESS,elecLicenseDirMain);
    }

    /**
     * @desc: 添加
     * @author: chenyj
     * @date: 2020/3/2
     * @param oid
     * @param elecLicenseDirMainData
     * @return
     */
    @PostMapping("{oid}")
    public Result save(@PathVariable String oid,
                       @RequestBody ElecLicenseDirMainData elecLicenseDirMainData){
        try {
            elecLicenseDirMainDataService.save(oid,elecLicenseDirMainData);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(StatusCodeEnum.FAIL);
        }
        return new Result(StatusCodeEnum.SUCCESS);
    }

    /**
     * @desc: 简单查询
     * @author: chenyj
     * @date: 2020/3/2
     * @param directoryName
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/search/{directoryName}/{pageNumber}/{pageSize}")
    public Result find(@PathVariable String directoryName,
                       @PathVariable int pageNumber,
                       @PathVariable int pageSize){
        Page<ElecLicenseDirMainData> page=elecLicenseDirMainDataService.findByDirectoryNameLike(directoryName,pageNumber, pageSize);
        return  new Result(StatusCodeEnum.SUCCESS,new PageResult<ElecLicenseDirMainData>(page));
    }

}
