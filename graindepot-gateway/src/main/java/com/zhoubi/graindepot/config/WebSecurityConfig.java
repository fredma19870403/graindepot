package com.zhoubi.graindepot.config;

import com.zhoubi.graindepot.jwt.JwtAuthenticationTokenFilter;
import com.zhoubi.graindepot.service.GateUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by Administrator on 2018-12-6.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private GateUserDetailsService detailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login").defaultSuccessUrl("/main", true).permitAll()
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/login").invalidateHttpSession(true).permitAll()
                .and().authorizeRequests()
                .antMatchers("/assets/**").permitAll()
                .anyRequest().authenticated()
                .and().authorizeRequests().antMatchers("/**").authenticated()
        ;
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.httpBasic();

        //不使用请求缓存
        http.requestCache().disable();

        // 添加JWT com.fred.graindepot.filter
        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        // 禁用缓存
        http.headers().cacheControl();
        http.headers().contentTypeOptions().disable();
    }
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // auth.get
        auth.userDetailsService(detailsService).passwordEncoder(new MyBCryptPasswordEncoder());
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }
}
