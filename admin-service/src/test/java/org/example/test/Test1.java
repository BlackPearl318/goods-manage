package org.example.test;

import org.example.mapper.AdminMapper;
import org.example.service.AdminServiceImp;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class Test1 {

    @Resource
    private AdminMapper adminMapper;
    @Resource
    private AdminServiceImp adminServiceImp;

    @Test
    public void test(){
        String s1 = adminServiceImp.dataEncryption("01zDq34Z");
        String s2 = adminServiceImp.dataDecryption("vJq16mZY/JeyNleriL0Hgw==");
        System.out.println(s1);
        System.out.println(s2);
    }


}
