package org.example.test;

import org.example.mapper.GoodMapper;
import org.example.pojo.Good;
import org.example.service.GoodServiceImp;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class Test1 {

    @Resource
    private GoodMapper goodMapper;

    @Resource
    private GoodServiceImp goodService;

    @Test
    public void test1(){

        List<Map<String, Double>> maps = goodService.gPriceAvgByAidGroup();
        System.out.println(maps);
        //boolean b = goodMapper.addOne("洛杉矶", "而忽视", 12, 5678, null, null, null);
        //System.out.println("添加结果"+b);


    }

    @Test
    public void test2(){
//
//        List<Good> goods = goodService.selectAllByNumberDesc(1, 10);
//        goods.forEach(System.out::println);

    }
}
