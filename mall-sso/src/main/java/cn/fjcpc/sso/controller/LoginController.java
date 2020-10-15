package cn.fjcpc.sso.controller;

import cn.fjcpc.common.pojo.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    @RequestMapping("/user/showLogin")
    public String toLoginPage() {
        return "login";
    }

    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    @ResponseBody
    public Result login(String username, String password){
        Result result = new Result(200);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            result.setMessage("用户名不存在");
            result.setStatus(0);
        } catch (IncorrectCredentialsException e) {
            result.setMessage("用户名或密码错误");
            result.setStatus(0);
        }
        /*
        SavedRequest savedRequest = WebUtils.getSavedRequest(request);
        System.out.println(savedRequest.getRequestUrl());
        */
        return result;
    }

}
