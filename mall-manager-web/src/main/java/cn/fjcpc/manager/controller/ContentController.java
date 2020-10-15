package cn.fjcpc.manager.controller;

import cn.fjcpc.common.pojo.DataGridResult;
import cn.fjcpc.common.pojo.Result;
import cn.fjcpc.manager.pojo.TbContent;
import cn.fjcpc.manager.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *  TbContent内容表管理
 */
@Controller
@ResponseBody
public class ContentController {
    @Autowired
    private ContentService contentService;

    @RequestMapping(value = "/content/query/list",method = RequestMethod.GET)
    public DataGridResult queryContentList(@RequestParam(defaultValue = "0") Long categoryId,
                                           int page, int rows) {
        return contentService.getContentByCategoryId(categoryId,page,rows);
    }

    @RequestMapping(value = "/content/save",method = RequestMethod.POST)
    public Result saveContent(TbContent content) {
        Result result = new Result();
        if (contentService.addContent(content) == 1) {
            result.setStatus(200);
        }
        return result;
    }

    @RequestMapping(value = "/rest/content/edit",method = RequestMethod.POST)
    public Result editContent(TbContent content) {
        Result result = new Result();
        if (contentService.updateContent(content) == 1) {
            result.setStatus(200);
        }
        return result;
    }

    @RequestMapping(value = "/content/delete",method = RequestMethod.POST)
    public Result deleteContent(String ids) {
        Result result = new Result();
        if (contentService.deleteContents(ids) == 1) {
            result.setStatus(200);
            result.setMessage("删除商品不存在！");
        }
        return  result;
    }

}
