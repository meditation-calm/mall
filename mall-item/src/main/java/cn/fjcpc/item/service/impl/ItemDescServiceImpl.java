package cn.fjcpc.item.service.impl;

import cn.fjcpc.common.jedis.JedisClientCluster;
import cn.fjcpc.common.jedis.RedisKeyGenerator;
import cn.fjcpc.item.service.ItemDescService;
import cn.fjcpc.manager.pojo.TbItemDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemDescServiceImpl implements ItemDescService {
    @Autowired
    private cn.fjcpc.manager.service.ItemDescService itemDescDubboService;
    @Autowired
    private JedisClientCluster jedisClient;

    @Override
    public String getItemDescById(Long id) {
        String key = RedisKeyGenerator.generatorKey("mall-item",ItemDescServiceImpl.class,"getItemDescById",id);
        if (jedisClient.exists(key)){
            String json = jedisClient.get(key);
            if (json!=null && !json.equals("")) {
                return json;
            }
        }
        TbItemDesc itemDesc = itemDescDubboService.getItemDescById(id);

        jedisClient.set(key,itemDesc.getItemDesc());
        return itemDesc.getItemDesc();
    }
}
