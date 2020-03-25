package com.zfsoft.elasticsearch.service.impl;

import com.zfsoft.elasticsearch.pojo.ElecLicenseDirMainData;
import com.zfsoft.elasticsearch.service.IElecLicenseDirMainDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

/**
 * @Author chenyj
 * @Description
 * @Date create by 2020/3/2 16:12
 * 陈银杰专属测试
 */
@Service
public class ElecLicenseDirMainDataServiceImpl implements IElecLicenseDirMainDataService {
    /**
     * es jpa的形式，但是失败了
     */
  /*  @Autowired
    private IElecLicenseDirMainDataDao elecLicenseDirMainDataDao;*/

    @Autowired
    private ElasticsearchTemplate template;


    @Override
    public void save(String oid,ElecLicenseDirMainData elecLicenseDirMainData) throws Exception {
        if (elecLicenseDirMainData != null) {
            IndexQuery indexQuery = new IndexQueryBuilder().withId(oid).withObject(elecLicenseDirMainData).build();
            String documentId=template.index(indexQuery);
        }
    }

    @Override
    public Page<ElecLicenseDirMainData> findByDirectoryNameLike(String directoryName, int pageNumber, int pageSize) {
        Pageable pageable= PageRequest.of(pageNumber-1, pageSize);
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("directoryName", directoryName)).withPageable(pageable).build();
        return template.queryForPage(searchQuery,ElecLicenseDirMainData.class);
    }

    @Override
    public ElecLicenseDirMainData getElecLicenseDirMainByOid(String oid) throws Exception {
        GetQuery getQuery = new GetQuery();
        getQuery.setId(oid);
        return  template.queryForObject(getQuery, ElecLicenseDirMainData.class);

    }
}
