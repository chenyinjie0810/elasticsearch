package com.zfsoft.elasticsearch.vo;

/**
 * @author chenjian
 */
public class EsVo {
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("EsVo{");
        sb.append("data='").append(data).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
