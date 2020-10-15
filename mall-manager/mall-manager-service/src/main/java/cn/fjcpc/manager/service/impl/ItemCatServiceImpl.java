package cn.fjcpc.manager.service.impl;

import cn.fjcpc.common.jedis.JedisClientCluster;
import cn.fjcpc.common.jedis.RedisKeyGenerator;
import cn.fjcpc.common.pojo.EasyUITree;
import cn.fjcpc.common.pojo.Result;
import cn.fjcpc.common.utils.JsonUtils;
import cn.fjcpc.manager.dto.ItemCatMenu;
import cn.fjcpc.manager.mapper.TbItemCatMapper;
import cn.fjcpc.manager.pojo.TbItemCat;
import cn.fjcpc.manager.pojo.TbItemCatExample;
import cn.fjcpc.manager.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private TbItemCatMapper itemCatMapper;
    @Autowired
    private JedisClientCluster jedisClientCluster;

    @Override
    public Result<List<Object>> selectItemCatAll() {
        Result<List<Object>> result = new Result<>();
        // 查询redis缓存数据
        String key = RedisKeyGenerator.generatorKey("item", TbItemCat.class, "itemCatAll", null);
        if (jedisClientCluster.exists(key)) {
            String itemCatJson = jedisClientCluster.get(key);
            if (itemCatJson != null && !itemCatJson.isEmpty()) {
                result.setData(JsonUtils.jsonToList(itemCatJson,Object.class));
                return result;
            }
        }
        // 获取所有一级菜单
        List<TbItemCat> tbItemCats = getItemCatList2TbItemCat(0L);
        List<Object> data = getMenuChilren(tbItemCats);
        // 缓存属Portal项目的菜单数据
        jedisClientCluster.set(key,JsonUtils.objectToJson(data));
        result.setData(data);
        return result;
    }

    /**
     * 递归查询出所有父级菜单对应子菜单(包涵父菜单数据)
     * @param tbItemCats
     * @return
     */
    private List<Object> getMenuChilren(List<TbItemCat> tbItemCats){
        List<Object> list = new ArrayList<>();
        if (!tbItemCats.isEmpty()) {
            for (TbItemCat itemCat : tbItemCats) {
                if (itemCat.getIsParent()) {
                    ItemCatMenu itemCatMenu = new ItemCatMenu();
                    itemCatMenu.setU("/products/"+ itemCat.getId()+".html");
                    itemCatMenu.setN("<a href='/products/"+ itemCat.getId()+".html'>"+itemCat.getName()+"</a>");
                    List<TbItemCat> chilrenMenu = getItemCatList2TbItemCat(itemCat.getId());
                    itemCatMenu.setI(getMenuChilren(chilrenMenu));
                    list.add(itemCatMenu);
                }else {
                    list.add("/products/"+ itemCat.getId()+".html|"+ itemCat.getName());
                }
            }
        }
        return list;
    }

    @Override
    public List<EasyUITree> getItemCatList2EasyUITree(Long pid) {
        TbItemCatExample example = new TbItemCatExample();
        example.createCriteria().andParentIdEqualTo(pid);
        List<TbItemCat> tbItemCats = itemCatMapper.selectByExample(example);
        List<EasyUITree> list = new ArrayList<>();
        for (TbItemCat itemCat:tbItemCats) {
            EasyUITree tree = new EasyUITree();
            tree.setId(itemCat.getId());
            tree.setText(itemCat.getName());
            tree.setState(itemCat.getIsParent()?"closed":"open");
            list.add(tree);
        }
        return list;
    }

    @Override
    public List<TbItemCat> getItemCatList2TbItemCat(Long pid) {
        TbItemCatExample example = new TbItemCatExample();
        example.createCriteria().andParentIdEqualTo(pid);
        return itemCatMapper.selectByExample(example);
    }

    @Override
    public TbItemCat getItemCat(Long id) {
        return itemCatMapper.selectByPrimaryKey(id);
    }
}
