package com.zfsoft.elasticsearch.service.impl;

import com.carrotsearch.hppc.cursors.ObjectObjectCursor;
import com.github.pagehelper.PageInfo;
import com.zfsoft.elasticsearch.pojo.FwPublicRegistrationData;
import com.zfsoft.elasticsearch.service.FwPublicRegistrationDataService;
import com.zfsoft.elasticsearch.util.PageUtil;
import com.zfsoft.elasticsearch.vo.FwPublicRegistrationQueryVo;
import org.elasticsearch.client.Client;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

/**
 * @author chenjian
 * 窗口登记的表单数据入库服务
 */
@Service
public class FwPublicRegistrationDataServiceImpl implements FwPublicRegistrationDataService {

    @Autowired
    private ElasticsearchTemplate template;
    @Autowired
    private Client esClient;

    @Override
    public String insert(FwPublicRegistrationData fwPublicRegistrationData) {
        if (fwPublicRegistrationData != null) {
            IndexQuery indexQuery = new IndexQueryBuilder().withId(fwPublicRegistrationData.getOid()).withObject(fwPublicRegistrationData).build();
            String index = template.index(indexQuery);
            if (!StringUtils.isEmpty(index)) {
                return index.replaceAll("\"", "");
            }
        }
        return null;
    }

    /**
     * 查询窗口登记的表单数据对象
     * @return
     * @param fwPublicRegistrationQueryVo
     */
    @Override
    public PageInfo queryFwPublicRegistrationDataList(FwPublicRegistrationQueryVo fwPublicRegistrationQueryVo) {
        if(fwPublicRegistrationQueryVo != null) {
            // 分页判断
            if (fwPublicRegistrationQueryVo.getPageNum() == null || fwPublicRegistrationQueryVo.getPageNum() < 0) {
                fwPublicRegistrationQueryVo.setPageNum(0);
            }
            // ES 首页=0
            if (fwPublicRegistrationQueryVo.getPageNum() > 0) {
                fwPublicRegistrationQueryVo.setPageNum(fwPublicRegistrationQueryVo.getPageNum()-1);
            }
            if (fwPublicRegistrationQueryVo.getPageSize() == null || fwPublicRegistrationQueryVo.getPageSize() <= 0) {
                fwPublicRegistrationQueryVo.setPageSize(10);
            }
            NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder().withIndices("elms_elasticsearch").withTypes("FwPublicRegistrationData");
            BoolQueryBuilder boolQuery_1 = QueryBuilders.boolQuery();
            if(!StringUtils.isEmpty(fwPublicRegistrationQueryVo.getRegNumber())) {
                boolQuery_1.must(QueryBuilders.termsQuery("regNumber", fwPublicRegistrationQueryVo.getRegNumber()));
            }
            if(!StringUtils.isEmpty(fwPublicRegistrationQueryVo.getCreateDateBegin())) {
                boolQuery_1.must(QueryBuilders.termsQuery("createDateBegin", fwPublicRegistrationQueryVo.getCreateDateBegin()));
            }
            if(fwPublicRegistrationQueryVo.getCreateDateBegin() != null || fwPublicRegistrationQueryVo.getCreateDateEnd() != null) {
                boolQuery_1.must(QueryBuilders.termsQuery("createDateBegin", fwPublicRegistrationQueryVo.getCreateDateEnd()));
            }
//            boolQuery_1.must(QueryBuilders.termsQuery("type", "FwPublicRegistrationData"));
            // 设置类型
            NativeSearchQuery nativeSearchQuery = queryBuilder.withQuery(boolQuery_1)
                    // 排序 按 info_date 倒序
                    .withSort(new FieldSortBuilder("createDate").order(SortOrder.DESC))
                    // 分页
                    .withPageable(PageRequest.of(fwPublicRegistrationQueryVo.getPageNum(), fwPublicRegistrationQueryVo.getPageSize()))
                    .build();
            AggregatedPage<FwPublicRegistrationData> secInfos = template.queryForPage(nativeSearchQuery, FwPublicRegistrationData.class);
            if (secInfos != null) {
                return PageUtil.change(fwPublicRegistrationQueryVo.getPageNum() + 1, fwPublicRegistrationQueryVo.getPageSize(), (int) secInfos.getTotalElements(), secInfos.getContent());
            }else {
                return PageUtil.change(fwPublicRegistrationQueryVo.getPageNum() + 1, fwPublicRegistrationQueryVo.getPageSize(), null, null);
            }
        }
        return null;
    }

    /**
     * 获取索引下的所有类型[索引==数据库|类型==表]
     *
     * @param indexName 索引名
     * @return String[]
     */
    private String[] getTypes(String... indexName) {
        if (indexName != null && indexName.length > 0) {
            ArrayList<String> temp = new ArrayList<>();
            for (String index : indexName) {
                ImmutableOpenMap<String, MappingMetaData> mappings = esClient.admin().cluster().prepareState().execute()
                        .actionGet().getState().getMetaData().getIndices().get(index).getMappings();
                if (mappings != null && mappings.size() > 0) {
                    for (ObjectObjectCursor<String, MappingMetaData> cursor : mappings) {
                        temp.add(cursor.key);
                    }
                }
            }
            return temp.toArray(new String[temp.size()]);
        }
        return null;
    }
}
