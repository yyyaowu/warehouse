package com.yao.warehouse.controller;


import com.yao.warehouse.common.R;
import com.yao.warehouse.domain.LoginUser;
import com.yao.warehouse.domain.SysUser;
import com.yao.warehouse.service.LoginServcie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class LoginController {

    @Autowired
    private LoginServcie loginServcie;

//    @PostMapping("/user/login")
//    public ResponseResult login(@RequestBody User user){
//        //登录
//        return loginServcie.login(user);
//    }

    @PostMapping("/user/login")
    public R login(@RequestBody SysUser user){
        //登录
        log.info("user:{}",user);
        return loginServcie.login(user);
    }

    @RequestMapping("/user/logout")
    public R logout(){
        return loginServcie.logout();
    }

    @GetMapping("/user/info")
    public R<LoginUser> getInfo(){
        return loginServcie.getInfo();

    }
}
