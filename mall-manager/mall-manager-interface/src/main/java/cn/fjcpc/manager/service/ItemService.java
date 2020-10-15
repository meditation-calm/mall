package cn.fjcpc.manager.service;

import cn.fjcpc.common.pojo.DataGridResult;
import cn.fjcpc.manager.pojo.TbItem;

import java.util.Arrays;
import java.util.List;


public interface ItemService {
    /**
     * 正常/下架/删除
     */
    List<String> Item_Request_Uri = Arrays.asList("reshelf","instock","delete");

    Byte[] Item_Status = {1,2,3};

    /**
     * 查询商品列表
     * @param page  当前页
     * @param rows  页显示条数
     * @return
     */
    DataGridResult getItemList(int page,int rows);

    /**
     * 修改商品状态
     * @return
     */
    int updateItem(String ids,String uri);

    /**
     * 根据商品ID查询商品
     * @param id
     * @return
     */
    TbItem getItemById(Long id);

    /**
     * 新增商品(包含商品表和商品描述表)
     * @param item
     * @param desc
     * @return
     */
    boolean saveItem(TbItem item,String desc,String paramData);

    /**
     * 根据状态获取商品
     * @param status
     * @return
     */
    List<TbItem> getItemByStatus(byte status);

}
