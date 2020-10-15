package cn.fjcpc.portal.controller;

import cn.fjcpc.common.utils.JsonUtils;
import cn.fjcpc.manager.pojo.TbContentCategory;
import cn.fjcpc.manager.service.ContentCategoryService;
import cn.fjcpc.manager.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    @Autowired
    private ContentService contentService;
    @Autowired
    private ContentCategoryService contentCategoryService;

    @RequestMapping("/")
    public String showIndex(Model model){
        // 初始化首页大广告
        TbContentCategory contentCategory = contentCategoryService.getContentCategoryByName("大广告");
        if (contentCategory != null) {
            model.addAttribute("ad1", JsonUtils.objectToJson(contentService.
                    getContentByCategoryId(0,contentCategory.getId(),true)));
        }
        return "index";
    }

}
