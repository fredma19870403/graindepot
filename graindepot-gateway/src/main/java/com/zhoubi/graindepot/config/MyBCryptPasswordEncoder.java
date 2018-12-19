package com.zhoubi.graindepot.config;

import com.zhoubi.graindepot.util.RSAUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 */
public class MyBCryptPasswordEncoder extends BCryptPasswordEncoder {

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
       // System.out.println(rawPassword);
        if(rawPassword.length()>20)//大于20我们认为是加密密码
            rawPassword= RSAUtils.decryptStringByJs(rawPassword.toString());
      //  System.out.println(rawPassword);
        return super.matches(rawPassword, encodedPassword);
    }
}
