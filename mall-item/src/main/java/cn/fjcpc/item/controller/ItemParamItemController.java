package cn.fjcpc.item.controller;

import cn.fjcpc.item.service.ItemParamItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemParamItemController {
    @Autowired
    private ItemParamItemService itemParamItemService;

    @RequestMapping(value = "/item/param/{id}.html",produces = "text/html;charset=utf-8",method = RequestMethod.GET)
    @ResponseBody
    public String showItemParamItem(@PathVariable("id") Long id){
        return itemParamItemService.getItemParamTable(id);
    }

}
