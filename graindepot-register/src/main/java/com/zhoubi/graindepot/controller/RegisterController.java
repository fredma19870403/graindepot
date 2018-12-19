package com.zhoubi.graindepot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2018-12-19.
 */
@Controller
@RequestMapping("register")
public class RegisterController {
    @RequestMapping(value="/toRegister",method = RequestMethod.GET)
    public String toRegister(Model model, HttpServletRequest request, HttpServletResponse response){
        System.out.println("111111111");
        return "register";
    }
}
