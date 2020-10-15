package cn.fjcpc.item.controller;

import cn.fjcpc.item.service.ItemDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemDescController {
    @Autowired
    private ItemDescService itemDescService;

    @RequestMapping(value = "/item/desc/{id}.html",method = RequestMethod.GET,produces = "text/html;charset=utf-8")
    @ResponseBody
    public String showItemDesc(@PathVariable("id") Long id) {
        return itemDescService.getItemDescById(id);
    }


}
