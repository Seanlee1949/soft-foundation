package com.example.boot.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.boot.entity.dto.HistoryData;
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
public interface HistoryDataMapper extends BaseMapper<HistoryData> {
    @Select("select * from SF_HISTORY_DATA where DEVICE_TYPE = #{deviceType}")
    List<HistoryData> selectByDeviceType(String deviceType);
}
