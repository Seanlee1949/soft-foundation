package com.example.boot.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.boot.entity.dto.HistoryDetailData;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lishuai
 * @since 2022/11/27
 */
@Repository
@Mapper
public interface HistoryDetailDataMapper extends BaseMapper<HistoryDetailData> {
    @Select("select * from SF_HISTORY_DETAIL_DATA where DEVICE_KEY = #{deviceKeyTemp} AND PILE_DESCRIBE = #{pileDescribeTemp}")
    List<HistoryDetailData> selectByDeviceKey(String deviceKeyTemp, String pileDescribeTemp);

    @Select("select ID from SF_HISTORY_DETAIL_DATA where DEVICE_KEY = #{deviceKeyTemp} AND PILE_DESCRIBE = #{pileDescribeTemp}")
    List<String> selectIdsByDeviceKey(String deviceKeyTemp, String pileDescribeTemp);

    @Delete("delete from SF_HISTORY_DETAIL_DATA  where pile_describe = #{pileDescribe}")
    void deleteByPileDescribe(String pileDescribe);

    @Select("select * from SF_HISTORY_DETAIL_DATA limit #{offset},#{limit} ")
    List<HistoryDetailData> selectByOffset(long offset, long limit);

//   default void insertBatch(List<HistoryDetailData> historyDetailDataList){
//       QueryWrapper<HistoryDetailData> queryWrapper = new QueryWrapper<>();
//        queryWrapper.insert(historyDetailDataList, queryWrapper);
//    }


    default void batchInsert(List<HistoryDetailData> historyDetailDataList) {


    }
}
