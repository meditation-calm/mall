package cn.fjcpc.manager.service.impl;

import cn.fjcpc.common.exception.MallException;
import cn.fjcpc.common.jedis.JedisClientCluster;
import cn.fjcpc.common.pojo.DataGridResult;
import cn.fjcpc.common.utils.IDUtils;
import cn.fjcpc.common.utils.JsonUtils;
import cn.fjcpc.manager.dto.PortalContentDto;
import cn.fjcpc.manager.dto.front.PortalContentDtoUtils;
import cn.fjcpc.manager.mapper.TbContentCategoryMapper;
import cn.fjcpc.manager.mapper.TbContentMapper;
import cn.fjcpc.manager.pojo.TbContent;
import cn.fjcpc.manager.pojo.TbContentCategory;
import cn.fjcpc.manager.pojo.TbContentCategoryExample;
import cn.fjcpc.manager.pojo.TbContentExample;
import cn.fjcpc.manager.service.ContentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    private TbContentMapper contentMapper;
    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;
    @Autowired
    private JedisClientCluster jedisClientCluster;

    @Override
    public List<PortalContentDto> getContentByCategoryId(int count, Long categoryId, boolean isSort) {
        List<PortalContentDto> list = new ArrayList<>();

        if (count == 0) {
            count = 4;
        }
        if (jedisClientCluster.exists(key)) {
            List<PortalContentDto> portalContentDtos = JsonUtils.jsonToList(jedisClientCluster.get(key), PortalContentDto.class);
            if (!portalContentDtos.isEmpty()) {
                if (portalContentDtos.size() >= count) {
                    return portalContentDtos.subList(0,count);
                }
                return portalContentDtos;
            }
        }

        TbContentExample example = new TbContentExample();
        if (isSort) {
            example.setOrderByClause("updated desc");
        }
        example.createCriteria().andCategoryIdEqualTo(categoryId);
        // 开始分页
        PageHelper.startPage(1,count);
        List<TbContent> tbContents = contentMapper.selectByExampleWithBLOBs(example);
        PageInfo<TbContent> pageInfo = new PageInfo<>(tbContents);
        List<TbContent> contentList = pageInfo.getList();
        if (!contentList.isEmpty()) {
            for (TbContent content:contentList) {
                PortalContentDto contentDto = PortalContentDtoUtils.Tbcontent2PortalContentDto(content);
                list.add(contentDto);
            }
        }
        jedisClientCluster.set(key,JsonUtils.objectToJson(list));
        return list;
    }

    @Override
    public DataGridResult getContentByCategoryId(Long categoryId, int page, int rows) {
        DataGridResult result = new DataGridResult();
        PageHelper.startPage(page,rows);
        TbContentExample example = new TbContentExample();
        example.setOrderByClause("updated desc");
        if (categoryId != 0) {
            example.createCriteria().andCategoryIdEqualTo(categoryId);
        }
        List<TbContent> contents = contentMapper.selectByExampleWithBLOBs(example);
        PageInfo<TbContent> pageInfo = new PageInfo<>(contents);
        result.setTotal(pageInfo.getTotal());
        result.setRows(pageInfo.getList());
        return result;
    }

    @Override
    public int addContent(TbContent content) {
        Long id = IDUtils.genItemId();
        Date date = new Date();
        content.setId(id);
        content.setCreated(date);
        content.setUpdated(date);
        int index = 0;
        if ((index = contentMapper.insertSelective(content)) != 1) {
            throw new MallException("新增内容失败！");
        }
        TbContentCategoryExample example = new TbContentCategoryExample();
        example.createCriteria().andNameEqualTo("大广告");
        List<TbContentCategory> cate = contentCategoryMapper.selectByExample(example);
        if (cate != null && !cate.isEmpty()) {
            if (cate.get(0).getId().equals(content.getCategoryId())) {
                if (jedisClientCluster.exists(key)) {
                    String json = jedisClientCluster.get(key);
                    if (json != null && !json.trim().equals("")) {
                        List<PortalContentDto> portalContentDtos = JsonUtils.jsonToList(json, PortalContentDto.class);
                        PortalContentDto contentDto = PortalContentDtoUtils.Tbcontent2PortalContentDto(content);
                        // 大广告最多显示10个
                        if (portalContentDtos.size() == 10) {
                            portalContentDtos.remove(9);
                        }
                        portalContentDtos.add(0,contentDto);
                        jedisClientCluster.set(key,JsonUtils.objectToJson(portalContentDtos));
                    }
                }
            }
        }
        return index;
    }

    @Override
    public int updateContent(TbContent content) {
        Date date = new Date();
        content.setUpdated(date);
        int result;
        int index = -1;
        if ((result = contentMapper.updateByPrimaryKeySelective(content)) == 1) {
            TbContentCategoryExample example = new TbContentCategoryExample();
            example.createCriteria().andNameEqualTo("大广告");
            List<TbContentCategory> cate = contentCategoryMapper.selectByExample(example);
            if (cate!= null && !cate.isEmpty()) {
                if (cate.get(0).getId().equals(content.getCategoryId())) {
                    if (jedisClientCluster.exists(key)) {
                        String contentJson = jedisClientCluster.get(key);
                        if (contentJson != null && !contentJson.trim().equals("")) {
                            List<PortalContentDto> portalContentDtos = JsonUtils.jsonToList(contentJson, PortalContentDto.class);
                            for (PortalContentDto contentDto:portalContentDtos) {
                                index ++;
                                if (contentDto.getId().equals(content.getId())) {
                                    portalContentDtos.set(index,PortalContentDtoUtils.Tbcontent2PortalContentDto(content));
                                    jedisClientCluster.set(key,JsonUtils.objectToJson(portalContentDtos));
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    @Override
    public int deleteContents(String ids) {
        String[] strs = ids.split(",");
        int index = 0;
        // 删除下次重新更新值
        if (jedisClientCluster.exists(key)) {
            jedisClientCluster.del(key);
        }
        for (String id:strs) {
            if (!id.trim().isEmpty()) {
                index += contentMapper.deleteByPrimaryKey(Long.parseLong(id));
            }
        }
        if (index != strs.length) {
            throw new MallException("内容信息删除失败！");
        }
        return 1;
    }

}
