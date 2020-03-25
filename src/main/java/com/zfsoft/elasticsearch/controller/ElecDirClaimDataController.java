package com.zfsoft.elasticsearch.controller;
import com.zfsoft.elasticsearch.entity.Result;
import com.zfsoft.elasticsearch.enums.StatusCodeEnum;
import com.zfsoft.elasticsearch.pojo.ElecDirClaimData;
import com.zfsoft.elasticsearch.service.IElecDirClaimDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Author chenyj
 * @Description
 * @Date create by 2020/3/3 17:31
 * 陈银杰专属测试
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/elecDirClaimData/")
public class ElecDirClaimDataController {

    @Autowired
    private IElecDirClaimDataService elecDirClaimDataService;

    /**
     * @desc: 根据oid查询
     * @author: chenyj
     * @date: 2020/3/3
     * @param oid
     * @return
     */
    @GetMapping("{oid}")
    public Result get(@PathVariable String oid){
        ElecDirClaimData elecDirClaimData=null;
        try {
            elecDirClaimData = elecDirClaimDataService.getElecDirClaimDataByOid(oid);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(StatusCodeEnum.FAIL);
        }
        return new Result(StatusCodeEnum.SUCCESS,elecDirClaimData);
    }

    /**
     * @desc: 保存
     * @author: chenyj
     * @date: 2020/3/3
     * @param oid
     * @param elecDirClaimData
     * @return
     */
    @PostMapping("{oid}")
    public Result save(@PathVariable String oid,
                       @RequestBody ElecDirClaimData elecDirClaimData){
        try{
            elecDirClaimData.setOid(oid);
            elecDirClaimData.setModifyDate(new Date());
            elecDirClaimDataService.saveElecDirClaimData(elecDirClaimData);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(StatusCodeEnum.FAIL);
        }
        return new Result(StatusCodeEnum.SUCCESS);
    }
}
