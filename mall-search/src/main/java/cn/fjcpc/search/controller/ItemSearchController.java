package cn.fjcpc.search.controller;

import cn.fjcpc.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class ItemSearchController {
    @Autowired
    private ItemSearchService itemSearchService;

    /**
     * 商品搜索功能
     * @param model
     * @param q
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "search.html",method = RequestMethod.GET)
    public String searchItem(Model model, String q, @RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "12") int rows){
        try {
            q = new String(q.getBytes(),"utf-8");
            Map<String, Object> map = itemSearchService.getSolrItems(q, page, rows);
            model.addAttribute("query",q);
            model.addAttribute("itemList",map.get("itemList"));
            model.addAttribute("page",page);
            model.addAttribute("totalPages",map.get("totalPages"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "search";
    }

    /**
     * 初始化 solr 数据
     * @return
     */
    @RequestMapping(value = "/solr/init",produces = "text/html;charset=utf-8")
    @ResponseBody
    public String init(){
        long start = System.currentTimeMillis();
        try {
            itemSearchService.init();
            long end = System.currentTimeMillis();
            return "初始化时间为："+(end-start)/1000.0+"秒";
        } catch (Exception e) {
            e.printStackTrace();
            return "初始化失败";
        }
    }

}
