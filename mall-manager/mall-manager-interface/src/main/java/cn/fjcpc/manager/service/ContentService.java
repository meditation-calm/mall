package cn.fjcpc.manager.service;

import cn.fjcpc.common.jedis.RedisKeyGenerator;
import cn.fjcpc.common.pojo.DataGridResult;
import cn.fjcpc.manager.dto.PortalContentDto;
import cn.fjcpc.manager.pojo.TbContent;

import java.util.List;

public interface ContentService {

    String key = RedisKeyGenerator.generatorKey("portal", TbContent.class, "getBigPic", null);

    /**
     * portal 页面内容数据展示
     * @param categoryId    商品类目ID
     * @param isSort    是否支持排序
     * @param count     个数
     * @return
     */
    List<PortalContentDto> getContentByCategoryId(int count, Long categoryId, boolean isSort);

    /**
     * 分页查询内容
     * @param categoryId
     * @param page
     * @param rows
     * @return
     */
    DataGridResult getContentByCategoryId(Long categoryId, int page, int rows);

    /**
     * 新增内容
     * @param content
     * @return
     */
    int addContent(TbContent content);

    /**
     * 修改内容
     * @param content
     * @return
     */
    int updateContent(TbContent content);

    /**
     * 批量删除内容信息
     * @param ids
     * @return
     */
    int deleteContents(String ids);

}
