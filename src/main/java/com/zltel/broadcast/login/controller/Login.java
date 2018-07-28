package com.zltel.broadcast.login.controller;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zltel.broadcast.um.util.PartyFirstSignInError;
import com.zltel.broadcast.um.util.PartySignInValidate;


@Controller
@RequestMapping(value = {"/"})
public class Login {

    /** 日志输出对象 **/
    public static final Logger logout = LoggerFactory.getLogger(Login.class);
    
    @Resource
    private PartySignInValidate partySignInValidate;


    public static final String LOGIN_URL = "/login/login";

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String login_get(String un, String ps, Model model) {

        return "redirect:" + LOGIN_URL + ".jsp";
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    public String login(String un, String ps, Model model) {
        try {
            if(StringUtils.isAnyBlank(un,ps))throw new AuthenticationException();
            
            //partySignInValidate.isFirstSignIn(un);	//如果是党员登录是否第一次登录
            UsernamePasswordToken token = new UsernamePasswordToken(un, ps);
            SecurityUtils.getSubject().login(token);
        } catch (IncorrectCredentialsException ie) {
            // 验证不通过
            model.addAttribute("error", "用户名/密码不正确!");
            return LOGIN_URL;
        } catch (ExcessiveAttemptsException ee) {
            // 重试次数过多
            model.addAttribute("error", "登陆失败次数过多,请稍后重试!");
            return LOGIN_URL;
        } catch (AuthenticationException ae) {
            // 验证不通过
            model.addAttribute("error", "用户名/密码不正确!");
            return LOGIN_URL;
        } catch (PartyFirstSignInError ae) {
            //党员第一次登录
            model.addAttribute("error", "党员第一次登陆，修改密码");
            return "redirect:/login/init_pwd.jsp";
        } catch (Exception e) {
            logout.error(e.getMessage());
            model.addAttribute("error", "未知错误");
            return LOGIN_URL;
        }
        return "redirect:/";
    }

    @RequestMapping(value = {"/logout"}, method = RequestMethod.GET)
    public String logout(String un, String ps) {
        SecurityUtils.getSubject().logout();
        return "redirect:" + LOGIN_URL + ".jsp";
    }

}
