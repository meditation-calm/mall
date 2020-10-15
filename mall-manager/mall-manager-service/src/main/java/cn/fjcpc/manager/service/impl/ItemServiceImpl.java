package cn.fjcpc.manager.service.impl;

import cn.fjcpc.common.jedis.JedisClientCluster;
import cn.fjcpc.common.jedis.RedisKeyGenerator;
import cn.fjcpc.common.pojo.DataGridResult;
import cn.fjcpc.common.utils.HttpUtils;
import cn.fjcpc.common.utils.IDUtils;
import cn.fjcpc.common.utils.JsonUtils;
import cn.fjcpc.manager.mapper.TbItemDescMapper;
import cn.fjcpc.manager.mapper.TbItemMapper;
import cn.fjcpc.manager.mapper.TbItemParamItemMapper;
import cn.fjcpc.manager.pojo.TbItem;
import cn.fjcpc.manager.pojo.TbItemDesc;
import cn.fjcpc.manager.pojo.TbItemExample;
import cn.fjcpc.manager.pojo.TbItemParamItem;
import cn.fjcpc.manager.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;
    @Autowired
    private JedisClientCluster jedisClient;
    @Value("${addsolr.url}")
    private String solrUrl;

    @Override
    public DataGridResult getItemList(int page, int rows) {
        /* 分页插件开始分页 */
        PageHelper.startPage(page,rows);
        List<TbItem> tbItems = itemMapper.selectByExample(new TbItemExample());

        PageInfo<TbItem> pageInfo = new PageInfo<>(tbItems);
        DataGridResult dataGridResult = new DataGridResult();
        dataGridResult.setTotal(pageInfo.getTotal());
        dataGridResult.setRows(pageInfo.getList());
        return dataGridResult;
    }

    @Override
    public int updateItem(String ids, String uri) {
        String[] strs = ids.split(",");
        int result = 0;
        int itemUri_Index;
        Date updated = new Date();
        TbItem tbItem;
        if((itemUri_Index = ItemService.Item_Request_Uri.indexOf(uri))!=-1) {
            for(String id:strs) {
                tbItem = new TbItem();
                tbItem.setId(Long.parseLong(id));
                tbItem.setStatus(ItemService.Item_Status[itemUri_Index]);
                tbItem.setUpdated(updated);
                result += itemMapper.updateByPrimaryKeySelective(tbItem);
                // redis缓存中移除
                String itemKey = RedisKeyGenerator.generatorKey("mall-item",ItemServiceImpl.class,"getItemById",Long.parseLong(id));
                String descKey = RedisKeyGenerator.generatorKey("mall-item",ItemDescServiceImpl.class,"getItemDescById",Long.parseLong(id));
                String paramKey = RedisKeyGenerator.generatorKey("mall-item",ItemParamItemServiceImpl.class,"getItemParamTable",Long.parseLong(id));
                if (itemUri_Index >= 1) {
                    if (jedisClient.exists(itemKey)) {
                        jedisClient.del(itemKey);
                    }
                    if (jedisClient.exists(descKey)) {
                        jedisClient.del(descKey);
                    }
                    if (jedisClient.exists(paramKey)) {
                        jedisClient.del(paramKey);
                    }
                }
            }
            if(result == strs.length) {
                return 1;
            }
        }
        return 0;
    }

    @Override
    public TbItem getItemById(Long id) {
        return itemMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean saveItem(TbItem item, String desc, String paramData) {
        boolean result = false;
        //商品ID
        long id = IDUtils.genItemId();
        Date currentDate = new Date();
        //填充商品表信息
        item.setId(id);
        item.setStatus((byte) 1);
        item.setCreated(currentDate);
        item.setUpdated(currentDate);
        int index = itemMapper.insertSelective(item);
        //商品描述表
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(id);
        if(desc != null) {
            itemDesc.setItemDesc(desc);
        }
        itemDesc.setCreated(currentDate);
        itemDesc.setUpdated(currentDate);
        index += itemDescMapper.insertSelective(itemDesc);
        //商品规格和商品的关系表
        TbItemParamItem itemParamItem = new TbItemParamItem();
        itemParamItem.setItemId(id);
        itemParamItem.setParamData(paramData);
        itemParamItem.setCreated(currentDate);
        itemParamItem.setUpdated(currentDate);
        index += itemParamItemMapper.insertSelective(itemParamItem);
        if(index == 3){
            result = true;
        }

        // 同时同步solr数据
        new Thread(() -> {
            Map<String,Object> map = new HashMap<>();
            map.put("item",item);
            map.put("desc",desc);
            HttpUtils.doPostJson(solrUrl, JsonUtils.objectToJson(map));
        }).start();

        return result;
    }

    @Override
    public List<TbItem> getItemByStatus(byte status) {
        TbItemExample example = new TbItemExample();
        example.createCriteria().andStatusEqualTo(status);
        return itemMapper.selectByExample(example);
    }
}
