package cn.fjcpc.manager.service;

import cn.fjcpc.manager.pojo.TbUser;

public interface UserService {

    /**
     * 根据用户名密码查询用户
     * @param username
     * @return
     */
    TbUser getUserByUserName(String username);

}
