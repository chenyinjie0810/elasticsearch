package com.zfsoft.elasticsearch.entity;

import com.zfsoft.elasticsearch.enums.StatusCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author chenyj
 * @Description
 * @Date create by 2019/10/22 22:51
 * 陈银杰专属测试
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Result {

    /**
     *成功标识
     */
    private boolean flag;
    /**
     * 错误码
     */
    private Integer code;

    /**
     * 反馈信息
     */
    private String message;

    /**
     * 返回数据
     */
    private Object data;

    public void setSuccess(StatusCodeEnum statusCodeEnum, Object data){
        this.flag=statusCodeEnum.getFlag();
        this.message=statusCodeEnum.getMessage();
        if (null!= data) {
            this.data=data;
        }
    }

    public Result(boolean flag, Object data) {
        this.flag = flag;
        this.data = data;
    }

    public Result(StatusCodeEnum statusCodeEnum,Object data){
        this.flag=statusCodeEnum.getFlag();
        this.message=statusCodeEnum.getMessage();
        if (null!= data) {
            this.data=data;
        }
    }
    public Result(StatusCodeEnum statusCodeEnum){
        this.flag=statusCodeEnum.getFlag();
        this.message=statusCodeEnum.getMessage();
    }


    public Result(boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }
}
