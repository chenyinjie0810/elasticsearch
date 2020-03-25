package com.zfsoft.elasticsearch.util;

import java.io.*;

/**
 * @Author chenyj
 * @Description
 * @Date create by 2020/3/9 14:33
 * 陈银杰专属测试
 */
public class FileUtil {

    /**
     * @desc: 获取文件内容
     * @author: chenyj
     * @date: 2020/3/9
     * @param file
     * @return
     */
    public static String getContentByFile(File file){
        StringBuffer content=null;
        BufferedReader bufferedReader=null;
        try {
            bufferedReader=new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String a;
            content=new StringBuffer();
            while ((a=bufferedReader.readLine()) != null){
                content.append(a+"\n");
            }
            System.out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return content.toString();
    }
}
