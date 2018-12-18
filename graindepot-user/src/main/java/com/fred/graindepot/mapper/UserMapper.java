package com.fred.graindepot.mapper;

import com.fred.graindepot.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.Map;

/**
 * Created by Administrator on 2018-12-5.
 */
public interface UserMapper extends Mapper<User> {
    public User getUserById(@Param("id") Integer id);
}
