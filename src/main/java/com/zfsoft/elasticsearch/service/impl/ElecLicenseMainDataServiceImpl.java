package com.zfsoft.elasticsearch.service.impl;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import com.zfsoft.elasticsearch.pojo.ElecLicenseMainData;
import com.zfsoft.elasticsearch.service.ElecLicenseMainDataService;
import com.zfsoft.elasticsearch.util.PageUtil;
import com.zfsoft.elasticsearch.vo.ElecLicenseMainQueryVo;
import com.zfsoft.elasticsearch.vo.EsElecLicneseQueryVo;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author chenjian
 * 电子证照的表单数据入库服务
 */
@Service
public class ElecLicenseMainDataServiceImpl implements ElecLicenseMainDataService {

    @Autowired
    private ElasticsearchTemplate template;


    @Override
    public String insert(ElecLicenseMainData elecLicenseMainData) {
        if (elecLicenseMainData != null) {
            IndexQuery indexQuery = new IndexQueryBuilder().withId(elecLicenseMainData.getOid()).withObject(elecLicenseMainData).build();
            String index = template.index(indexQuery);
            if (!StringUtils.isEmpty(index)) {
                return index;
            }
        }
        return null;
    }

    @Override
    public PageInfo queryElecLicenseMainDataList(ElecLicenseMainQueryVo elecLicenseMainQueryVo) {
        if (elecLicenseMainQueryVo != null) {
            // 分页判断
            if (elecLicenseMainQueryVo.getPageNum() == null || elecLicenseMainQueryVo.getPageNum() < 0) {
                elecLicenseMainQueryVo.setPageNum(0);
            }
            // ES 首页=0
            if (elecLicenseMainQueryVo.getPageNum() > 0) {
                elecLicenseMainQueryVo.setPageNum(elecLicenseMainQueryVo.getPageNum() - 1);
            }
            if (elecLicenseMainQueryVo.getPageSize() == null || elecLicenseMainQueryVo.getPageSize() <= 0) {
                elecLicenseMainQueryVo.setPageSize(10);
            }
            NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder().withIndices("elms_elasticsearch").withTypes("ElecLicenseMainData");
            BoolQueryBuilder boolQuery_1 = QueryBuilders.boolQuery();
            if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getDirectoryName())) {
                boolQuery_1.must(QueryBuilders.matchQuery("directoryName", elecLicenseMainQueryVo.getDirectoryName()));
            }
            if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getHolder())) {
                boolQuery_1.must(QueryBuilders.matchQuery("holder", elecLicenseMainQueryVo.getHolder()));
            }
            if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getIdentificateNumber())) {
                boolQuery_1.must(QueryBuilders.termsQuery("identificateNumber", elecLicenseMainQueryVo.getIdentificateNumber()));
            }
            if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getClaimOid())) {
                boolQuery_1.must(QueryBuilders.termsQuery("claimOid", elecLicenseMainQueryVo.getClaimOid()));
            }
            // by yuy 2019-10-11 start
            if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getOrganOid())) {
                boolQuery_1.must(QueryBuilders.wildcardQuery("organOid", "*" + elecLicenseMainQueryVo.getOrganOid() + "*"));
            }

            if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getZxAuditFlag())) {// 注销
                boolQuery_1.must(QueryBuilders.termsQuery("zxAuditFlag", "6"));
                boolQuery_1.must(QueryBuilders.termsQuery("zxCancelFlag", "1"));
            } else if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getDxAuditFlag())) {// 吊销
                boolQuery_1.must(QueryBuilders.termsQuery("dxAuditFlag", "12"));
                boolQuery_1.must(QueryBuilders.termsQuery("dxCancelFlag", "1"));
            } else if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getBgAuditFlag())) {// 变更
                // 变更待审核(需要保证上一个版本为签发存档，以防在变更的时候走了注销或者吊销)
                boolQuery_1.must(QueryBuilders.termsQuery("licenseStatus", "20"));
                boolQuery_1.mustNot(QueryBuilders.termsQuery("licenseStatus", "4"));
                boolQuery_1.must(QueryBuilders.termsQuery("preLicenseStatus", "4"));
                //boolQuery_1.must(QueryBuilders.termsQuery("bgAuditFlag", "20"));
                //boolQuery_1.mustNot(QueryBuilders.termsQuery("bgPreElecOid", ""));
                //boolQuery_1.must(QueryBuilders.termsQuery("preLicenseStatus", "4"));
            } else if ("1".equals(elecLicenseMainQueryVo.getLicenseStatus())) {// 新增审核
                boolQuery_1.must(QueryBuilders.termsQuery("licenseStatus", "1"));
            }

            if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getLicenseStatus())) {
                if (elecLicenseMainQueryVo.getLicenseStatus().contains(",")) {
                    String[] statusArr = elecLicenseMainQueryVo.getLicenseStatus().split(",");
                    boolQuery_1.must(QueryBuilders.termsQuery("licenseStatus", statusArr));
                } else {
                    boolQuery_1.must(QueryBuilders.termsQuery("licenseStatus", elecLicenseMainQueryVo.getLicenseStatus()));
                }
            }
            //add by chenyq 20200313 来源
            if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getInfoSource())) {
                boolQuery_1.must(QueryBuilders.termsQuery("infoSource", elecLicenseMainQueryVo.getInfoSource()));
            }

            //目录名称
            if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getDirectoryName())) {
                boolQuery_1.must(QueryBuilders.matchQuery("directoryName", elecLicenseMainQueryVo.getDirectoryName()));
            }

            // by yuy 2019-10-11 end
            // 设置类型
            NativeSearchQuery nativeSearchQuery = queryBuilder.withQuery(boolQuery_1)
                    // 排序 按 info_date 倒序
                    .withSort(new FieldSortBuilder("createTime").order(SortOrder.DESC))
                    // 分页
                    .withPageable(PageRequest.of(elecLicenseMainQueryVo.getPageNum(), elecLicenseMainQueryVo.getPageSize()))
                    .build();
            AggregatedPage<ElecLicenseMainData> secInfos = template.queryForPage(nativeSearchQuery, ElecLicenseMainData.class);
            if (secInfos != null) {
                return PageUtil.change(elecLicenseMainQueryVo.getPageNum() + 1, elecLicenseMainQueryVo.getPageSize(), (int) secInfos.getTotalElements(), secInfos.getContent());
            } else {
                return PageUtil.change(elecLicenseMainQueryVo.getPageNum() + 1, elecLicenseMainQueryVo.getPageSize(), null, null);
            }
        }
        return null;
    }

    @Override
    public PageInfo queryElecLicenseMainDataWhList(ElecLicenseMainQueryVo elecLicenseMainQueryVo) {
        if (elecLicenseMainQueryVo != null) {
            // 分页判断
            if (elecLicenseMainQueryVo.getPageNum() == null || elecLicenseMainQueryVo.getPageNum() < 0) {
                elecLicenseMainQueryVo.setPageNum(0);
            }
            // ES 首页=0
            if (elecLicenseMainQueryVo.getPageNum() > 0) {
                elecLicenseMainQueryVo.setPageNum(elecLicenseMainQueryVo.getPageNum() - 1);
            }
            if (elecLicenseMainQueryVo.getPageSize() == null || elecLicenseMainQueryVo.getPageSize() <= 0) {
                elecLicenseMainQueryVo.setPageSize(10);
            }
            NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder().withIndices("elms_elasticsearch").withTypes("ElecLicenseMainData");
            BoolQueryBuilder boolQuery_1 = QueryBuilders.boolQuery();
            if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getDirectoryName())) {
                boolQuery_1.must(QueryBuilders.termsQuery("directoryName", elecLicenseMainQueryVo.getDirectoryName()));
            }
            if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getHolder())) {
                boolQuery_1.must(QueryBuilders.termsQuery("holder", elecLicenseMainQueryVo.getHolder()));
            }
            if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getIdentificateNumber())) {
                boolQuery_1.must(QueryBuilders.termsQuery("identificateNumber", elecLicenseMainQueryVo.getIdentificateNumber()));
            }
            if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getOrganOid())) {
                boolQuery_1.must(QueryBuilders.termsQuery("organOid", elecLicenseMainQueryVo.getOrganOid()));
            }
            boolQuery_1.must(QueryBuilders.termsQuery("licenseStatus", "4"));
            //注销
            if ("1".equals(elecLicenseMainQueryVo.getWhType())) {
                if (StringUtil.isNotEmpty(elecLicenseMainQueryVo.getLicenseStatus()) && !"4".equals(elecLicenseMainQueryVo.getLicenseStatus())) {
                    boolQuery_1.must(QueryBuilders.termsQuery("zxAuditFlag", elecLicenseMainQueryVo.getLicenseStatus()));
                } else {
                    boolQuery_1.mustNot(QueryBuilders.termsQuery("zxAuditFlag", new String[]{"6", "7", "8"}));
                }

                //吊销
            } else if ("2".equals(elecLicenseMainQueryVo.getWhType())) {
                if (StringUtil.isNotEmpty(elecLicenseMainQueryVo.getLicenseStatus()) && !"4".equals(elecLicenseMainQueryVo.getLicenseStatus())) {
                    boolQuery_1.must(QueryBuilders.termsQuery("dxAuditFlag", elecLicenseMainQueryVo.getLicenseStatus()));
                } else {
                    boolQuery_1.mustNot(QueryBuilders.termsQuery("dxAuditFlag", new String[]{"12", "13", "14"}));
                }
                //延续
            } else if ("3".equals(elecLicenseMainQueryVo.getWhType())) {
                if (StringUtil.isNotEmpty(elecLicenseMainQueryVo.getLicenseStatus()) && !"4".equals(elecLicenseMainQueryVo.getLicenseStatus())) {
                    boolQuery_1.must(QueryBuilders.termsQuery("yxAuditFlag", elecLicenseMainQueryVo.getLicenseStatus()));
                } else {
                    boolQuery_1.mustNot(QueryBuilders.termsQuery("yxAuditFlag", new String[]{"15", "16", "17"}));
                }
                //纠错
            } else if ("4".equals(elecLicenseMainQueryVo.getWhType())) {
                if (StringUtil.isNotEmpty(elecLicenseMainQueryVo.getLicenseStatus()) && !"4".equals(elecLicenseMainQueryVo.getLicenseStatus())) {
                    boolQuery_1.must(QueryBuilders.termsQuery("jcAuditFlag", elecLicenseMainQueryVo.getLicenseStatus()));
                } else {
                    boolQuery_1.mustNot(QueryBuilders.termsQuery("yxAuditFlag", new String[]{"22", "23", "24", "25"}));
                }
            }
            if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getClaimOid())) {
                boolQuery_1.must(QueryBuilders.termsQuery("claimOid", elecLicenseMainQueryVo.getClaimOid()));
            }
            if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getLicenseType())) {
                boolQuery_1.must(QueryBuilders.termsQuery("licenseType", elecLicenseMainQueryVo.getLicenseType()));
            }

            // 设置类型
            NativeSearchQuery nativeSearchQuery = queryBuilder.withQuery(boolQuery_1)
                    // 排序 按 info_date 倒序
                    .withSort(new FieldSortBuilder("createTime").order(SortOrder.DESC))
                    // 分页
                    .withPageable(PageRequest.of(elecLicenseMainQueryVo.getPageNum(), elecLicenseMainQueryVo.getPageSize()))
                    .build();
            AggregatedPage<ElecLicenseMainData> secInfos = template.queryForPage(nativeSearchQuery, ElecLicenseMainData.class);
            if (secInfos != null) {
                return PageUtil.change(elecLicenseMainQueryVo.getPageNum() + 1, elecLicenseMainQueryVo.getPageSize(), (int) secInfos.getTotalElements(), secInfos.getContent());
            } else {
                return PageUtil.change(elecLicenseMainQueryVo.getPageNum() + 1, elecLicenseMainQueryVo.getPageSize(), null, null);
            }
        }
        return null;
    }

    @Override
    public ElecLicenseMainData viewElecLicenseMainDataByOid(String elecLicenseOid) {
        GetQuery getQuery = new GetQuery();
        getQuery.setId(elecLicenseOid);
        ElecLicenseMainData data = template.queryForObject(getQuery, ElecLicenseMainData.class);
        return data;
    }

    @Override
    public String deleteElecLicenseMainDataIndex(String indexName) {
        template.deleteIndex(indexName);
        return null;
    }

    @Override
    public Page<ElecLicenseMainData> queryAllAuditElecLicenseMainLice(ElecLicenseMainData
                                                                              elecLicenseMainData, String auditObj, int pageNumber, int pageSize) throws Exception {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        if (null != elecLicenseMainData) {
            //固定条件 -开始
            //持有者证件号
            if (!StringUtils.isEmpty(elecLicenseMainData.getIdentificateNumber())) {
                boolQuery.must(QueryBuilders.matchQuery("identificateNumber", elecLicenseMainData.getIdentificateNumber()));
            }
            //目录名称
            if (!StringUtils.isEmpty(elecLicenseMainData.getDirectoryName())) {
                boolQuery.must(QueryBuilders.matchQuery("directoryName", elecLicenseMainData.getDirectoryName()));
            }
            //目录主键
            if (!StringUtils.isEmpty(elecLicenseMainData.getDirectoryOid())) {
                boolQuery.must(QueryBuilders.matchQuery("directoryOid", elecLicenseMainData.getDirectoryOid()));
            }
            if (!StringUtils.isEmpty(elecLicenseMainData.getClaimOid())) {
                boolQuery.must(QueryBuilders.matchQuery("claimOid", elecLicenseMainData.getClaimOid()));
            }
            //目录状态
            boolQuery.must(QueryBuilders.termsQuery("dirAuditStatus", new String[]{"3", "5", "7"}));
            //认领删除状态
            if (!StringUtils.isEmpty(elecLicenseMainData.getClaimDelFlag())) {
                boolQuery.must(QueryBuilders.matchQuery("claimDelFlag", elecLicenseMainData.getClaimDelFlag()));
            }
            //目录删除状态
            if (!StringUtils.isEmpty(elecLicenseMainData.getDirDelFlag())) {
                boolQuery.must(QueryBuilders.matchQuery("dirDelFlag", elecLicenseMainData.getDirDelFlag()));
            }
            //固定条件 -结束
            if (!StringUtils.isEmpty(auditObj)) {
                //新增审核
                if ("0".equals(auditObj)) {
                    boolQuery.must(QueryBuilders.matchQuery("licenseStatus", "1"));
                }
                //注销审核
                if ("1".equals(auditObj)) {
                    boolQuery.must(QueryBuilders.termsQuery("licenseStatus", new String[]{"6"}));
                }
                //吊销审核
                if ("2".equals(auditObj)) {
                    boolQuery.must(QueryBuilders.termsQuery("licenseStatus", "12"));
                }
                //纠错审核
                if ("4".equals(auditObj)) {
                    boolQuery.must(QueryBuilders.termsQuery("jcAuditFlag", "23"));
                }
                //延续审核
                if ("3".equals(auditObj)) {
                    boolQuery.must(QueryBuilders.termsQuery("licenseStatus", "15"));
                }
                //变更审核
                if ("5".equals(auditObj)) {
                    boolQuery.must(QueryBuilders.termsQuery("bgAuditFlag", "20"));
                    boolQuery.must(QueryBuilders.termsQuery("preLicenseStatus", "4"));
                }
                //年检审核
                if ("6".equals(auditObj)) {
                    boolQuery.must(QueryBuilders.termsQuery("autoAnnualFlag", "0"));
                    boolQuery.must(QueryBuilders.termsQuery("njAuditFlag", "9"));
                    boolQuery.must(QueryBuilders.termsQuery("njDelFlag", "N"));
                }
            }
        }
        //排序
        FieldSortBuilder fieldSort = new FieldSortBuilder("createTime").order(SortOrder.DESC);
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withPageable(pageable).withQuery(boolQuery).withSort(fieldSort).build();
        return template.queryForPage(searchQuery, ElecLicenseMainData.class);
    }

    @Override
    public String deleteElecLicenseMainDataByOid(String oid) {
        return template.delete(ElecLicenseMainData.class, oid);
    }

    @Override
    public ElecLicenseMainData getElecLicenseMainDataByElecOid(String elecOid) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder().withIndices("elms_elasticsearch").withTypes("ElecLicenseMainData");
        BoolQueryBuilder boolQuery_1 = QueryBuilders.boolQuery();
        boolQuery_1.must(QueryBuilders.termsQuery("elecLicenOid", elecOid));
        // 设置类型
        NativeSearchQuery nativeSearchQuery = queryBuilder.withQuery(boolQuery_1).build();
        List<ElecLicenseMainData> secInfos = template.queryForList(nativeSearchQuery, ElecLicenseMainData.class);
        if (secInfos != null && secInfos.size() > 0) {
            return secInfos.get(0);
        }
        return null;
    }

    @Override
    public ElecLicenseMainData getElecLicenseMainDataByOid(String oid) {
        GetQuery getQuery = new GetQuery();
        getQuery.setId(oid);
        ElecLicenseMainData data = template.queryForObject(getQuery, ElecLicenseMainData.class);
        return data;
    }

    @Override
    public PageInfo queryElecLicenseMainDataPwWhList(ElecLicenseMainQueryVo elecLicenseMainQueryVo) {
        if (elecLicenseMainQueryVo != null) {
            // 分页判断
            if (elecLicenseMainQueryVo.getPageNum() == null || elecLicenseMainQueryVo.getPageNum() < 0) {
                elecLicenseMainQueryVo.setPageNum(0);
            }
            // ES 首页=0
            if (elecLicenseMainQueryVo.getPageNum() > 0) {
                elecLicenseMainQueryVo.setPageNum(elecLicenseMainQueryVo.getPageNum() - 1);
            }
            if (elecLicenseMainQueryVo.getPageSize() == null || elecLicenseMainQueryVo.getPageSize() <= 0) {
                elecLicenseMainQueryVo.setPageSize(10);
            }
            NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder().withIndices("elms_elasticsearch").withTypes("ElecLicenseMainData");
            BoolQueryBuilder boolQuery_1 = QueryBuilders.boolQuery();
            if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getDirectoryName())) {
                boolQuery_1.must(QueryBuilders.termsQuery("directoryName", elecLicenseMainQueryVo.getDirectoryName()));
            }
            if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getHolder())) {
                boolQuery_1.must(QueryBuilders.termsQuery("holder", elecLicenseMainQueryVo.getHolder()));
            }
            if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getIdentificateNumber())) {
                boolQuery_1.must(QueryBuilders.termsQuery("identificateNumber", elecLicenseMainQueryVo.getIdentificateNumber()));
            }
            /*if(!StringUtils.isEmpty(elecLicenseMainQueryVo.getOrganOid())) {
                boolQuery_1.must(QueryBuilders.wildcardQuery("organOid", "*"+elecLicenseMainQueryVo.getOrganOid()+"*"));
            }*/
            if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getClaimOid())) {
                boolQuery_1.must(QueryBuilders.termsQuery("claimOid", elecLicenseMainQueryVo.getClaimOid()));
            }
            if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getLicenseType())) {
                boolQuery_1.must(QueryBuilders.termsQuery("licenseType", elecLicenseMainQueryVo.getLicenseType()));
            }
            if (StringUtil.isNotEmpty(elecLicenseMainQueryVo.getWhType())) {
                /*if ("5".equals(elecLicenseMainQueryVo.getWhType())) {// 变更
                    boolQuery_1.must(QueryBuilders.termsQuery("licenseStatus", new String[]{"4", "19", "20", "21"}));
                }*/
                if (StringUtils.isEmpty(elecLicenseMainQueryVo.getLicenseStatus()) || "4".equals(elecLicenseMainQueryVo.getLicenseStatus())) {
                    if ("1".equals(elecLicenseMainQueryVo.getWhType()) || "2".equals(elecLicenseMainQueryVo.getWhType())
                            || "5".equals(elecLicenseMainQueryVo.getWhType())) {// 注销、吊销、延续
                        boolQuery_1.mustNot(QueryBuilders.boolQuery()
                                .must(QueryBuilders.termsQuery("zxAuditFlag", new String[]{"6", "7"}))
                                .must(QueryBuilders.termsQuery("zxCancelFlag", "1"))
                        );
                        boolQuery_1.mustNot(QueryBuilders.boolQuery()
                                .must(QueryBuilders.termsQuery("dxAuditFlag", new String[]{"12", "13"}))
                                .must(QueryBuilders.termsQuery("dxCancelFlag", "1"))
                        );
                        if ("5".equals(elecLicenseMainQueryVo.getWhType())) {// 变更
                            boolQuery_1.must(QueryBuilders.termsQuery("licenseStatus", "4"));
                            boolQuery_1.mustNot(QueryBuilders.termsQuery("bgNextAuditFlag", new String[]{"2", "19", "20", "21"}));
                        }
                    }
                } else {
                    if ("1".equals(elecLicenseMainQueryVo.getWhType())) {// 注销
                        boolQuery_1.must(QueryBuilders.termsQuery("dxAuditFlag", elecLicenseMainQueryVo.getLicenseStatus()));
                        boolQuery_1.must(QueryBuilders.termsQuery("zxCancelFlag", "1"));
                    } else if ("2".equals(elecLicenseMainQueryVo.getWhType())) {// 吊销
                        boolQuery_1.must(QueryBuilders.termsQuery("dxAuditFlag", elecLicenseMainQueryVo.getLicenseStatus()));
                        boolQuery_1.must(QueryBuilders.termsQuery("zxCancelFlag", "1"));
                    } else if ("5".equals(elecLicenseMainQueryVo.getWhType())) {// 变更:查询状态不为签发存档的时候必须保证上版本为签发存档，以防做别的操作
                        boolQuery_1.must(QueryBuilders.termsQuery("bgNextAuditFlag", elecLicenseMainQueryVo.getLicenseStatus()));
                        boolQuery_1.must(QueryBuilders.termsQuery("licenseStatus", "4"));
                    }
                }
            }
            // 设置类型
            NativeSearchQuery nativeSearchQuery = queryBuilder.withQuery(boolQuery_1)
                    // 排序 按 info_date 倒序
                    .withSort(new FieldSortBuilder("createTime").order(SortOrder.DESC))
                    // 分页
                    .withPageable(PageRequest.of(elecLicenseMainQueryVo.getPageNum(), elecLicenseMainQueryVo.getPageSize()))
                    .build();
            AggregatedPage<ElecLicenseMainData> secInfos = template.queryForPage(nativeSearchQuery, ElecLicenseMainData.class);
            if (secInfos != null) {
                return PageUtil.change(elecLicenseMainQueryVo.getPageNum() + 1, elecLicenseMainQueryVo.getPageSize(), (int) secInfos.getTotalElements(), secInfos.getContent());
            } else {
                return PageUtil.change(elecLicenseMainQueryVo.getPageNum() + 1, elecLicenseMainQueryVo.getPageSize(), null, null);
            }
        }
        return null;
    }

    @Override
    public long queryElecLicenseMainDataAuditNum(ElecLicenseMainQueryVo elecLicenseMainQueryVo, String auditType) {
        if (elecLicenseMainQueryVo != null) {
            NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder().withIndices("elms_elasticsearch").withTypes("ElecLicenseMainData");
            BoolQueryBuilder boolQuery_1 = QueryBuilders.boolQuery();
            if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getDirectoryName())) {
                boolQuery_1.must(QueryBuilders.termsQuery("directoryName", elecLicenseMainQueryVo.getDirectoryName()));
            }
            if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getHolder())) {
                boolQuery_1.must(QueryBuilders.termsQuery("holder", elecLicenseMainQueryVo.getHolder()));
            }
            if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getIdentificateNumber())) {
                boolQuery_1.must(QueryBuilders.termsQuery("identificateNumber", elecLicenseMainQueryVo.getIdentificateNumber()));
            }
            if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getOrganOid())) {
                boolQuery_1.must(QueryBuilders.wildcardQuery("organOid", "*" + elecLicenseMainQueryVo.getOrganOid() + "*"));
            }
            if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getClaimOid())) {
                boolQuery_1.must(QueryBuilders.termsQuery("claimOid", elecLicenseMainQueryVo.getClaimOid()));
            }
            if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getLicenseType())) {
                boolQuery_1.must(QueryBuilders.termsQuery("licenseType", elecLicenseMainQueryVo.getLicenseType()));
            }
            if (StringUtil.isNotEmpty(auditType)) {
                // 新增
                if ("0".equals(auditType)) {
                    boolQuery_1.must(QueryBuilders.termsQuery("licenseStatus", "1"));
                    // 注销
                } else if ("1".equals(auditType)) {
                    boolQuery_1.must(QueryBuilders.termsQuery("zxAuditFlag", "6"));
                    boolQuery_1.must(QueryBuilders.termsQuery("zxCancelFlag", "1"));
                    // 吊销
                } else if ("2".equals(auditType)) {
                    boolQuery_1.must(QueryBuilders.termsQuery("dxAuditFlag", "12"));
                    boolQuery_1.must(QueryBuilders.termsQuery("dxCancelFlag", "1"));
                    // 纠错
                } else if ("3".equals(auditType)) {
                    boolQuery_1.must(QueryBuilders.termsQuery("jcAuditFlag", "23"));
                    boolQuery_1.must(QueryBuilders.termsQuery("jcDelFlag", "N"));
                    // 延续
                } else if ("4".equals(auditType)) {
                    boolQuery_1.must(QueryBuilders.termsQuery("yxAuditFlag", "15"));
                    boolQuery_1.must(QueryBuilders.termsQuery("yxDelFlag", "N"));
                    // 变更
                } else if ("5".equals(auditType)) {
                    boolQuery_1.must(QueryBuilders.termsQuery("bgAuditFlag", "20"));
                    boolQuery_1.must(QueryBuilders.termsQuery("licenseStatus", "4"));
                    // 年检
                } else if ("6".equals(auditType)) {
                    boolQuery_1.must(QueryBuilders.termsQuery("autoAnnualFlag", "0"));
                    boolQuery_1.must(QueryBuilders.termsQuery("njAuditFlag", "9"));
                    boolQuery_1.must(QueryBuilders.termsQuery("njDelFlag", "N"));
                }
            }
            // 设置类型
            NativeSearchQuery nativeSearchQuery = queryBuilder.withQuery(boolQuery_1)
                    .withSort(new FieldSortBuilder("createTime").order(SortOrder.DESC))
                    .withPageable(PageRequest.of(1, 999)).build();
            return template.count(nativeSearchQuery, ElecLicenseMainData.class);
        }
        return 0;
    }

    @Override
    public PageInfo queryElecLicenseMainDataAuditList(ElecLicenseMainQueryVo elecLicenseMainQueryVo) {
        if (elecLicenseMainQueryVo != null) {
            // 分页判断
            if (elecLicenseMainQueryVo.getPageNum() == null || elecLicenseMainQueryVo.getPageNum() < 0) {
                elecLicenseMainQueryVo.setPageNum(0);
            }
            // ES 首页=0
            if (elecLicenseMainQueryVo.getPageNum() > 0) {
                elecLicenseMainQueryVo.setPageNum(elecLicenseMainQueryVo.getPageNum() - 1);
            }
            if (elecLicenseMainQueryVo.getPageSize() == null || elecLicenseMainQueryVo.getPageSize() <= 0) {
                elecLicenseMainQueryVo.setPageSize(10);
            }
            NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder().withIndices("elms_elasticsearch").withTypes("ElecLicenseMainData");
            BoolQueryBuilder boolQuery_1 = QueryBuilders.boolQuery();
            if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getDirectoryName())) {
                boolQuery_1.must(QueryBuilders.termsQuery("directoryName", elecLicenseMainQueryVo.getDirectoryName()));
            }
            if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getHolder())) {
                boolQuery_1.must(QueryBuilders.termsQuery("holder", elecLicenseMainQueryVo.getHolder()));
            }
            if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getIdentificateNumber())) {
                boolQuery_1.must(QueryBuilders.termsQuery("identificateNumber", elecLicenseMainQueryVo.getIdentificateNumber()));
            }
            if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getClaimOid())) {
                boolQuery_1.must(QueryBuilders.termsQuery("claimOid", elecLicenseMainQueryVo.getClaimOid()));
            }
            if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getOrganOid())) {
                boolQuery_1.must(QueryBuilders.wildcardQuery("organOid", "*" + elecLicenseMainQueryVo.getOrganOid() + "*"));
            }
            if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getZxAuditFlag())) {// 注销
                boolQuery_1.must(QueryBuilders.termsQuery("zxAuditFlag", "6"));
                boolQuery_1.must(QueryBuilders.termsQuery("zxCancelFlag", "1"));
            } else if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getDxAuditFlag())) {// 吊销
                boolQuery_1.must(QueryBuilders.termsQuery("dxAuditFlag", "12"));
                boolQuery_1.must(QueryBuilders.termsQuery("dxCancelFlag", "1"));
            } else if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getYxAuditFlag())) {// 延续
                boolQuery_1.must(QueryBuilders.termsQuery("yxAuditFlag", "15"));
                boolQuery_1.must(QueryBuilders.matchQuery("yxDelFlag", "N"));
            } else if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getJcAuditFlag())) {// 纠错
                // modify by chenyj on 2020/3/15 修改term查询不到数据问题
                boolQuery_1.must(QueryBuilders.termsQuery("licenseStatus", "23"));
                boolQuery_1.must(QueryBuilders.matchQuery("jcDelFlag", "N"));
//                boolQuery_1.must(QueryBuilders.termsQuery("jcAuditFlag", "23"));
//                boolQuery_1.must(QueryBuilders.termsQuery("jcDelFlag", "N"));
            } else if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getBgAuditFlag())) {// 变更
                // 变更待审核(需要保证上一个版本为签发存档，以防在变更的时候走了注销或者吊销)
                boolQuery_1.must(QueryBuilders.termsQuery("bgAuditFlag", "20"));
                boolQuery_1.must(QueryBuilders.termsQuery("preLicenseStatus", "4"));
                boolQuery_1.mustNot(QueryBuilders.termsQuery("licenseStatus", new String[]{"4", "2"}));
            } else if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getNjAuditFlag())) {// 年检
                boolQuery_1.must(QueryBuilders.termsQuery("autoAnnualFlag", "0"));
                boolQuery_1.must(QueryBuilders.termsQuery("njAuditFlag", "9"));
                boolQuery_1.must(QueryBuilders.termsQuery("njDelFlag", "N"));
            } else if ("1".equals(elecLicenseMainQueryVo.getLicenseStatus())) {// 新增审核
                boolQuery_1.must(QueryBuilders.termsQuery("licenseStatus", "1"));
            }
            if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getLicenseStatus())) {
                if (elecLicenseMainQueryVo.getLicenseStatus().contains(",")) {
                    String[] statusArr = elecLicenseMainQueryVo.getLicenseStatus().split(",");
                    boolQuery_1.must(QueryBuilders.termsQuery("licenseStatus", statusArr));
                } else {
                    boolQuery_1.must(QueryBuilders.termsQuery("licenseStatus", elecLicenseMainQueryVo.getLicenseStatus()));
                }
            }
            // 设置类型
            NativeSearchQuery nativeSearchQuery = queryBuilder.withQuery(boolQuery_1)
                    // 排序 按 info_date 倒序
                    .withSort(new FieldSortBuilder("createTime").order(SortOrder.DESC))
                    // 分页
                    .withPageable(PageRequest.of(elecLicenseMainQueryVo.getPageNum(), elecLicenseMainQueryVo.getPageSize()))
                    .build();
            AggregatedPage<ElecLicenseMainData> secInfos = template.queryForPage(nativeSearchQuery, ElecLicenseMainData.class);
            if (secInfos != null) {
                return PageUtil.change(elecLicenseMainQueryVo.getPageNum() + 1, elecLicenseMainQueryVo.getPageSize(), (int) secInfos.getTotalElements(), secInfos.getContent());
            } else {
                return PageUtil.change(elecLicenseMainQueryVo.getPageNum() + 1, elecLicenseMainQueryVo.getPageSize(), null, null);
            }
        }
        return null;
    }

    @Override
    public PageInfo queryElecLicenseQueryList(EsElecLicneseQueryVo elecLicenseMainQueryVo) {
        if (elecLicenseMainQueryVo != null) {
            // 分页判断
            if (elecLicenseMainQueryVo.getPageNum() == null || elecLicenseMainQueryVo.getPageNum() < 0) {
                elecLicenseMainQueryVo.setPageNum(0);
            }
            // ES 首页=0
            if (elecLicenseMainQueryVo.getPageNum() > 0) {
                elecLicenseMainQueryVo.setPageNum(elecLicenseMainQueryVo.getPageNum() - 1);
            }
            if (elecLicenseMainQueryVo.getPageSize() == null || elecLicenseMainQueryVo.getPageSize() <= 0) {
                elecLicenseMainQueryVo.setPageSize(10);
            }
            NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder().withIndices("elms_elasticsearch").withTypes("ElecLicenseMainData");
            BoolQueryBuilder boolQuery_1 = QueryBuilders.boolQuery();
            if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getDirectoryName())) {
                boolQuery_1.must(QueryBuilders.matchQuery("directoryName", elecLicenseMainQueryVo.getDirectoryName()));
            }
            if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getHolder())) {
                boolQuery_1.must(QueryBuilders.matchQuery("holder", elecLicenseMainQueryVo.getHolder()));
            }
            if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getHolderType())) {
                boolQuery_1.must(QueryBuilders.termsQuery("directoryObj", elecLicenseMainQueryVo.getHolderType()));
            }
            if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getIdentificateNumber())) {
                boolQuery_1.must(QueryBuilders.termsQuery("identificateNumber", elecLicenseMainQueryVo.getIdentificateNumber()));
            }
            if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getLicenseStatus())) {
                // 证照状态多的情况
                String licenseStatus = elecLicenseMainQueryVo.getLicenseStatus();
                if (licenseStatus.contains(",")) {
                    String[] licenseStatusArr = licenseStatus.split(",");
                    for (String item : licenseStatusArr) {
                        boolQuery_1.should(QueryBuilders.termQuery("licenseStatus", item));
                    }
                } else {
                    boolQuery_1.must(QueryBuilders.termsQuery("licenseStatus", elecLicenseMainQueryVo.getLicenseStatus()));
                }
            }
            if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getLicenseClassifyStatus())) {
                boolQuery_1.must(QueryBuilders.termsQuery("licenseClassifyStatus", elecLicenseMainQueryVo.getLicenseClassifyStatus()));
            }
            if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getDirectoryOid())) {
                boolQuery_1.must(QueryBuilders.termsQuery("directoryOid", elecLicenseMainQueryVo.getDirectoryOid()));
            }
            if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getDirAuditPass())) {
                String dirAuditStatus = elecLicenseMainQueryVo.getDirAuditPass();
                if (dirAuditStatus.contains(",")) {
                    String[] dirAuditStatusArr = dirAuditStatus.split(",");
                    for (String item : dirAuditStatusArr) {
                        boolQuery_1.must(QueryBuilders.termsQuery("dirAuditStatus", item));
                    }
                } else {
                    boolQuery_1.must(QueryBuilders.termsQuery("dirAuditStatus", elecLicenseMainQueryVo.getDirAuditPass()));
                }
            }

            if (!StringUtils.isEmpty(elecLicenseMainQueryVo.getOrganOid())) {
                // 认领部门Id === claimOrganOid（下面的organOid测试使用）
                boolQuery_1.must(QueryBuilders.termsQuery("organOid", elecLicenseMainQueryVo.getOrganOid()));
            }

            boolQuery_1.must(QueryBuilders.termsQuery("claimDelFlag.keyword", "N"));
            boolQuery_1.must(QueryBuilders.termsQuery("dirDelFlag.keyword", "N"));
            boolQuery_1.must(QueryBuilders.termQuery("dirAbleFlag.keyword", "Y"));
            // 设置类型
            NativeSearchQuery nativeSearchQuery = queryBuilder.withQuery(boolQuery_1)
                    // 排序 按 info_date 倒序
                    .withSort(new FieldSortBuilder("createTime").order(SortOrder.DESC))
                    // 分页
                    .withPageable(PageRequest.of(elecLicenseMainQueryVo.getPageNum(), elecLicenseMainQueryVo.getPageSize()))
                    .build();
            AggregatedPage<ElecLicenseMainData> secInfos = template.queryForPage(nativeSearchQuery, ElecLicenseMainData.class);
            if (secInfos != null) {
                return PageUtil.change(elecLicenseMainQueryVo.getPageNum() + 1, elecLicenseMainQueryVo.getPageSize(), (int) secInfos.getTotalElements(), secInfos.getContent());
            } else {
                return PageUtil.change(elecLicenseMainQueryVo.getPageNum() + 1, elecLicenseMainQueryVo.getPageSize(), null, null);
            }
        }
        return null;
    }
}
