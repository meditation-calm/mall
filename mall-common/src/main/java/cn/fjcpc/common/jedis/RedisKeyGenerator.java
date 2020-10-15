package cn.fjcpc.common.jedis;

import cn.fjcpc.common.pojo.EasyUITree;

public class RedisKeyGenerator {

    /**
     * Redis生成Key名
     * @param project   使用类所属项目名
     * @param tableClass    表对象
     * @param serviceName   业务名
     * @param id    模块ID
     * @return
     */
    public static String generatorKey(String project,Class tableClass,String serviceName,Long id) {
        String key = project+":"+tableClass.getSimpleName()+":"+serviceName;
        if (id != null) {
            key += ":"+id;
        }
        return key;
    }

    public static void main(String[] args) {
        System.out.println(generatorKey("portal", EasyUITree.class,"BigPic",1L));
    }

}
