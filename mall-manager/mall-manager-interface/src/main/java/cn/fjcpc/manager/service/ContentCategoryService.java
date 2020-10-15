package cn.fjcpc.manager.service;

import cn.fjcpc.common.pojo.EasyUITree;
import cn.fjcpc.common.pojo.Result;
import cn.fjcpc.manager.pojo.TbContentCategory;

import java.util.List;
import java.util.Map;

public interface ContentCategoryService {

    /**
     * 根据父类目ID查询内容分类
     * @param id
     * @return
     */
    List<EasyUITree> getContentCategoryListByParentId(Long id);

    /**
     * 内容分类商品新增
     * @param contentCategory
     * @return
     */
    Result<Map<String,Object>> addContentCategory(TbContentCategory contentCategory);

    /**
     * 根据主键ID对内容分类节点重命名
     * @param contentCategory
     * @return
     */
    Result updateContentCategoryNameById(TbContentCategory contentCategory);

    /**
     * 根据主键删除内容分类管理节点
     * @param contentCategory
     * @return
     */
    Result deleteContentCategoryById(TbContentCategory contentCategory);

    /**
     * 根据名称查询内容分类
     * @param name
     * @return
     */
    TbContentCategory getContentCategoryByName(String name);

}
