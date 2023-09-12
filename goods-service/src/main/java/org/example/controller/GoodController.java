package org.example.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.example.client.AdminClient;
import org.example.pojo.Admin;
import org.example.pojo.Good;
import org.example.pojo.Result;
import org.example.service.GoodServiceImp;
import org.example.util.GoodUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/goods")
//不配置gateway网关时使用解决跨域问题
//@CrossOrigin(origins = "http://localhost:8080",allowCredentials = "true",allowedHeaders = "*")
public class GoodController {

    @Resource
    private GoodServiceImp goodService;

    @Resource
    private AdminClient adminClient;

    @Resource
    private GoodUtils utils;

    public final int G_NUMBER_SUM_ALL = 0;

    public final int G_NUMBER_SUM_BY_AID = 1;

    public final int G_PRICE_AVG_ALL = 2;

    public final int G_PRICE_AVG_BY_AID = 3;

    public final int G_NUMBER_SUM_BY_AID_GROUP = 4;

    public final int G_PRICE_AVG_BY_AID_GROUP = 5;

    public final int G_NUMBER_AVG_ALL = 6;

    public final char SELECT_ALL_BY_NUMBER_ASC = 'A';

    public final char SELECT_ALL_BY_NUMBER_DESC = 'B';

    public final char SELECT_ALL_BY_PRICE_ASC = 'C';

    public final char SELECT_ALL_BY_PRICE_DESC = 'D';

    public final char SELECT_ALL_BY_TIME_ASC = 'E';

    public final char SELECT_ALL_BY_TIME_DESC = 'F';

    public final char SELECT_ALL_BY_ADMIN_NUMBER_ASC = 'a';

    public final char SELECT_ALL_BY_ADMIN_NUMBER_DESC = 'b';

    public final char SELECT_ALL_BY_ADMIN_PRICE_ASC = 'c';

    public final char SELECT_ALL_BY_ADMIN_PRICE_DESC = 'd';

    public final char SELECT_ALL_BY_ADMIN_TIME_ASC = 'e';

    public final char SELECT_ALL_BY_ADMIN_TIME_DESC = 'f';

    //展示全部
    @GetMapping("/select/{currentPage}/{pageSize}")
    public Result showAll(@PathVariable Integer currentPage,@PathVariable Integer pageSize){

        List<Good> goods = goodService.selectAll(currentPage, pageSize);
        //插入admin信息
        List<Good> newGoods = utils.setAdminInfo(goods);
        //返回全部商品数据及总条目数
        return new Result(true,newGoods,goodService.selectAllNumber().toString());
    }

    //根据aId查询商品信息
    @GetMapping("/select/{currentPage}/{pageSize}/{aId}")
    public Result showByAid(@PathVariable Integer currentPage,@PathVariable Integer pageSize,@PathVariable Integer aId){

        List<Good> goods = goodService.selectByAid(currentPage, pageSize,aId);

        //插入对应的admin信息
        List<Good> newGoods = utils.setAdminInfo(goods);
        //返回信息和条目数
        return new Result(true,newGoods,goodService.selectByAidNumber(aId).toString());
    }

    //条件查询
    @GetMapping("/selectByWhere")
    public Result selectByWhere(@RequestParam(value = "gName",required = false)String gName,
                                @RequestParam(value = "gBrand",required = false)String gBrand,
                                @RequestParam(value = "gPriceUp",required = false) BigDecimal gPriceUp,
                                @RequestParam(value = "gPriceDown",required = false) BigDecimal gPriceDown,
                                @RequestParam(value = "gNumberUp",required = false)Integer gNumberUp,
                                @RequestParam(value = "gNumberDown",required = false)Integer gNumberDown,
                                @RequestParam(value = "gFrom",required = false)String gFrom,
                                @RequestParam(value = "gTimeUp",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date gTimeUp,
                                @RequestParam(value = "gTimeDown",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss" ) Date gTimeDown,
                                @RequestParam(value = "currentPage",required = false) Integer currentPage,
                                @RequestParam(value = "pageSize",required = false) Integer pageSize) {

        List<Good> goods = goodService.selectByWhere(gName,gBrand,gPriceUp,gPriceDown
                ,gNumberUp,gNumberDown,gFrom,gTimeUp,gTimeDown,currentPage,pageSize);


        //插入对应的admin信息
        List<Good> newGoods = utils.setAdminInfo(goods);
        //返回数据和条目数
        return new Result(true,newGoods,goodService.selectByWhereNumber(gName,gBrand,gPriceUp,gPriceDown
                ,gNumberUp,gNumberDown,gFrom,gTimeUp,gTimeDown).toString());
    }

    //排序(全部商品)
    @GetMapping("/selectAll/sort/{currentPage}/{pageSize}/{mark}")
    public Result showAllBySort(@PathVariable Integer currentPage,@PathVariable Integer pageSize,@PathVariable char mark){

        List<Good> goods = null;

        if(mark == this.SELECT_ALL_BY_NUMBER_ASC){
            goods = goodService.selectAllByNumberAsc(currentPage, pageSize);
        }else if(mark == this.SELECT_ALL_BY_NUMBER_DESC){
            goods = goodService.selectAllByNumberDesc(currentPage, pageSize);
        }else if(mark == this.SELECT_ALL_BY_PRICE_ASC){
            goods = goodService.selectAllByPriceAsc(currentPage, pageSize);
        }else if(mark == this.SELECT_ALL_BY_PRICE_DESC){
            goods = goodService.selectAllByPriceDesc(currentPage, pageSize);
        }else if(mark == this.SELECT_ALL_BY_TIME_ASC){
            goods = goodService.selectAllByTimeAsc(currentPage, pageSize);
        }else if(mark == this.SELECT_ALL_BY_TIME_DESC){
            goods = goodService.selectAllByTimeDesc(currentPage, pageSize);
        }

        if(goods == null){
            return new Result(false,null,"数据异常");
        }else {
            //插入对应的admin信息
            List<Good> newGoods = utils.setAdminInfo(goods);
            //返回数据和条目数
            return new Result(true,newGoods,goodService.selectAllNumber().toString());
        }

    }


    //排序(个体商品)
    @GetMapping("/selectAll/adminSort/{currentPage}/{pageSize}/{aId}/{adminMark}")
    public Result showAllByAdminSort(@PathVariable Integer currentPage,@PathVariable Integer pageSize,@PathVariable Integer aId,@PathVariable char adminMark){

        List<Good> goods = null;

        if(adminMark == this.SELECT_ALL_BY_ADMIN_NUMBER_ASC){
            goods = goodService.selectAllByAdminNumberAsc(currentPage,pageSize,aId);
        }else if(adminMark == this.SELECT_ALL_BY_ADMIN_NUMBER_DESC){
            goods = goodService.selectAllByAdminNumberDesc(currentPage,pageSize,aId);
        }else if(adminMark == this.SELECT_ALL_BY_ADMIN_PRICE_ASC){
            goods = goodService.selectAllByAdminPriceAsc(currentPage,pageSize,aId);
        }else if(adminMark == this.SELECT_ALL_BY_ADMIN_PRICE_DESC){
            goods = goodService.selectAllByAdminPriceDesc(currentPage,pageSize,aId);
        }else if(adminMark == this.SELECT_ALL_BY_ADMIN_TIME_ASC){
            goods = goodService.selectAllByAdminTimeAsc(currentPage,pageSize,aId);
        }else if(adminMark == this.SELECT_ALL_BY_ADMIN_TIME_DESC){
            goods = goodService.selectAllByAdminTimeDesc(currentPage,pageSize,aId);
        }

        if(goods == null){
            return new Result(false,null,"数据异常");
        }else {
            //插入对应的admin信息
            List<Good> newGoods = utils.setAdminInfo(goods);
            //返回数据和条目数
            return new Result(true,newGoods,goodService.selectByAidNumber(aId).toString());
        }

    }



    //根据gId删除单个商品
    @DeleteMapping("/deleteByGId")
    public Result deleteByGId(@RequestParam("gId") Integer gId){

        boolean b = goodService.deleteByGId(gId);

        if(b){
            return new Result(true,"删除成功");
        }else{
            return new Result(false,"删除失败");
        }

    }

    //根据aId删除全部商品信息
    @DeleteMapping("/deleteAllByAid")
    public Result deleteAllByAid(@RequestParam("aId") Integer aId){

        boolean b = goodService.deleteAllByAid(aId);

        if(b){
            return new Result(true,"删除成功");
        }else{
            return new Result(false,"删除失败");
        }

    }

    //修改商品信息
    @PutMapping("/updateByGId")
    public Result updateByGId(@RequestBody String data) {

        //反序列化,将JSON字符串转化为Good对象
        Good good = utils.getGoodByJSON(data);

        boolean b = goodService.updateByGId(good);

        if(b){
            return new Result(true,"修改成功");
        }else{
            return new Result(false,"修改失败");
        }
    }

    //添加商品
    @PostMapping("/addOne")
    public Result addOne(@RequestBody String data){
        //反序列化,将JSON字符串转化为Good对象
        Good good = utils.getGoodByJSON(data);

        boolean b = goodService.addOne(good);

        if(b){
            return new Result(true,"添加成功");
        }else{
            return new Result(false,"添加失败");
        }

    }


    //数据分析
    @GetMapping("/statisticsAll/{status}/{aId}")
    public Result statisticsAll(@PathVariable Integer status ,@PathVariable Integer aId){

        if(status == this.G_NUMBER_SUM_ALL){

            Integer num = goodService.gNumberSumAll();
            return new Result(true,num,"查询成功");

        }else if(status == this.G_NUMBER_SUM_BY_AID){

            Integer num = goodService.gNumberSumByAid(aId);
            return new Result(true,num,"查询成功");

        }else if(status == this.G_PRICE_AVG_ALL){

            BigDecimal num = goodService.gPriceAvgAll();
            return new Result(true,num,"查询成功");

        }else if(status == this.G_PRICE_AVG_BY_AID){

            BigDecimal num = goodService.gPriceAvgByAid(aId);
            return new Result(true,num,"查询成功");

        }else if(status == this.G_NUMBER_SUM_BY_AID_GROUP){

            List<Map<String, Integer>> maps = goodService.gNumberSumByAidGroup();
            if(maps != null){
                return new Result(true,maps,"查询成功");
            }else{
                return new Result(false,null,"暂无数据");
            }

        }else if(status == this.G_PRICE_AVG_BY_AID_GROUP){

            List<Map<String, Double>> maps = goodService.gPriceAvgByAidGroup();
            if(maps != null){
                return new Result(true,maps,"查询成功");
            }else{
                return new Result(false,null,"暂无数据");
            }

        }else if(status == this.G_NUMBER_AVG_ALL){
            Integer num = goodService.gNumberSumAll();
            Result result = adminClient.selectAllAdminNum();
            if(result == null){
                return new Result(false,null,"暂无数据");
            }else{
                if(result.getData() == null){
                    return new Result(true,0,"查询成功");
                }else{
                    return new Result(true,num/Integer.parseInt(result.getData().toString()),"查询成功");
                }
            }
        }else{
            return new Result(false,null,"传参错误");
        }
    }





}
