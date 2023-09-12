package org.example.controller;

import org.example.pojo.Admin;
import org.example.pojo.Result;
import org.example.service.AdminServiceImp;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/admins")
//不配置gateway网关时使用解决跨域问题
//@CrossOrigin(origins = "http://localhost:8080",allowCredentials = "true",allowedHeaders = "*")
public class AdminController {

    @Resource
    private AdminServiceImp adminService;

    @GetMapping("/selectAll/{currentPage}/{pageSize}")
    public Result selectAll(@PathVariable Integer currentPage, @PathVariable Integer pageSize){
        List<Admin> admins = adminService.selectAll(currentPage, pageSize);
        return new Result(true,admins,null);
    }

    //根据aId查询用户
    @GetMapping("/selectByAid/{aId}")
    public Result selectByAid(@PathVariable Integer aId){
        Admin admin = adminService.selectByAId(aId);
        if(admin != null){
            return new Result(true,admin,"查询成功");
        }else{
            return new Result(false,null,"暂无用户信息");
        }
    }

    //根据aPhone查询用户
    @GetMapping("/selectByPhone/{aPhone}")
    public Result selectByAid(@PathVariable String aPhone){
        Admin admin = adminService.selectByPhone(aPhone);
        if(admin == null){
            return new Result(false,null,"无此用户");
        }else{
            return new Result(true,admin.getAAccount(),null);
        }
    }

    //根据账号密码验证用户的身份(登录)
    @PostMapping("/login")
    public Result login(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

        String account = httpServletRequest.getParameter("account");
        String password = httpServletRequest.getParameter("password");

        Admin admin = adminService.selectByAccountAndPassword(account,password);

        if(admin == null){
            return new Result(false,null,"账号或密码错误");
        }else{
            //获取ServletContext域
            //使用servlet上下文共享区域实现(ServletContext)
            ServletContext servletContext = httpServletRequest.getSession().getServletContext();
            //获取用户的登录状态
            //用户多端登录限制
            String attribute = (String)servletContext.getAttribute(admin.getAId().toString());
            if(attribute == null){
                //(1)登录成功将用户信息存入session
                HttpSession session = httpServletRequest.getSession();
                session.setAttribute("account",account);
                session.setAttribute("password",password);
                //设置session的失效时间(用户不予交互的最大忍耐时间)
                session.setMaxInactiveInterval(7*24*60*60);
                //(2)登录成功后将sessionId作为值返回一个cookie
                String sessionId = session.getId();
                Cookie cookie = new Cookie("JSESSIONID", sessionId);
                //7天过期的cookie
                cookie.setMaxAge(7*24*60*60);
                httpServletResponse.addCookie(cookie);
                //将用户的登录信息存入ServletContext域中
                servletContext.setAttribute(admin.getAId().toString(),sessionId);
                return new Result(true,admin,"登录成功");
            }else {
                //获取session
                HttpSession session = httpServletRequest.getSession(false);
                String sessionId = session.getId();
                //比较sessionId是否一致
                if(!attribute.equals(sessionId)){
                    return new Result(false,null,"账号已在别处登录");
                }else{
                    return new Result(true,admin,"登录成功");
                }
            }

        }
    }


    //自动登录
    @PostMapping("/autoLogin")
    public Result autoLogin(HttpServletRequest httpServletRequest){
        //获取cookie
        Cookie[] cookies = httpServletRequest.getCookies();
        //获取当前的session
        HttpSession session = httpServletRequest.getSession(false);
        //获取sessionId
        String sessionId = session.getId();

        for(Cookie cookie : cookies){
            if(cookie.getName().equals("JSESSIONID")){
                //匹配当前的sessionId
                if(cookie.getValue().equals(sessionId)){
                    //获取具体的session值
                    String account = (String)session.getAttribute("account");
                    String password = (String)session.getAttribute("password");
                    //根据session信息查询当前用户
                    Admin admin = adminService.selectByAccountAndPassword(account,password);

                    if(admin == null){
                        return new Result(false,null,"自动登录失败,查不到用户");
                    }else{
                        //检测账号登录状态
                        String attribute = (String) session.getServletContext().getAttribute(admin.getAId().toString());
                        if(attribute==null){
                            return new Result(false,null,"无信息");
                        }else{
                            if(!attribute.equals(sessionId)){
                                return new Result(false,null,"账号已在别处登录");
                            }else{
                                return new Result(true,admin,"登录成功");
                            }
                        }

                    }

                }
            }
        }
        return new Result(false,null,"自动登录失败,暂无用户");
    }

    //退出登录
    @PostMapping("/backLogin")
    public Result backLogin(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse){

        String aId = httpServletRequest.getParameter("aId");
        String status = httpServletRequest.getParameter("status");

        HttpSession session = httpServletRequest.getSession(false);

        Admin admin = adminService.selectByAId(Integer.parseInt(aId));

        if(admin == null){
            return new Result(false,null,"账号异常");
        }else{
            if(status.equals("0")){
                //清除用户账号的登录状态
                session.getServletContext().removeAttribute(admin.getAId().toString());
                //清除cookie
                Cookie cookie = new Cookie("JSESSIONID", "");
                cookie.setMaxAge(0);
                httpServletResponse.addCookie(cookie);
                //清除用户账号的登录状态
                session.getServletContext().removeAttribute(admin.getAId().toString());
                //不清除session
                return new Result(true,admin,"账号已退出");
            }else if(status.equals("1")){
                //清除用户账号的登录状态
                session.getServletContext().removeAttribute(admin.getAId().toString());
                //清除cookie
                Cookie cookie = new Cookie("JSESSIONID", "");
                cookie.setMaxAge(0);
                httpServletResponse.addCookie(cookie);
                //清除session
                session.removeAttribute("account");
                session.removeAttribute("password");
                session.invalidate();
                return new Result(true,admin,"账号已注销");
            }else{
                return new Result(false,null,"参数异常");
            }
        }

    }


    //注册
    @PostMapping("/register")
    public Result createAdmin(HttpServletRequest httpServletRequest){

        String phoneNumber = httpServletRequest.getParameter("phoneNumber");
        String password = httpServletRequest.getParameter("password");
        String password2 = httpServletRequest.getParameter("password2");
        String aName = httpServletRequest.getParameter("aName");
        boolean status = adminService.createAdmin(phoneNumber, password, password2,aName);

        if(!status){
            return new Result(false,null,"注册失败");
        }else{
            //查询当前新注册的用户信息
            Admin admin = adminService.selectByPhone(phoneNumber);
            return new Result(true,admin.getAAccount(),"注册成功");
        }

    }


    //修改用户名
    @PutMapping("/updateAdminName")
    public Result updateAdminName(HttpServletRequest httpServletRequest){
        String aId = httpServletRequest.getParameter("aId");
        String aName = httpServletRequest.getParameter("aName");

        boolean b = adminService.updateAdminName(Integer.parseInt(aId),aName);

        if(!b){
            return new Result(false,"修改失败");
        }else{
            //查询当前的用户信息
            Admin admin = adminService.selectByAId(Integer.parseInt(aId));
            return new Result(true,admin,"修改成功");
        }

    }


    //修改用户手机号
    @PutMapping("/updateAdminPhoneNumber")
    public Result updateAdminPhoneNumber(HttpServletRequest httpServletRequest){
        String aId = httpServletRequest.getParameter("aId");
        String oldPhoneNumber = httpServletRequest.getParameter("oldPhoneNumber");
        String newPhoneNumber = httpServletRequest.getParameter("newPhoneNumber");

        boolean b = adminService.updateAdminPhoneNumber(Integer.parseInt(aId),oldPhoneNumber, newPhoneNumber);

        if(!b){
            return new Result(false,"修改失败");
        }else{
            //查询当前的用户信息
            Admin admin = adminService.selectByAId(Integer.parseInt(aId));
            return new Result(true,admin,"修改成功");
        }

    }


    //修改用户密码
    @PutMapping("/updateAdminPassword")
    public Result updateAdminPassword(HttpServletRequest httpServletRequest){
        String aId = httpServletRequest.getParameter("aId");
        String oldPassword = httpServletRequest.getParameter("oldPassword");
        String newPassword = httpServletRequest.getParameter("newPassword");
        String newPassword2 = httpServletRequest.getParameter("newPassword2");

        boolean b = adminService.updateAdminPassword(Integer.parseInt(aId), oldPassword, newPassword,newPassword2);

        if(!b){
            return new Result(false,"修改失败");
        }else{
            //查询当前的用户信息
            Admin admin = adminService.selectByAId(Integer.parseInt(aId));
            //修改成功后更新用户session
            HttpSession session = httpServletRequest.getSession(false);
            session.setAttribute("account",admin.getAAccount());
            session.setAttribute("password",newPassword);
            return new Result(true,admin,"修改成功");
        }

    }


    //找回用户密码
    @PutMapping("/backAdminPassword")
    public Result backAdminPassword(HttpServletRequest httpServletRequest){
        String phoneNumber = httpServletRequest.getParameter("phoneNumber");
        String newPassword = httpServletRequest.getParameter("newPassword");
        String newPassword2 = httpServletRequest.getParameter("newPassword2");

        boolean b = adminService.backAdminPassword(phoneNumber, newPassword,newPassword2);

        if(!b){
            return new Result(false,"用户不存在");
        }else{
            return new Result(true,"修改成功");
        }

    }



    @GetMapping("/selectAllAdminNum")
    public Result selectAllAdminNum(){
        return new Result(true,adminService.selectAllAdminNum(),"查询成功");
    }


    //判断cookie是否存在
    @PostMapping("/checkCookieExist")
    public Result checkCookieExist(HttpServletRequest httpServletRequest){
        Cookie[] cookies = httpServletRequest.getCookies();

        for(Cookie cookie : cookies){
            if(cookie.getName().equals("JSESSIONID")){
                HttpSession session = httpServletRequest.getSession(false);
                if(cookie.getValue().equals(session.getId())){
                    return new Result(true,null,"cookie存在");
                }else{
                    return new Result(false,null,"cookie不匹配");
                }
            }
        }
        return new Result(false,null,"cookie不存在");
    }

}
