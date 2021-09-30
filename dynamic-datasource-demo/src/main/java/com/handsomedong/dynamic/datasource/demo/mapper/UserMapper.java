package com.handsomedong.dynamic.datasource.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.handsomedong.dynamic.datasource.annotation.DataSource;
import com.handsomedong.dynamic.datasource.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by HandsomeDong on 2021/9/29 23:58
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    List<User> getUserList();

    @DataSource("HandsomeDong")
    List<User> getUserListFromHandsomeDong();
}
