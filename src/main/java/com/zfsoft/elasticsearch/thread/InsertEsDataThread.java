package com.zfsoft.elasticsearch.thread;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.StringUtil;
import com.zfsoft.elasticsearch.pojo.ElecLicenseMainData;
import com.zfsoft.elasticsearch.service.ElecLicenseMainDataService;

import java.io.File;
import java.io.FileInputStream;

/**
 * 插入es数据
 * @author yuy
 * @date 2019-11-12
 */
public class InsertEsDataThread extends Thread {

    private ElecLicenseMainDataService elecLicenseMainDataService;

    private String path;

    public InsertEsDataThread(ElecLicenseMainDataService elecLicenseMainDataService, String path) {
        super();
        this.elecLicenseMainDataService = elecLicenseMainDataService;
        this.path = path;
    }

    @Override
    public void run() {
        int i = 0;
        try {
            long start = System.currentTimeMillis();
            File f = new File(path);
            Long filelength = f.length();
            byte[] filecontent = new byte[filelength.intValue()];
            FileInputStream in = new FileInputStream(f);
            in.read(filecontent);
            in.close();
            String content = new String(filecontent);
            JSONArray jsonArray = JSON.parseArray(content);
            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                ElecLicenseMainData elecLicenseMainData = jsonObject.toJavaObject(ElecLicenseMainData.class);
                String data = elecLicenseMainDataService.insert(elecLicenseMainData);
                System.out.println(path + "-------" + data+"-----"+i);
                if (StringUtil.isNotEmpty(data)) {
                    i++;
                }
            }
            long end = System.currentTimeMillis();
            System.out.println("文件名："+path+"，插入数据="+i+"，用时秒数："+(end-start)/1000);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("文件名："+path+"，插入数据="+i);
        }
    }

}
