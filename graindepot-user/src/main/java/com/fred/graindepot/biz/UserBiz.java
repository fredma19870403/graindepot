package com.fred.graindepot.biz;

import com.fred.graindepot.constant.UserConstant;
import com.fred.graindepot.entity.User;
import com.fred.graindepot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Administrator on 2018-12-5.
 */
@Service
public class UserBiz extends BaseBiz<UserMapper,User> {
    @Autowired
    private UserMapper userMapper;
    @Override
    public void insertSelective(User entity) {
        String password = new BCryptPasswordEncoder(UserConstant.PW_ENCORDER_SALT).encode(entity.getPassword());
        entity.setPassword(password);
        super.insertSelective(entity);
    }
    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    public User getUserByUsername(String username){
        User user = new User();
        user.setUsername(username);
        return mapper.selectOne(user);
    }
    public User getUserById(Integer userId){

        return mapper.getUserById(Integer.valueOf(userId));
    }
}
