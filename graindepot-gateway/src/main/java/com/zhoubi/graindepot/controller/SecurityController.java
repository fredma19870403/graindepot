package com.zhoubi.graindepot.controller;

import com.zhoubi.graindepot.config.DefinedSessionRegistry;
import com.zhoubi.graindepot.rpc.IUserService;
import com.zhoubi.graindepot.util.RSAUtils;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.interfaces.RSAPublicKey;

/**
 * Created by Administrator on 2018-12-5.
 */
@Controller
public class SecurityController {
    @Autowired
    private IUserService userService;
    @Autowired
    private DefinedSessionRegistry sessionRegistry;
    public void addRSA(HttpServletRequest request){
        // 将公钥的 modulus 和 exponent 传给页面。
        // Hex -> apache commons-codec
        //System.out.println("sppp");
        RSAPublicKey publicKey = RSAUtils.getDefaultPublicKey();
        request.getSession().setAttribute("modulus", new String(Hex.encodeHex(publicKey.getModulus().toByteArray())));
        request.getSession().setAttribute("exponent", new String(Hex.encodeHex(publicKey.getPublicExponent().toByteArray())));
        //System.out.println("......sppp");

    }
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, HttpServletRequest request, HttpServletResponse response
            ,HttpSession session
            , @RequestParam(required = false) String error) {
        addRSA(request);
        return "login";
    }
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(Model model, HttpServletRequest request, HttpServletResponse response
            ,HttpSession session
            , @RequestParam(required = false) String error) {

        return "main";
    }
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String toindex(Model model, HttpServletRequest request, HttpServletResponse response
            ,HttpSession session
            , @RequestParam(required = false) String error) throws IOException {
        return "index";
    }
    @RequestMapping(value = "/logout")
    public String logout(Model model, HttpServletRequest request, HttpSession session) {
        SessionInformation sessionInformation = sessionRegistry.getSessionInformation(session.getId());
        if (sessionInformation != null) {
            //将退出记录到表中
            User user = (User) sessionInformation.getPrincipal();
        }
        sessionRegistry.removeSessionInformation(session.getId());
        return "login";
    }
}
