package org.example.service;

import com.github.pagehelper.PageHelper;
import org.bouncycastle.util.encoders.Base64;
import org.example.mapper.AdminMapper;
import org.example.pojo.Admin;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class AdminServiceImp implements AdminService{

    @Resource
    private AdminMapper adminMapper;

    //查询全部
    @Override
    public List<Admin> selectAll(Integer currentPage,Integer pageSize) {
        //分页操作
        PageHelper.startPage(currentPage,pageSize);
        return adminMapper.selectAll();
    }

    //根据aId查询
    @Override
    public Admin selectByAId(Integer aId) {
        Admin admin = adminMapper.selectByAId(aId);
        if(admin == null){
            return null;
        }else{
            //将密码加密
            admin.setAPassword(this.dataEncryption(admin.getAPassword()));
            //将手机号加密
            admin.setAPhone(this.dataEncryption(admin.getAPhone()));
        }
        return admin;
    }

    //根据账号密码验证用户的身份
    @Override
    public Admin selectByAccountAndPassword(String account, String password) {
        Admin admin = adminMapper.selectByAccountAndPassword(account, password);
        if(admin == null){
            return null;
        }else{
            //将密码加密
            admin.setAPassword(this.dataEncryption(admin.getAPassword()));
            //将手机号加密
            admin.setAPhone(this.dataEncryption(admin.getAPhone()));
        }
        return admin;
    }

    //注册用户
    @Override
    @Transactional
    public boolean createAdmin(String phoneNumber, String password, String password2, String aName) {

        //判断两次密码的输入是否相同
        if(password.equals(password2)){

            //随机生成账号
            Random random = new Random();
            Integer number = random.nextInt(99999999, 1000000000);
            String aAccount= number.toString();

            //获取当前时间
            Date aTime = new Date();

            //处理账号重复问题
            while(true){
                Admin admin = adminMapper.checkAccount(aAccount);
                if(admin != null ){
                    number=number+1;
                    if(number>=1000000000){
                        number=-99999999;
                    }
                    aAccount=number.toString();
                }else{
                    break;
                }
            }

            //手机号重复注册的问题
            Admin admin = adminMapper.checkPhoneNumber(phoneNumber);
            if(admin != null){
                return false;
            }else{
                //添加
                return adminMapper.createAdmin(aAccount,phoneNumber,password,aTime,aName);
            }

        }else{
            return false;
        }

    }

    //根据手机号查询用户信息
    @Override
    public Admin selectByPhone(String aPhone) {
        return adminMapper.selectByPhone(aPhone);
    }


    //修改用户名
    @Override
    @Transactional
    public boolean updateAdminName(Integer aId, String aName) {
        Admin admin = adminMapper.selectByAId(aId);
        if(admin == null){
            return false;
        }else {
            return adminMapper.updateAdminName(aId,aName);
        }
    }

    //修改用户手机号
    @Override
    @Transactional
    public boolean updateAdminPhoneNumber(Integer aId,String oldPhoneNumber, String newPhoneNumber) {
        //根据aId查询用户
        Admin admin = adminMapper.selectByPhone(oldPhoneNumber);
        /*
         * 必须对admin1做非空判断
         */
        if(admin == null){
            return false;
        }else{
            //修改用户信息
            return adminMapper.updateAdminPhoneNumber(aId,newPhoneNumber);
        }
    }

    //修改用户密码
    @Override
    @Transactional
    public boolean updateAdminPassword(Integer aId, String oldPassword,String newPassword,String newPassword2) {
        //判断两次密码的输入是否相同
        if(newPassword.equals(newPassword2)){
            //根据aId查询用户
            Admin admin1 = adminMapper.selectByAId(aId);
            /*
             * 必须对admin1做非空判断
             */
            if(admin1 == null){
                return false;
            }else{
                //获取当前账号信息
                String aAccount=admin1.getAAccount();
                //判断当前用户输入的密码是否匹配
                Admin admin = adminMapper.selectByAccountAndPassword(aAccount, oldPassword);
                if(admin == null){
                    //匹配失败直接返回false
                    return false;
                }else{
                    //修改用户密码
                    return adminMapper.updateAdminPassword(admin.getAId(),newPassword);
                }
            }
        }else{
            return false;
        }
    }

    //找回密码
    @Override
    @Transactional
    public boolean backAdminPassword(String phoneNumber, String newPassword,String newPassword2) {
        //判断两次密码的输入是否相同
        if(newPassword.equals(newPassword2)){
            //根据aId查询用户
            Admin admin = adminMapper.selectByPhone(phoneNumber);
            /*
             * 必须对admin1做非空判断
             */
            if(admin == null){
                return false;
            }else{
                //修改用户密码
                return adminMapper.backAdminPassword(phoneNumber,newPassword);
            }
        }else{
            return false;
        }

    }


    //查询全部用户数量
    @Override
    public Integer selectAllAdminNum() {
        return adminMapper.selectAllAdminNum();
    }

    //数据加密
    //使用DES算法
    @Override
    public String dataEncryption(String data) {
        //原始密钥 64位 8个字节
        String originalKey = "kv7@4^%$";
        //根据原始密钥创建的新密钥
        SecretKeySpec newKey = new SecretKeySpec(originalKey.getBytes(),"DES");

        //加密后的密文
        String encode="";

        //加密
        //获取指定的加密算法的工具类
        try {
            Cipher des = Cipher.getInstance("DES");
            //初始化工具类
            //Cipher.ENCRYPT_MODE 意为 加密模式
            des.init(Cipher.ENCRYPT_MODE,newKey);
            //对明文进行加密
            byte[] bytes = des.doFinal(data.getBytes());
            //使用base64防止乱码
            encode = Base64.toBase64String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //返回密文
        return encode;
    }

    //数据解密
    //使用DES算法
    @Override
    public String dataDecryption(String data) {

        //原始密钥 64位 8个字节
        String originalKey = "kv7@4^%$";
        //根据原始密钥创建的新密钥
        SecretKeySpec newKey = new SecretKeySpec(originalKey.getBytes(),"DES");

        //解密后的密文
        String encode="";

        //加密
        //获取指定的加密算法的工具类
        try {
            Cipher des = Cipher.getInstance("DES");
            //初始化工具类
            //Cipher.DECRYPT_MODE 意为 解密模式
            des.init(Cipher.DECRYPT_MODE,newKey);
            //对密文进行解密
            byte[] decode = Base64.decode(data);
            byte[] bytes = des.doFinal(decode);
            encode=new String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回明文
        return encode;
    }


}
