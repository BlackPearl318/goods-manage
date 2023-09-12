package org.example.service;

import org.example.pojo.Admin;

import java.util.List;

public interface AdminService {

    //查询全部
    List<Admin> selectAll(Integer currentPage,Integer pageSize);

    //根据aId查询
    Admin selectByAId(Integer aId);

    //根据账号密码验证用户的身份
    Admin selectByAccountAndPassword(String account,String password);

    //创建用户(注册)
    boolean createAdmin(String phoneNumber, String password, String password2, String aName);

    //根据手机号查询用户信息
    Admin selectByPhone(String aPhone);

    //修改用户名
    boolean updateAdminName(Integer aId,String aName);

    //修改用户手机号
    boolean updateAdminPhoneNumber(Integer aId,String oldPhoneNumber,String newPhoneNumber);

    //修改用户密码
    boolean updateAdminPassword(Integer aId,String oldPassword,String newPassword,String newPassword2);

    //找回密码
    boolean backAdminPassword(String phoneNumber,String newPassword,String newPassword2);

    //查询全部用户数量
    Integer selectAllAdminNum();

    //数据加密
    String dataEncryption(String data);

    //数据解密
    String dataDecryption(String data);

}
