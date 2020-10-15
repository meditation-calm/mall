package cn.fjcpc.manager.service.impl;

import cn.fjcpc.manager.mapper.TbItemParamItemMapper;
import cn.fjcpc.manager.pojo.TbItemParamItem;
import cn.fjcpc.manager.pojo.TbItemParamItemExample;
import cn.fjcpc.manager.service.ItemParamItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemParamItemServiceImpl implements ItemParamItemService {
    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;

    @Override
    public TbItemParamItem getItemParamItemById(Long id) {
        TbItemParamItemExample example = new TbItemParamItemExample();
        example.createCriteria().andItemIdEqualTo(id);
        List<TbItemParamItem> itemParamItems = itemParamItemMapper.selectByExampleWithBLOBs(example);
        if (itemParamItems!=null && itemParamItems.size()>0) {
            return itemParamItems.get(0);
        }
        return null;
    }
}
