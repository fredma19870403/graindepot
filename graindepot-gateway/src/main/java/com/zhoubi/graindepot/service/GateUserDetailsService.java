package com.zhoubi.graindepot.service;

import com.zhoubi.graindepot.biz.UserSecurity;
import com.zhoubi.graindepot.entity.UserInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

/**
 * ${DESCRIPTION}
 *
 * @author administrator
 * @create 2017-06-22 13:00
 */
@Service
public class GateUserDetailsService implements UserDetailsService {
    @Autowired
    private UserSecurity userSecurity;


    @Autowired
    private HttpServletRequest request;
/*    @Autowired
    private SessionRegistry sessionRegistry;*/
    @Autowired
    private HttpServletResponse response;

    private Integer maxOnlineNum;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isBlank(username)) {
            throw new RuntimeException("用户名为空");
        }
        UserInfo info = userSecurity.getUserByUsername(username);
        if (info.getUsername() == null)
            throw new RuntimeException("用户名不存在");

        request.setAttribute("username", username);

        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        return new User(username, info.getPassword(),
                true, // 是否可用
                true, // 是否过期
                true, // 证书不过期为true
                true, // 账户未锁定为true
                authorities);
    }
}
