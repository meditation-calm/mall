package cn.fjcpc.search.service;

import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;
import java.util.Map;

public interface ItemSearchService {

    /**
     *  初始化solr数据
     */
    void init() throws IOException, SolrServerException;

    /**
     * portal的搜索功能--从solr中获取商品内容
     * @param query
     * @param page
     * @param rows
     * @return
     */
    Map<String,Object> getSolrItems(String query,int page,int rows) throws IOException, SolrServerException;

}
