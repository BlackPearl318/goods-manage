package org.example.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.pojo.Good;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface GoodMapper {

    //查询全部
    List<Good> selectAll();

    //根据aId查询商品信息
    List<Good> selectByAid(@Param("aId") Integer aId);

    //条件查询
    List<Good> selectByWhere(@Param("gName") String gName,
                             @Param("gBrand") String gBrand,
                             @Param("gPriceUp") BigDecimal gPriceUp,
                             @Param("gPriceDown") BigDecimal gPriceDown,
                             @Param("gNumberUp") Integer gNumberUp,
                             @Param("gNumberDown") Integer gNumberDown,
                             @Param("gFrom") String gFrom,
                             @Param("gTimeUp") Date gTimeUp,
                             @Param("gTimeDown") Date gTimeDown);

    //全部商品按照仓储量升序排序
    List<Good> selectAllByNumberAsc();

    //全部商品按照仓储量降序排序
    List<Good> selectAllByNumberDesc();

    //全部商品按照单价升序排序
    List<Good> selectAllByPriceAsc();

    //全部商品按照单价降序排序
    List<Good> selectAllByPriceDesc();

    //全部商品按照时间升序排序
    List<Good> selectAllByTimeAsc();

    //全部商品按照时间降序排序
    List<Good> selectAllByTimeDesc();

    //用户的全部商品按照仓储量升序排序
    List<Good> selectAllByAdminNumberAsc(@Param("aId") Integer aId);

    //用户的全部商品按照仓储量降序排序
    List<Good> selectAllByAdminNumberDesc(@Param("aId") Integer aId);

    //用户的全部商品按照单价升序排序
    List<Good> selectAllByAdminPriceAsc(@Param("aId") Integer aId);

    //用户的全部商品按照单价降序排序
    List<Good> selectAllByAdminPriceDesc(@Param("aId") Integer aId);

    //用户的全部商品按照时间升序排序
    List<Good> selectAllByAdminTimeAsc(@Param("aId") Integer aId);

    //用户的全部商品按照时间降序排序
    List<Good> selectAllByAdminTimeDesc(@Param("aId") Integer aId);

    //删除单件商品
    boolean deleteByGId(@Param("gId") Integer gId);

    //根据aId删除全部商品信息
    boolean deleteAllByAid(@Param("aId") Integer aId);

    //踩坑:时间类型不可做非空字符串判断，只可做非空判断
    boolean updateByGId(@Param("gId") Integer gId,
                        @Param("gName")String gName,
                        @Param("gBrand") String gBrand,
                        @Param("gPrice") BigDecimal gPrice,
                        @Param("gNumber") Integer gNumber,
                        @Param("gFrom") String gFrom,
                        @Param("gTime") Date gTime);


    //添加商品
    boolean addOne(@Param("gName")String gName,
                   @Param("gBrand") String gBrand,
                   @Param("gPrice") BigDecimal gPrice,
                   @Param("gNumber") Integer gNumber,
                   @Param("gFrom") String gFrom,
                   @Param("gTime") Date gTime,
                   @Param("aId") Integer aId);


    //统计总库存量
    Integer gNumberSumAll();

    //查询指定a_id的总库存量
    Integer gNumberSumByAid(@Param("aId") Integer aId);

    //统计全部商品的平均价格
    BigDecimal gPriceAvgAll();

    //查询指定a_id的商品的平均价格
    BigDecimal gPriceAvgByAid(@Param("aId") Integer aId);

    //查询不同用户的不同的库存量
    @MapKey("a_id")
    List<Map<String,Integer>> gNumberSumByAidGroup();

    //查询不同用户的商品的平均单价
    @MapKey("a_id")
    List<Map<String,Double>> gPriceAvgByAidGroup();


}
