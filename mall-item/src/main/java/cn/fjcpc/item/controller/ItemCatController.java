package cn.fjcpc.item.controller;

import cn.fjcpc.manager.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;

    /**
     * 返回jsonp数据格式的所有菜单信息
     * @param callback
     * @return
     */
    @RequestMapping("/rest/itemcat/all")
    @ResponseBody
    public MappingJacksonValue showItemCatAll(String callback){
        MappingJacksonValue mjv = new MappingJacksonValue(itemCatService.selectItemCatAll());
        mjv.setJsonpFunction(callback);
        return mjv;
    }

}
