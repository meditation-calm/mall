package cn.fjcpc.manager.controller;

import cn.fjcpc.common.pojo.DataGridResult;
import cn.fjcpc.common.pojo.Result;
import cn.fjcpc.manager.dto.ItemParamDto;
import cn.fjcpc.manager.dto.front.ItemParamDtoUtils;
import cn.fjcpc.manager.pojo.TbItemCat;
import cn.fjcpc.manager.pojo.TbItemParam;
import cn.fjcpc.manager.pojo.TbItemParamItem;
import cn.fjcpc.manager.service.ItemCatService;
import cn.fjcpc.manager.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 *  商品参数信息
 */
@Controller
public class ItemParamController {
    @Autowired
    private ItemParamService itemParamService;
    @Autowired
    private ItemCatService itemCatService;

    /**
     * 规格参数中新增商品参数
     * @param id
     * @param paramData
     * @return
     */
    @RequestMapping(value = "/item/param/save/{itemCatId}",method = RequestMethod.POST)
    @ResponseBody
    public Result addItemParam(@PathVariable("itemCatId") Long id,String paramData){
        Result result = new Result(200);
        if (!itemParamService.addItemParam(id,paramData)) {
            result.setMessage("添加商品规格参数失败！");
            result.setStatus(0);
        }
        return result;
    }

    /**
     * 未选择商品类目添加商品参数
     * @return
     */
    @RequestMapping(value = "/item/param/save",method = RequestMethod.POST)
    @ResponseBody
    public Result addItemParamNotParam(){
        return new Result("未选择商品类目！",0);
    }

    /**
     * 商品类目选择按钮
     * @param itemCatId
     * @return
     */
    @RequestMapping(value = "/item/param/query/itemcatid/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Result<TbItemParam> getItemParam(@PathVariable("id") Long itemCatId){
        Result<TbItemParam> result = new Result<>();
        TbItemParam itemParam = itemParamService.queryItemParamByItemCatId(itemCatId);
        if (itemParam != null) {
            result.setStatus(200);
            result.setData(itemParam);
        }
        return result;
    }

    /**
     * 批量删除商品规格参数
     * @param ids
     * @return
     */
    @RequestMapping(value = "/item/param/delete",method = RequestMethod.POST)
    @ResponseBody
    public Result deleteItemParams(String ids){
        Result result = new Result(200);
        if (!itemParamService.deleteItemParam(ids)) {
            result.setMessage("可能原因：删除数据不存在！");
        }
        return result;
    }

    /**
     * 分页查询商品规格参数列表
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/item/param/list",method = RequestMethod.GET)
    @ResponseBody
    public DataGridResult queryItemParamList(@RequestParam(defaultValue = "1") int page,
                                             @RequestParam(defaultValue = "30") int rows){
        List<ItemParamDto> itemParamDtos = new ArrayList<>();
        DataGridResult dataGridResult = itemParamService.selectItemParamList(page, rows);
        for (TbItemParam itemParam:(List<TbItemParam>) dataGridResult.getRows()) {
            ItemParamDto itemParamDto = ItemParamDtoUtils.TbItemParam2ItemParamDto(itemParam);
            //查询并填充商品类目名称属性
            TbItemCat itemCat = itemCatService.getItemCat(itemParam.getItemCatId());
            itemParamDto.setItemCatName(itemCat.getName());
            itemParamDtos.add(itemParamDto);
        }
        dataGridResult.setRows(itemParamDtos);
        return dataGridResult;
    }

    @RequestMapping(value = "/rest/item/param/item/query/{itemId}",method = RequestMethod.GET)
    @ResponseBody
    public Result<TbItemParamItem> queryOneItemParam(@PathVariable("itemId") int id){
        Result<TbItemParamItem> result = new Result<>();
        result.setStatus(200);
        TbItemParamItem itemParamItem = new TbItemParamItem();
        itemParamItem.setId(2L);
        String str = "[{"+"'group'"+":"+"'主体'"+","+"'params'"+":"+"[{"+"'k'"+":"+"'品牌'"+","+"'v'"+":"+"'锤子'"+"}]}]";
        itemParamItem.setParamData(str);
        result.setData(itemParamItem);
        return result;
    }

}
