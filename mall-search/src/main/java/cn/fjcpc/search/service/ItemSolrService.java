package cn.fjcpc.search.service;

import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ItemSolrService {

    /**
     * 向solr服务器添加item数据
     * @param map
     * @param desc
     * @return
     */
    int addItemToSolr(Map<String,Object> map, String desc) throws IOException, SolrServerException;

    /**
     * 从solr中删除Item数据
     * @param ids
     * @return
     */
    int deleteItemFromSolr(List<String> ids) throws IOException, SolrServerException;

}
