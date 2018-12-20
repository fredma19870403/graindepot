package com.zhoubi.graindepot.filter;

import com.alibaba.fastjson.JSON;
import com.zhoubi.graindepot.rpc.IUserService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * ${DESCRIPTION}
 *
 * @author administrator
 * @create 2017-06-23 8:25
 */
@Component
@Slf4j
public class SessionAccessFilter extends ZuulFilter {
    @Autowired
    private SessionRepository<?> repository;
    @Autowired
    private IUserService userService;

    @Value("${gate.ignore.startWith}")
    private String startWith;
    @Value("${gate.ignore.contain}")
    private String contain;
    @Value("${gate.oauth.prefix}")
    private String oauthPrefix;

    public SessionAccessFilter() {
        super();
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpSession httpSession = ctx.getRequest().getSession();
        HttpServletRequest request = ctx.getRequest();
        final String requestUri = request.getRequestURI();
        final String method = request.getMethod();
        User user = getSessionUser(httpSession);
        String username = null;
        if (user != null) {
            username = user.getUsername();
            setCurrentUser(httpSession, username);
            // 设置头部校验信息
            ctx.addZuulRequestHeader("Authorization",
                    Base64Utils.encodeToString(user.getUsername().getBytes()));

            //将当前用户放入头部校验信息
            ctx.addZuulRequestHeader("X-AUTH-ID",
                    Base64Utils.encodeToString(((String) httpSession.getAttribute("currentUser")).getBytes()));

            // 查找合法链接
        }// 不进行拦截的地址


        if (isStartWith(requestUri) || isContains(requestUri) || isOAuth(requestUri)) {
            return null;
        }
        return null;
    }

    /**
     * 判定是否oauth资源
     *
     * @param requestUri
     * @return
     */
    private boolean isOAuth(String requestUri) {
        return requestUri.startsWith(oauthPrefix);
    }

    /**
     * 返回session中的用户信息
     *
     * @param httpSession
     * @return
     */
    private User getSessionUser(HttpSession httpSession) {
        Session session = repository.getSession(httpSession.getId());
        if (httpSession.getAttribute("SPRING_SECURITY_CONTEXT") == null)
            return null;
        SecurityContextImpl securityContextImpl =
                (SecurityContextImpl) httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
        return (User) securityContextImpl.getAuthentication().getPrincipal();
    }

    private void setCurrentUser(HttpSession httpSession, String username) {
        if (httpSession.getAttribute("currentUser") == null) {
            httpSession.setAttribute("currentUser", JSON.toJSONString(userService.getUserByUsername(username)));
        }
    }


    /**
     * 是否包含某种特征
     *
     * @param requestUri
     * @return
     */
    private boolean isContains(String requestUri) {
        boolean flag = false;
        for (String s : contain.split(",")) {
            if (requestUri.contains(s))
                return true;
        }
        return flag;
    }


    /**
     * URI是否以什么打头
     *
     * @param requestUri
     * @return
     */
    private boolean isStartWith(String requestUri) {
        boolean flag = false;
        for (String s : startWith.split(",")) {
            if (requestUri.startsWith(s))
                return true;
        }
        return flag;
    }

    /**
     * Reports an error message given a response body and code.
     *
     * @param body
     * @param code
     */
    private void setFailedRequest(HttpServletRequest request,String body, int code) {
        //log.debug("Reporting error ({}): {}", code, body);

        RequestContext ctx = RequestContext.getCurrentContext();
        String requestType = request.getHeader("X-Requested-With");
        if("XMLHttpRequest".equals(requestType)){
            ctx.setResponseStatusCode(403);
        }else{
            ctx.setResponseStatusCode(200);
        }
        ctx.getResponse().addHeader("noPermissions","true");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/noPermissions");

        if (dispatcher!=null) {
            try {
                dispatcher.forward(request,ctx.getResponse());
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
