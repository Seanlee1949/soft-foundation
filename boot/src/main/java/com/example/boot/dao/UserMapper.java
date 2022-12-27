package com.example.boot.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.boot.entity.dto.UserInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author lishuai
 * @since 2022/11/24
 */
@Repository
@Mapper
public interface UserMapper extends BaseMapper<UserInfo> {
    @Delete("delete from SF_USER where USER_NAME = #{userName}")
    void deleteByUserName(String userName);

    @Select("select * from SF_USER where USER_NAME = #{userName}")
    UserInfo selectByUserName(String userName);
}
