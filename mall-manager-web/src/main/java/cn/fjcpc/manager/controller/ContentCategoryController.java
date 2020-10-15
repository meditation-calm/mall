package cn.fjcpc.manager.controller;

import cn.fjcpc.common.pojo.EasyUITree;
import cn.fjcpc.common.pojo.Result;
import cn.fjcpc.manager.pojo.TbContentCategory;
import cn.fjcpc.manager.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class ContentCategoryController {
    @Autowired
    private ContentCategoryService contentCategoryService;

    /**
     * 查询网站内容分类管理-->内容分类管理
     * @param id
     * @return
     */
    @RequestMapping(value = "/content/category/list",method = RequestMethod.GET)
    @ResponseBody
    public List<EasyUITree> getContentCategoryList(@RequestParam(defaultValue = "0") Long id) {
        return contentCategoryService.getContentCategoryListByParentId(id);
    }

    /**
     * 新增内容分类类目
     * @param contentCategory
     * @return
     */
    @RequestMapping(value = "/content/category/create",method = RequestMethod.POST)
    @ResponseBody
    public Result<Map<String,Object>> createContentCategory(TbContentCategory contentCategory) {
        return contentCategoryService.addContentCategory(contentCategory);
    }

    /**
     * 重命名内容分类节点
     * @param contentCategory
     * @return
     */
    @RequestMapping(value = "/content/category/update",method = RequestMethod.POST)
    @ResponseBody
    public Result updateContentCategory(TbContentCategory contentCategory) {
        return contentCategoryService.updateContentCategoryNameById(contentCategory);
    }

    /**
     * 删除内容分类节点
     * @param contentCategory
     * @return
     */
    @RequestMapping(value = "/content/category/delete",method = RequestMethod.POST)
    @ResponseBody
    public Result deleteContentCategory(TbContentCategory contentCategory) {
        return contentCategoryService.deleteContentCategoryById(contentCategory);
    }

}
