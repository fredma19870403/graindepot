package com.zhoubi.graindepot.rpc;


import com.zhoubi.graindepot.entity.UserInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * ${DESCRIPTION}
 *
 * @author administrator
 * @create 2017-06-21 8:11
 */
@FeignClient("graindepot-user")
@RequestMapping("api")
public interface IUserService {
    @RequestMapping(value = "/user/username/{username}", method = RequestMethod.GET)
    public UserInfo getUserByUsername(@PathVariable("username") String username);


}
