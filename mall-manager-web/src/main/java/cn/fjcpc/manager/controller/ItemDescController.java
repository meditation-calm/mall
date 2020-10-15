package cn.fjcpc.manager.controller;

import cn.fjcpc.common.pojo.Result;
import cn.fjcpc.manager.pojo.TbItemDesc;
import cn.fjcpc.manager.service.ItemDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品描述处理
 */
@Controller
public class ItemDescController {
    @Autowired
    private ItemDescService itemDescService;

    @RequestMapping(value = "/rest/item/query/item/desc/{itemId}",method = RequestMethod.GET)
    @ResponseBody
    public Result<TbItemDesc> queryItemDesc(@PathVariable("itemId") Long id){
        Result<TbItemDesc> result = new Result<>();
        TbItemDesc tbItemDesc;
        if((tbItemDesc = itemDescService.getItemDescById(id))!=null) {
            result.setStatus(200);
            result.setData(tbItemDesc);
        }
        return result;
    }

}
