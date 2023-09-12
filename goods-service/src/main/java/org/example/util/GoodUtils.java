package org.example.util;

import com.alibaba.fastjson.JSON;
import org.example.client.AdminClient;
import org.example.pojo.Admin;
import org.example.pojo.Good;
import org.example.pojo.Result;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

//工具类
@Component
public class GoodUtils {

    @Resource
    private  AdminClient adminClient;

    //向Good里设置admin信息
    //无返回值也可以生效
    public List<Good> setAdminInfo(List<Good> goods){

        if(goods != null){
            //插入对应的admin信息
            for(Good good:goods){

                if(good.getAId() != null){
                    Result result = adminClient.selectByAid(good.getAId());

                    //将获取的result中的Data属性转化为json字符串
                    String s = JSON.toJSONString(result.getData());
                    //将转化后的Data属性的字符串转化为一个Admin对象
                    Admin admin = JSON.parseObject(s, Admin.class);
                    //将admin实例set进good里
                    good.setAdmin(admin);

                }else{
                    return null;
                }

            }
            //返回
            return goods;

        }else{
            return null;
        }

    }

    //将一个json字符串反序列化为一个Good对象
    public Good getGoodByJSON(String data){
        return JSON.parseObject(data, Good.class);
    }


}


