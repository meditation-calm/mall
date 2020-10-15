package cn.fjcpc.manager.controller;

import cn.fjcpc.common.pojo.EasyUITree;
import cn.fjcpc.manager.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 *  商品类目控制器
 */
@Controller
@ResponseBody
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;

    /**
     * 选择类目，获得同级的商品类目信息
     * @param id
     * @return
     */
    @RequestMapping("/item/cat/list")
    public List<EasyUITree> showItemCatList(@RequestParam(defaultValue = "0") Long id){
        return itemCatService.getItemCatList2EasyUITree(id);
    }

}
