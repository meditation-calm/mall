package cn.fjcpc.item.service;

import cn.fjcpc.manager.dto.SearchItemDto;

public interface ItemService {

    /**
     * 查看具体商品信息
     * @param id
     * @return
     */
    SearchItemDto getItemById(Long id);

}
