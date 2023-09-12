package org.example.service;

import org.apache.ibatis.annotations.Param;
import org.example.pojo.Good;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface GoodService {

    //查询全部
    List<Good> selectAll(Integer currentPage,Integer pageSize);

    //查询全部的总条目数
    Integer selectAllNumber();

    //根据aId查询商品信息
    List<Good> selectByAid(Integer currentPage,Integer pageSize,Integer aId);

    //全部商品按照仓储量升序排序
    List<Good> selectAllByNumberAsc(Integer currentPage,Integer pageSize);

    //全部商品按照仓储量降序排序
    List<Good> selectAllByNumberDesc(Integer currentPage,Integer pageSize);

    //全部商品按照单价升序排序
    List<Good> selectAllByPriceAsc(Integer currentPage,Integer pageSize);

    //全部商品按照单价降序排序
    List<Good> selectAllByPriceDesc(Integer currentPage,Integer pageSize);

    //全部商品按照时间升序排序
    List<Good> selectAllByTimeAsc(Integer currentPage,Integer pageSize);

    //全部商品按照时间降序排序
    List<Good> selectAllByTimeDesc(Integer currentPage,Integer pageSize);

    //用户的全部商品按照仓储量升序排序
    List<Good> selectAllByAdminNumberAsc(Integer currentPage,Integer pageSize,Integer aId);

    //用户的全部商品按照仓储量降序排序
    List<Good> selectAllByAdminNumberDesc(Integer currentPage,Integer pageSize,Integer aId);

    //用户的全部商品按照单价升序排序
    List<Good> selectAllByAdminPriceAsc(Integer currentPage,Integer pageSize,Integer aId);

    //用户的全部商品按照单价降序排序
    List<Good> selectAllByAdminPriceDesc(Integer currentPage,Integer pageSize,Integer aId);

    //用户的全部商品按照时间升序排序
    List<Good> selectAllByAdminTimeAsc(Integer currentPage,Integer pageSize,Integer aId);

    //用户的全部商品按照时间降序排序
    List<Good> selectAllByAdminTimeDesc(Integer currentPage,Integer pageSize,Integer aId);

    //查询对应aId查询出的总条目数
    Integer selectByAidNumber(Integer aId);

    //条件查询
    List<Good> selectByWhere(String gName,
                             String gBrand,
                             BigDecimal gPriceUp,
                             BigDecimal gPriceDown,
                             Integer gNumberUp,
                             Integer gNumberDown,
                             String gFrom,
                             Date gTimeUp,
                             Date gTimeDown,
                             Integer currentPage,
                             Integer pageSize);

    //条件查询的总条目数
    Integer selectByWhereNumber(String gName,
                                String gBrand,
                                BigDecimal gPriceUp,
                                BigDecimal gPriceDown,
                                Integer gNumberUp,
                                Integer gNumberDown,
                                String gFrom,
                                Date gTimeUp,
                                Date gTimeDown);

    //删除单件商品
    boolean deleteByGId (Integer gId);

    //根据aId删除全部商品信息
    boolean deleteAllByAid(Integer aId);

    //修改
    boolean updateByGId(Good good);

    //添加
    boolean addOne(Good good);

    //统计总库存量
    Integer gNumberSumAll();

    //查询指定a_id的总库存量
    Integer gNumberSumByAid(Integer aId);

    //统计全部商品的平均价格
    BigDecimal gPriceAvgAll();

    //查询指定a_id的商品的平均价格
    BigDecimal gPriceAvgByAid(@Param("aId") Integer aId);

    //查询不同用户的不同的库存量
    List<Map<String,Integer>> gNumberSumByAidGroup();

    //查询不同用户的商品的平均单价
    List<Map<String,Double>> gPriceAvgByAidGroup();
}
