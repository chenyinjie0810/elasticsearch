package com.zfsoft.elasticsearch.service.impl;

import com.zfsoft.elasticsearch.pojo.ElecDirClaimData;
import com.zfsoft.elasticsearch.service.IElecDirClaimDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;

/**
 * @Author chenyj
 * @Description
 * @Date create by 2020/3/3 17:08
 * 陈银杰专属测试
 */
@Service
public class ElecDirClaimDataServiceImpl implements IElecDirClaimDataService {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public ElecDirClaimData getElecDirClaimDataByOid(String oid) throws Exception {
        GetQuery query=new GetQuery();
        query.setId(oid);
        return elasticsearchTemplate.queryForObject(query, ElecDirClaimData.class);
    }

    @Override
    public void saveElecDirClaimData(ElecDirClaimData dirClaimData) throws Exception {
        IndexQuery indexQuery = new IndexQueryBuilder().withId(dirClaimData.getOid()).withObject(dirClaimData).build();
        String documentId = elasticsearchTemplate.index(indexQuery);
    }

    @Override
    public void deleteElecDirClaimDataByOid(String oid) throws Exception {
        elasticsearchTemplate.delete(ElecDirClaimData.class, oid);
    }

    @Override
    public Page<ElecDirClaimData> query(ElecDirClaimData elecDirClaimData, int pageNumber, int pageSize) throws Exception {
        return null;
    }
}
