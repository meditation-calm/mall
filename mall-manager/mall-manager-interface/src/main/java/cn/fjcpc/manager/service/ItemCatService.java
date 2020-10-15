package cn.fjcpc.manager.service;

import cn.fjcpc.common.pojo.EasyUITree;
import cn.fjcpc.common.pojo.Result;
import cn.fjcpc.manager.dto.ItemCatMenu;
import cn.fjcpc.manager.pojo.TbItemCat;

import java.util.List;

public interface ItemCatService {

    /**
     * 查询所有商品类目表结构信息-->封装于ItemCatMenu对象
     * @return
     */
    Result<List<Object>> selectItemCatAll();

    /**
     * 根据父类目ID获取商品类目-->封装于EasyUITree树形结构
     * @param pid
     * @return
     */
    List<EasyUITree> getItemCatList2EasyUITree(Long pid);

    /**
     * 根据父类目ID获取商品类目-->封装于TbItemCat对象
     * @param pid
     * @return
     */
    List<TbItemCat> getItemCatList2TbItemCat(Long pid);

    /**
     * 根据ID查询单个商品类目
     * @param id
     * @return
     */
    TbItemCat getItemCat(Long id);

}
