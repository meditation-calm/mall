package cn.fjcpc.item.service.impl;

import cn.fjcpc.common.jedis.JedisClientCluster;
import cn.fjcpc.common.jedis.RedisKeyGenerator;
import cn.fjcpc.common.utils.JsonUtils;
import cn.fjcpc.item.service.ItemService;
import cn.fjcpc.manager.dto.SearchItemDto;
import cn.fjcpc.manager.pojo.TbItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private cn.fjcpc.manager.service.ItemService itemDubboService;
    @Autowired
    private JedisClientCluster jedisClient;

    @Override
    public SearchItemDto getItemById(Long id) {
        String key = RedisKeyGenerator.generatorKey("mall-item",ItemServiceImpl.class,"getItemById",id);
        if (jedisClient.exists(key)){
            String json = jedisClient.get(key);
            if (json!=null && !json.equals("")) {
                return JsonUtils.jsonToPojo(json,SearchItemDto.class);
            }
        }
        SearchItemDto itemDto = new SearchItemDto();
        TbItem item = itemDubboService.getItemById(id);
        itemDto.setId(id);
        itemDto.setSellPoint(item.getSellPoint());
        itemDto.setTitle(item.getTitle());
        itemDto.setPrice(item.getPrice());
        itemDto.setImages(item.getImage()==null||item.getImage().equals("") ? new String[1] : item.getImage().split(","));

        jedisClient.set(key,JsonUtils.objectToJson(itemDto));
        return itemDto;
    }
}
