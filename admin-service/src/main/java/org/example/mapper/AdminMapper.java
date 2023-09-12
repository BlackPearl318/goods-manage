package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.pojo.Admin;

import java.util.Date;
import java.util.List;

@Mapper
public interface AdminMapper {

    //查询全部用户
    List<Admin> selectAll();

    //指定aId查询单个用户
    Admin selectByAId(@Param("aId") Integer aId);

    //登录
    Admin selectByAccountAndPassword(@Param("account")String account,@Param("password")String password);

    //创建用户
    boolean createAdmin(@Param("aAccount") String aAccount,
                        @Param("phoneNumber") String phoneNumber,
                        @Param("password") String password,
                        @Param("aTime") Date aTime,
                        @Param("aName") String aName);


    //根据手机号查询用户信息
    Admin selectByPhone(@Param("aPhone") String aPhone);
    //检测账号是否存在
    Admin checkAccount(@Param("aAccount") String aAccount);
    //检测手机号是否被注册
    Admin checkPhoneNumber(@Param("phoneNumber") String phoneNumber);

    //修改用户名
    boolean updateAdminName(@Param("aId") Integer aId, @Param("aName") String aName);

    //修改用户手机号
    boolean updateAdminPhoneNumber(@Param("aId") Integer aId, @Param("newPhoneNumber") String newPhoneNumber);

    //修改用户密码
    boolean updateAdminPassword(@Param("aId") Integer aId, @Param("password") String password);

    //找回密码
    boolean backAdminPassword(@Param("phoneNumber") String phoneNumber, @Param("password") String password);

    //查询全部用户数量
    Integer selectAllAdminNum();


}
