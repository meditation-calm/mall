package cn.fjcpc.item.controller;

import cn.fjcpc.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{id}.html")
    public String showItemDetails(@PathVariable("id") Long id, Model model){
        model.addAttribute("item",itemService.getItemById(id));
        return "item";
    }

}
