package cn.fjcpc.manager.service.impl;

import cn.fjcpc.manager.mapper.TbUserMapper;
import cn.fjcpc.manager.pojo.TbUser;
import cn.fjcpc.manager.pojo.TbUserExample;
import cn.fjcpc.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private TbUserMapper userMapper;

    @Override
    public TbUser getUserByUserName(String username) {
        TbUserExample example = new TbUserExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<TbUser> users = userMapper.selectByExample(example);
        if (users != null && users.size()>0) {
            return users.get(0);
        }
        return null;
    }
}
