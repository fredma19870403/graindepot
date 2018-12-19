package com.zhoubi.graindepot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by zhanghao on 2018/8/1.
 */
@Component
public class DefinedSessionRegistry extends SessionRegistryImpl {
    @Autowired
    private HttpServletRequest request;

    @Override
    public void onApplicationEvent(SessionDestroyedEvent event) {
        SessionInformation sessionInformation = getSessionInformation(event.getId());
        if (sessionInformation != null) {
            //将退出记录到表中
            User user = (User) sessionInformation.getPrincipal();
        }
        super.onApplicationEvent(event);
    }
}
