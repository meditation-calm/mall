package cn.fjcpc.manager.controller;

import cn.fjcpc.common.pojo.DataGridResult;
import cn.fjcpc.common.pojo.Result;
import cn.fjcpc.manager.pojo.TbItem;
import cn.fjcpc.manager.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 *  商品列表信息
 */
@Controller
@ResponseBody
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/item/save",method = RequestMethod.POST)
    public Result saveItem(TbItem item,@RequestParam(value = "desc",required = false) String desc,
                           String itemParams){
        Result result = new Result("新增商品成功！",200);
        if(item.getCid()==null || !itemService.saveItem(item,desc,itemParams)) {
            result.setStatus(0);
            result.setMessage("新增商品失败！");
        }
        return result;
    }

    /**
     * 查询商品列表数据
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/item/list",method = RequestMethod.GET)
    public DataGridResult getItemList(@RequestParam(defaultValue = "1") int page,
                                      @RequestParam(defaultValue = "30") int rows){
        return itemService.getItemList(page,rows);
    }

    /**
     *  修改商品状态
     * @param ids
     * @param uri
     * @return
     */
    @RequestMapping(value = "/rest/item/{operate}",method = RequestMethod.POST)
    public Result updateItem(String ids,@PathVariable("operate") String uri){
        Result result = new Result();
        if(itemService.updateItem(ids, uri) == 1) {
            result.setStatus(200);
        }
        return result;
    }

    @RequestMapping("/rest/page/item-edit")
    public String showItemEdit(){
        return "item-edit";
    }

}
