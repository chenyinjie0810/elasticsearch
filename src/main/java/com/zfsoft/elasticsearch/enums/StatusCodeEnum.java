package com.zfsoft.elasticsearch.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @Author chenyj
 * @Description
 * @Date create by 2019/10/22 22:59
 * 陈银杰专属测试
 */

@AllArgsConstructor
@NoArgsConstructor
public enum  StatusCodeEnum {

    SUCCESS(true, "成功"),
    FAIL(false, "失败");

    private boolean flag;
    private String message;

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
