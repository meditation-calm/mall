package cn.fjcpc.search.service.impl;

import cn.fjcpc.manager.service.ItemCatService;
import cn.fjcpc.search.service.ItemSolrService;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ItemSolrServiceImpl implements ItemSolrService {
    @Autowired
    private ItemCatService itemCatService;
    @Autowired
    private HttpSolrClient solrClient;

    @Override
    public int addItemToSolr(Map<String,Object> map, String desc) throws IOException, SolrServerException {
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id",map.get("id"));
        doc.addField("item_title",map.get("title"));
        doc.addField("item_sell_point", map.get("sellPoint"));
        doc.addField("item_price",map.get("price"));
        doc.addField("item_image", map.get("image"));
        doc.addField("item_category_name",itemCatService.getItemCat(Long.parseLong(map.get("cid").toString())).getName());
        doc.addField("item_desc",desc);
        Date date = new Date((Long) map.get("updated"));
        doc.addField("item_updated",date);
        solrClient.add(doc);
        UpdateResponse response = solrClient.commit();
        if (response.getStatus()==0){
            return 1;
        }
        return 0;
    }

    @Override
    public int deleteItemFromSolr(List<String> ids) throws IOException, SolrServerException {
        UpdateResponse response = solrClient.deleteById(ids);
        if (response.getStatus()==0){
            return 1;
        }
        return 0;
    }
}
