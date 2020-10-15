package cn.fjcpc.manager.dto.front;

import cn.fjcpc.manager.dto.ItemParamDto;
import cn.fjcpc.manager.pojo.TbItemParam;

public class ItemParamDtoUtils {

    public static ItemParamDto TbItemParam2ItemParamDto(TbItemParam itemParam) {
        ItemParamDto itemParamDto = new ItemParamDto();
        itemParamDto.setId(itemParam.getId());
        if (itemParam.getItemCatId()!=null) {
            itemParamDto.setItemCatId(itemParam.getItemCatId());
        }
        if (!itemParam.getParamData().isEmpty() && itemParam.getParamData()!=null) {
            itemParamDto.setParamData(itemParam.getParamData());
        }
        if (itemParam.getCreated()!=null) {
            itemParamDto.setCreated(itemParam.getCreated());
        }
        if (itemParam.getUpdated()!=null) {
            itemParamDto.setUpdated(itemParam.getUpdated());
        }
        return itemParamDto;
    }

}
