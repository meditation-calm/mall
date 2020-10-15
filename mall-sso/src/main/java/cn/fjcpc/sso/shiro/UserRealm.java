package cn.fjcpc.sso.shiro;

import cn.fjcpc.manager.pojo.TbUser;
import cn.fjcpc.manager.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class UserRealm extends AuthorizingRealm {

    private UserService userService;

    public UserRealm(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String getName() {
        return "userRealm";
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("---------------------授权--------------------");
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("---------------------认证--------------------");
        String username = token.getPrincipal().toString();
        TbUser user = userService.getUserByUserName(username);
        if (user == null) {
            return null;
        }
        return new SimpleAuthenticationInfo(user,user.getPassword(), ByteSource.Util.bytes("test"),getName());
    }

}
