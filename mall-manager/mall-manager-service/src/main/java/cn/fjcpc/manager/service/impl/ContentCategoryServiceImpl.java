package cn.fjcpc.manager.service.impl;

import cn.fjcpc.common.exception.MallException;
import cn.fjcpc.common.pojo.EasyUITree;
import cn.fjcpc.common.pojo.Result;
import cn.fjcpc.common.utils.IDUtils;
import cn.fjcpc.manager.mapper.TbContentCategoryMapper;
import cn.fjcpc.manager.pojo.TbContentCategory;
import cn.fjcpc.manager.pojo.TbContentCategoryExample;
import cn.fjcpc.manager.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    @Override
    public List<EasyUITree> getContentCategoryListByParentId(Long id) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        example.createCriteria().andParentIdEqualTo(id).andStatusEqualTo(1);
        List<TbContentCategory> tbContentCategories = contentCategoryMapper.selectByExample(example);
        List<EasyUITree> list = new ArrayList<>();
        for (TbContentCategory contentCategory:tbContentCategories) {
            EasyUITree tree = new EasyUITree();
            tree.setId(contentCategory.getId());
            tree.setText(contentCategory.getName());
            tree.setState(contentCategory.getIsParent() ? "closed":"open");
            list.add(tree);
        }
        return list;
    }

    @Override
    public Result<Map<String,Object>> addContentCategory(TbContentCategory contentCategory) {
        Result<Map<String,Object>> result = new Result<>();
        // 判断当前节点名称是否存在
        TbContentCategoryExample example = new TbContentCategoryExample();
        example.createCriteria().andParentIdEqualTo(contentCategory.getParentId())
                .andStatusEqualTo(1);
        List<TbContentCategory> contentCategories = contentCategoryMapper.selectByExample(example);
        for (TbContentCategory chilren:contentCategories) {
            if (chilren.getName().equals(contentCategory.getName())) {
                result.setMessage("该分类名称已存在！");
                return result;
            }
        }
        // 填充数据并新增内容分类
        Date date = new Date();
        long id = IDUtils.genItemId();
        contentCategory.setId(id);
        contentCategory.setStatus(1);
        contentCategory.setSortOrder(1);
        contentCategory.setIsParent(false);
        contentCategory.setCreated(date);
        contentCategory.setUpdated(date);
        int index = contentCategoryMapper.insertSelective(contentCategory);
        if(index > 0) {
            if (contentCategories.isEmpty()) {
                TbContentCategory  cate = new TbContentCategory();
                cate.setId(contentCategory.getParentId());
                cate.setIsParent(true);
                cate.setUpdated(date);
                index = contentCategoryMapper.updateByPrimaryKeySelective(cate);
                if (index != 1) {
                    throw new MallException("内容分类管理新增失败！");
                }
            }
        }
        result.setStatus(200);
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        result.setData(map);
        return result;
    }

    @Override
    public Result updateContentCategoryNameById(TbContentCategory contentCategory) {
        Result result = new Result();
        // 保证当前节点同级下不能重名
        TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(contentCategory.getId());
        if (category != null) {
            TbContentCategoryExample example = new TbContentCategoryExample();
            example.createCriteria().andParentIdEqualTo(category.getParentId())
                    .andStatusEqualTo(1);
            List<TbContentCategory> cateList = contentCategoryMapper.selectByExample(example);
            for (TbContentCategory cate : cateList) {
                if (cate.getName().equals(contentCategory.getName())) {
                    result.setMessage("当前节点所在父节点下不能重名！");
                    return result;
                }
            }
        }
        Date date = new Date();
        contentCategory.setUpdated(date);
        int index = contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
        if (index != 1) {
            throw new MallException("重命名失败！");
        }
        result.setStatus(200);
        return result;
    }

    @Override
    public Result deleteContentCategoryById(TbContentCategory contentCategory) {
        Result result = new Result(200);
        Date date = new Date();
        int index = 0;
        contentCategory.setStatus(2);
        contentCategory.setUpdated(date);
        index = contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
        if (index > 0) {
            TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(contentCategory.getId());
            if (category != null) {
                TbContentCategoryExample example = new TbContentCategoryExample();
                example.createCriteria().andParentIdEqualTo(category.getParentId())
                        .andStatusEqualTo(1);
                List<TbContentCategory> categoryList = contentCategoryMapper.selectByExample(example);
                if (categoryList == null||categoryList.size() == 0) {
                    TbContentCategory cate = new TbContentCategory();
                    cate.setId(contentCategory.getParentId());
                    cate.setIsParent(false);
                    cate.setUpdated(date);
                    index += contentCategoryMapper.updateByPrimaryKeySelective(cate);
                    if (index != 2) {
                        throw new MallException("修改父节点是否父类目失败!");
                    }
                }
            }
        }else {
            throw new MallException("删除内容分类节点失败！");
        }
        return result;
    }

    @Override
    public TbContentCategory getContentCategoryByName(String name) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        example.createCriteria().andNameEqualTo(name).andStatusEqualTo(1);
        List<TbContentCategory> contentCategories = contentCategoryMapper.selectByExample(example);
        return contentCategories.isEmpty()?null:contentCategories.get(0);
    }

}
