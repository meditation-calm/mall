package cn.fjcpc.manager.service.impl;

import cn.fjcpc.manager.mapper.TbItemDescMapper;
import cn.fjcpc.manager.pojo.TbItemDesc;
import cn.fjcpc.manager.service.ItemDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemDescServiceImpl implements ItemDescService {
    @Autowired
    private TbItemDescMapper itemDescMapper;

    @Override
    public TbItemDesc getItemDescById(Long id) {
        return itemDescMapper.selectByPrimaryKey(id);
    }
}
