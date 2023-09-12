package org.example.service;

import com.github.pagehelper.PageHelper;
import org.example.mapper.GoodMapper;
import org.example.pojo.Good;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class GoodServiceImp implements GoodService{

    @Resource
    private GoodMapper goodMapper;

    //查询全部
    @Override
    public List<Good> selectAll(Integer currentPage,Integer pageSize) {
        //分页操作
        PageHelper.startPage(currentPage,pageSize);
        return goodMapper.selectAll();
    }

    //查询全部的总条目数
    @Override
    public Integer selectAllNumber() {
        return goodMapper.selectAll().size();
    }

    //根据aId查询商品信息
    @Override
    public List<Good> selectByAid(Integer currentPage,Integer pageSize,Integer aId){
        //分页操作
        PageHelper.startPage(currentPage,pageSize);
        return goodMapper.selectByAid(aId);
    }

    //全部商品按照仓储量升序排序
    @Override
    public List<Good> selectAllByNumberAsc(Integer currentPage,Integer pageSize) {
        //分页操作
        PageHelper.startPage(currentPage,pageSize);
        return goodMapper.selectAllByNumberAsc();
    }

    //全部商品按照仓储量降序排序
    @Override
    public List<Good> selectAllByNumberDesc(Integer currentPage,Integer pageSize) {
        //分页操作
        PageHelper.startPage(currentPage,pageSize);
        return goodMapper.selectAllByNumberDesc();
    }

    //全部商品按照单价升序排序
    @Override
    public List<Good> selectAllByPriceAsc(Integer currentPage,Integer pageSize){
        //分页操作
        PageHelper.startPage(currentPage,pageSize);
        return goodMapper.selectAllByPriceAsc();
    }

    //全部商品按照单价降序排序
    @Override
    public List<Good> selectAllByPriceDesc(Integer currentPage,Integer pageSize){
        //分页操作
        PageHelper.startPage(currentPage,pageSize);
        return goodMapper.selectAllByPriceDesc();
    }

    //全部商品按照时间升序排序
    @Override
    public List<Good> selectAllByTimeAsc(Integer currentPage,Integer pageSize){
        //分页操作
        PageHelper.startPage(currentPage,pageSize);
        return goodMapper.selectAllByTimeAsc();
    }

    //全部商品按照时间降序排序
    @Override
    public List<Good> selectAllByTimeDesc(Integer currentPage,Integer pageSize){
        //分页操作
        PageHelper.startPage(currentPage,pageSize);
        return goodMapper.selectAllByTimeDesc();
    }

    //用户的全部商品按照仓储量升序排序
    @Override
    public List<Good> selectAllByAdminNumberAsc(Integer currentPage,Integer pageSize,Integer aId){
        //分页操作
        PageHelper.startPage(currentPage,pageSize);
        return goodMapper.selectAllByAdminNumberAsc(aId);
    }

    //用户的全部商品按照仓储量降序排序
    @Override
    public List<Good> selectAllByAdminNumberDesc(Integer currentPage,Integer pageSize,Integer aId){
        //分页操作
        PageHelper.startPage(currentPage,pageSize);
        return goodMapper.selectAllByAdminNumberDesc(aId);
    }

    //用户的全部商品按照单价升序排序
    @Override
    public List<Good> selectAllByAdminPriceAsc(Integer currentPage,Integer pageSize,Integer aId){
        //分页操作
        PageHelper.startPage(currentPage,pageSize);
        return goodMapper.selectAllByAdminPriceAsc(aId);
    }

    //用户的全部商品按照单价降序排序
    @Override
    public List<Good> selectAllByAdminPriceDesc(Integer currentPage,Integer pageSize,Integer aId){
        //分页操作
        PageHelper.startPage(currentPage,pageSize);
        return goodMapper.selectAllByAdminPriceDesc(aId);
    }

    //用户的全部商品按照时间升序排序
    @Override
    public List<Good> selectAllByAdminTimeAsc(Integer currentPage,Integer pageSize,Integer aId){
        //分页操作
        PageHelper.startPage(currentPage,pageSize);
        return goodMapper.selectAllByAdminTimeAsc(aId);
    }

    //用户的全部商品按照时间降序排序
    @Override
    public List<Good> selectAllByAdminTimeDesc(Integer currentPage,Integer pageSize,Integer aId){
        //分页操作
        PageHelper.startPage(currentPage,pageSize);
        return goodMapper.selectAllByAdminTimeDesc(aId);
    }

    //查询对应aId查询出的总条目数
    @Override
    public Integer selectByAidNumber(Integer aId) {
        return goodMapper.selectByAid(aId).size();
    }

    //条件查询
    @Override
    public List<Good> selectByWhere(String gName,
                                    String gBrand,
                                    BigDecimal gPriceUp,
                                    BigDecimal gPriceDown,
                                    Integer gNumberUp,
                                    Integer gNumberDown,
                                    String gFrom,
                                    Date gTimeUp,
                                    Date gTimeDown,
                                    Integer currentPage,
                                    Integer pageSize) {

        if(currentPage==null){
            currentPage=1;
        }
        if(pageSize==null){
            pageSize=10;
        }

        //分页操作
        PageHelper.startPage(currentPage,pageSize);

        return goodMapper.selectByWhere(gName,gBrand,gPriceUp,gPriceDown
                ,gNumberUp,gNumberDown,gFrom,gTimeUp,gTimeDown);

    }

    //条件查询的总条目数
    @Override
    public Integer selectByWhereNumber(String gName, String gBrand, BigDecimal gPriceUp, BigDecimal gPriceDown, Integer gNumberUp, Integer gNumberDown, String gFrom, Date gTimeUp, Date gTimeDown) {
        return goodMapper.selectByWhere(gName,gBrand,gPriceUp,gPriceDown
                ,gNumberUp,gNumberDown,gFrom,gTimeUp,gTimeDown).size();
    }


    //删除单件商品
    @Override
    @Transactional
    public boolean deleteByGId(Integer gId) {
        if(gId == null){
            gId=0;
        }
        return goodMapper.deleteByGId(gId);
    }

    //根据aId删除全部商品信息
    @Override
    @Transactional
    public boolean deleteAllByAid(Integer aId){
        if(aId == null){
            aId=0;
        }
        return goodMapper.deleteAllByAid(aId);
    }

    //修改
    @Override
    @Transactional
    public boolean updateByGId(Good good) {

        Integer gId = good.getGId();
        String gName = good.getGName();
        String gBrand = good.getGBrand();
        Integer gNumber = good.getGNumber();
        BigDecimal gPrice = good.getGPrice();
        String gFrom = good.getGFrom();
        Date gTime = good.getGTime();

        return goodMapper.updateByGId(gId,gName,gBrand,gPrice,gNumber,gFrom,gTime);
    }


    //添加
    @Override
    @Transactional
    public boolean addOne(Good good) {

        String gName = good.getGName();
        String gBrand = good.getGBrand();
        Integer gNumber = good.getGNumber();
        BigDecimal gPrice = good.getGPrice();
        String gFrom = good.getGFrom();
        Date gTime = good.getGTime();
        Integer aId = good.getAId();

        return goodMapper.addOne(gName,gBrand,gPrice,gNumber,gFrom,gTime,aId);
    }

    //统计总库存量
    @Override
    public Integer gNumberSumAll() {
        Integer number=goodMapper.gNumberSumAll();
        //如果数值不存在，则返回0
        if(number == null){
            return 0;
        }
        return number;

    }

    //查询指定a_id的总库存量
    @Override
    public Integer gNumberSumByAid(Integer aId) {
        Integer number= goodMapper.gNumberSumByAid(aId);
        //如果数值不存在，则返回0
        if(number == null){
            return 0;
        }
        return number;

    }

    //统计全部商品的平均价格
    @Override
    public BigDecimal gPriceAvgAll() {
        BigDecimal number = goodMapper.gPriceAvgAll();
        if(number == null){
            return new BigDecimal(0);
        }else{
            //四舍五入保留两位小数
            return number.setScale(2, RoundingMode.HALF_UP);
        }
    }

    //查询指定a_id的商品的平均价格
    @Override
    public BigDecimal gPriceAvgByAid(Integer aId) {
        BigDecimal number = goodMapper.gPriceAvgByAid(aId);
        if(number == null){
            return new BigDecimal(0);
        }else{
            //四舍五入保留两位小数
            return number.setScale(2, RoundingMode.HALF_UP);
        }
    }

    //查询不同用户的不同的库存量
    @Override
    public List<Map<String,Integer>> gNumberSumByAidGroup() {
        return goodMapper.gNumberSumByAidGroup();
    }

    //查询不同用户的商品的平均单价
    @Override
    public List<Map<String,Double>> gPriceAvgByAidGroup() {

        List<Map<String, Double>> maps = goodMapper.gPriceAvgByAidGroup();

        if(maps != null){

            for(Map<String, Double> map: maps){
                //根据key获取value
                Double avgS = map.get("avgs");
                //将value的小数进行四舍五入
                BigDecimal avgNew = new BigDecimal(avgS.toString());
                BigDecimal bigDecimal = avgNew.setScale(2, RoundingMode.HALF_UP);
                //将修改完成后的values替换原来的values
                map.remove("avgs");
                map.put("avgs",bigDecimal.doubleValue());
            }
            return maps;

        }else{
            return null;
        }

    }


}
