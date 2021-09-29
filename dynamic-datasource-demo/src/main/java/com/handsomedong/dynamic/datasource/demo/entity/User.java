package com.handsomedong.dynamic.datasource.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Created by HandsomeDong on 2021/9/29 23:55
 */
@TableName("user")
@Data
public class User {
    private Integer id;
    private String userName;
}
