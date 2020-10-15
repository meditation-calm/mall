package cn.fjcpc.search.controller;

import cn.fjcpc.search.service.ItemSolrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ItemSolrController {
    @Autowired
    private ItemSolrService itemSolrService;

    /**
     * 向solr添加商品查询信息
     * @param map
     * @return
     */
    @RequestMapping(value = "/itemSolr/add",method = RequestMethod.POST)
    @ResponseBody
    public int addItemToSolr(@RequestBody Map<String,Object> map) {
        try {
            return itemSolrService.addItemToSolr((LinkedHashMap) map.get("item"),map.get("desc").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 删除solr中商品信息
     * @param ids
     * @return
     */
    @RequestMapping(value = "/itemSolr/delete",method = RequestMethod.POST)
    @ResponseBody
    public int deleteItemFromSolr(@RequestBody List<String> ids) {
        try {
            return itemSolrService.deleteItemFromSolr(ids);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
