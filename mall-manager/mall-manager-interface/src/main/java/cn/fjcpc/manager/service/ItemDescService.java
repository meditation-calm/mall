package cn.fjcpc.manager.service;

import cn.fjcpc.manager.pojo.TbItemDesc;

public interface ItemDescService {

    /**
     * 根据商品ID查询商品描述
     * @param id  主键ID
     * @return
     */
    TbItemDesc getItemDescById(Long id);

}
