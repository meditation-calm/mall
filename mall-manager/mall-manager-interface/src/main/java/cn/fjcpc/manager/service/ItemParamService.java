package cn.fjcpc.manager.service;

import cn.fjcpc.common.anotation.ServiceExeProcess;
import cn.fjcpc.common.pojo.DataGridResult;
import cn.fjcpc.manager.pojo.TbItemParam;

public interface ItemParamService {

    /**
     * 分页查询商品规格参数
     * @param page
     * @param rows
     * @return
     */
    DataGridResult selectItemParamList(int page, int rows);

    /**
     * 批量删除商品规格参数
     * @param ids
     * @return
     */
    boolean deleteItemParam(String ids);

    /**
     * 根据商品类目ID查询
     * @param id
     * @return
     */
    TbItemParam queryItemParamByItemCatId(Long id);

    /**
     * 新增商品规格参数模板
     * @param itemCatId
     * @param paramData
     * @return
     */
    boolean addItemParam(Long itemCatId, String paramData);

}
