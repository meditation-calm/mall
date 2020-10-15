package cn.fjcpc.item.service.impl;

import cn.fjcpc.common.jedis.JedisClientCluster;
import cn.fjcpc.common.jedis.RedisKeyGenerator;
import cn.fjcpc.common.utils.JsonUtils;
import cn.fjcpc.item.pojo.ItemParam;
import cn.fjcpc.item.service.ItemParamItemService;
import cn.fjcpc.manager.pojo.TbItemParamItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemParamItemServiceImpl implements ItemParamItemService {
    @Autowired
    private cn.fjcpc.manager.service.ItemParamItemService itemParamItemDubboService;
    @Autowired
    private JedisClientCluster jedisClient;

    @Override
    public String getItemParamTable(Long id) {
        StringBuilder builder = new StringBuilder();
        //  检查Redis缓存
        String key = RedisKeyGenerator.generatorKey("mall-item",ItemParamItemServiceImpl.class,"getItemParamTable",id);
        if (jedisClient.exists(key)){
            String json = jedisClient.get(key);
            if (json!=null && !json.equals("")) {
                return json;
            }
        }
        // 数据库中获取
        TbItemParamItem paramItem = itemParamItemDubboService.getItemParamItemById(id);
        if (paramItem!=null) {
            List<ItemParam> itemParams = JsonUtils.jsonToList(paramItem.getParamData(), ItemParam.class);

            for (ItemParam ip:itemParams) {
                builder.append("<table width='500px' style='color:gray;'>");

                if (ip.getParams()!=null && ip.getParams().size()>0) {
                    for (int i=0;i<ip.getParams().size();i++) {
                        builder.append("<tr>");
                        if (i==0) {
                            builder.append("<td align='right' width='10%'>"+ip.getGroup()+"</td>");
                        }else {
                            builder.append("<td></td>");
                        }
                        builder.append("<td align='right' width='30%'>"+ip.getParams().get(i).getK()+"</td>");
                        builder.append("<td width='10%'></td>");
                        builder.append("<td>"+ip.getParams().get(i).getV()+"</td>");
                        builder.append("</tr>");
                    }
                }

                builder.append("</table>");
                builder.append("<hr style='color:gray;'/>");
            }

            jedisClient.set(key,builder.toString());
        }
        return builder.toString();
    }
}
