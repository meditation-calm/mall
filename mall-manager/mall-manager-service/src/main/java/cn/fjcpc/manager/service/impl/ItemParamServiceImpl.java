package cn.fjcpc.manager.service.impl;

import cn.fjcpc.common.exception.MallException;
import cn.fjcpc.common.pojo.DataGridResult;
import cn.fjcpc.manager.mapper.TbItemParamMapper;
import cn.fjcpc.manager.pojo.TbItemParam;
import cn.fjcpc.manager.pojo.TbItemParamExample;
import cn.fjcpc.manager.service.ItemParamService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ItemParamServiceImpl implements ItemParamService {
    @Autowired
    private TbItemParamMapper itemParamMapper;

    @Override
    public DataGridResult selectItemParamList(int page, int rows) {
        PageHelper.startPage(page,rows);
        List<TbItemParam> tbItemParams = itemParamMapper.selectByExampleWithBLOBs(new TbItemParamExample());
        PageInfo<TbItemParam> pageInfo = new PageInfo<>(tbItemParams);

        DataGridResult dataGridResult = new DataGridResult();
        dataGridResult.setTotal(pageInfo.getTotal());
        dataGridResult.setRows(pageInfo.getList());
        return dataGridResult;
    }

    @Override
    public boolean deleteItemParam(String ids) {
        boolean flag = false;
        String[] strings = ids.split(",");
        int index = 0;
        for (String id:strings) {
            index += itemParamMapper.deleteByPrimaryKey(Long.parseLong(id));
        }
        if (index != strings.length) {
            throw new MallException("批量删除规格参数失败！");
        }
        return flag;
    }

    @Override
    public TbItemParam queryItemParamByItemCatId(Long id) {
        TbItemParam itemParam = null;
        TbItemParamExample example = new TbItemParamExample();
        example.createCriteria().andItemCatIdEqualTo(id);
        List<TbItemParam> tbItemParams = itemParamMapper.selectByExampleWithBLOBs(example);
        if (tbItemParams != null && !tbItemParams.isEmpty()) {
            itemParam = tbItemParams.get(0);
        }
        return itemParam;
    }

    @Override
    public boolean addItemParam(Long itemCatId, String paramData) {
        TbItemParam itemParam = new TbItemParam();
        Date date = new Date();
        if (itemCatId!=null) {
            itemParam.setItemCatId(itemCatId);
        }
        itemParam.setParamData(paramData);
        itemParam.setCreated(date);
        itemParam.setUpdated(date);
        TbItemParamExample example = new TbItemParamExample();
        example.createCriteria().andItemCatIdEqualTo(itemCatId);
        List<TbItemParam> tbItemParams = itemParamMapper.selectByExample(example);
        if (tbItemParams !=null && !tbItemParams.isEmpty()) {
            return false;
        }
        return itemParamMapper.insertSelective(itemParam) == 1;
    }

}
