package com.example.boot.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.boot.entity.dto.Record;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author lishuai
 * @since 2022/11/27
 */
@Repository
@Mapper
public interface RecordMapper extends BaseMapper<Record> {
}
