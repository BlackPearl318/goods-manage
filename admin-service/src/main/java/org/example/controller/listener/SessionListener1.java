package org.example.controller.listener;

import org.example.pojo.Admin;
import org.example.service.AdminServiceImp;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

//监听器组件,监听session的存活状态
//注册组件
@Component
@WebListener
public class SessionListener1 implements HttpSessionListener {

    @Resource
    private AdminServiceImp adminService;

    //session销毁时调用
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        //获取具体的session值
        String account = (String)session.getAttribute("account");
        String password = (String)session.getAttribute("password");
        //根据session信息查询当前用户
        Admin admin = adminService.selectByAccountAndPassword(account,password);
        //session过期前将用户的登录状态清空
        //清除用户账号的登录状态
        session.getServletContext().removeAttribute(admin.getAId().toString());
    }
}
