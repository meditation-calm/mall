package cn.fjcpc.search.service.impl;

import cn.fjcpc.manager.dto.SearchItemDto;
import cn.fjcpc.manager.pojo.TbItem;
import cn.fjcpc.manager.pojo.TbItemCat;
import cn.fjcpc.manager.pojo.TbItemDesc;
import cn.fjcpc.manager.service.ItemCatService;
import cn.fjcpc.manager.service.ItemDescService;
import cn.fjcpc.manager.service.ItemService;
import cn.fjcpc.search.service.ItemSearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemSearchServiceImpl implements ItemSearchService {
    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemCatService itemCatService;
    @Autowired
    private ItemDescService itemDescService;
    @Autowired
    private HttpSolrClient solrClient;

    @Override
    public void init() throws IOException, SolrServerException {
        List<TbItem> itemList = itemService.getItemByStatus((byte) 1);
        for (TbItem item:itemList) {
            TbItemCat itemCat = itemCatService.getItemCat(item.getCid());
            TbItemDesc itemDesc = itemDescService.getItemDescById(item.getId());

            SolrInputDocument doc = new SolrInputDocument();
            doc.addField("id",item.getId());
            doc.addField("item_title",item.getTitle());
            doc.addField("item_sell_point",item.getSellPoint());
            doc.addField("item_price",item.getPrice());
            doc.addField("item_image",item.getImage());
            doc.addField("item_category_name",itemCat.getName());
            doc.addField("item_desc",itemDesc.getItemDesc());
            doc.addField("item_updated",item.getUpdated());

            solrClient.add(doc);
        }
        solrClient.commit();
    }

    @Override
    public Map<String, Object> getSolrItems(String query,int page,int rows) throws IOException, SolrServerException {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setStart((page-1)*rows);
        solrQuery.setRows(rows);
        solrQuery.setQuery("item_keywords:"+query);
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("item_title");
        solrQuery.setHighlightSimplePre("<span style='color:red'>");
        solrQuery.setHighlightSimplePost("</span>");
        //  最新状态在前
        solrQuery.setSort("item_updated", SolrQuery.ORDER.desc);
        QueryResponse response = solrClient.query(solrQuery);

        List<SearchItemDto> itemList = new ArrayList<>();
        SolrDocumentList documents = response.getResults();
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
        for (SolrDocument doc:documents) {
            SearchItemDto itemDto = new SearchItemDto();
            itemDto.setId(Long.parseLong(doc.getFieldValue("id").toString()));
            List<String> list = highlighting.get(doc.getFieldValue("id")).get("item_title");
            if (list != null && list.size()>0) {
                itemDto.setTitle(list.get(0));
            }else{
                itemDto.setTitle(doc.getFieldValue("item_title").toString());
            }
            if(doc.getFieldValue("item_sell_point") != null) {
                itemDto.setSellPoint(doc.getFieldValue("item_sell_point").toString());
            }
            itemDto.setPrice((Long) doc.getFieldValue("item_price"));
            Object image = doc.getFieldValue("item_image");
            itemDto.setImages(image==null||image.equals("") ? new String[1] : image.toString().split(","));
            itemList.add(itemDto);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("itemList",itemList);
        map.put("totalPages",documents.getNumFound()%rows==0 ? documents.getNumFound()/rows : documents.getNumFound()/rows+1);
        return map;
    }
}
