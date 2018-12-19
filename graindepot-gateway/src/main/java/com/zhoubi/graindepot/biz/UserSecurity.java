package com.zhoubi.graindepot.biz;

import com.zhoubi.graindepot.entity.UserInfo;
import com.zhoubi.graindepot.rpc.IUserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * ${DESCRIPTION}
 *
 * @author administrator
 * @create 2017-06-21 8:39
 */
@Service
public class UserSecurity {
    @Lazy
    @Autowired
    private IUserService userService;

    @HystrixCommand(fallbackMethod = "fallbackMethod")
    public UserInfo getUserByUsername(String username) {
        return userService.getUserByUsername(username);
    }

    public UserInfo fallbackMethod(String username) {
        return new UserInfo();
    }
}
