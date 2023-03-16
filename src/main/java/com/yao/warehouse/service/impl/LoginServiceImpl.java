package com.yao.warehouse.service.impl;


import com.yao.warehouse.Dto.UserDto;
import com.yao.warehouse.common.BaseContext;
import com.yao.warehouse.common.R;
import com.yao.warehouse.domain.LoginUser;
import com.yao.warehouse.domain.SysUser;
import com.yao.warehouse.mapper.MenuMapper;
import com.yao.warehouse.service.LoginServcie;
import com.yao.warehouse.service.RoleService;
import com.yao.warehouse.utils.JwtUtil;
import com.yao.warehouse.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class LoginServiceImpl implements LoginServcie {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisUtil redisCache;

    @Override
    public R login(SysUser user) {
        log.info("{}",user);
        //AuthenticationManager authenticate进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        log.info("authenticate:{}",authenticate);
        //如果认证没通过，给出对应的提示
        if(Objects.isNull(authenticate)){
            log.error("登录失败");
            throw new RuntimeException("登录失败");
        }
        //如果认证通过了，使用userid生成一个jwt jwt存入ResponseResult返回
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userid = loginUser.getUser().getId().toString();
        //BaseContext.setCurrentId(loginUser.getUser().getId());
        String jwt = JwtUtil.createJWT(userid);
        Map<String,String> map = new HashMap<>();
        map.put("token",jwt);
        //把完整的用户信息存入redis  userid作为key
        redisCache.set("login:"+userid,loginUser);
        return  R.success(map);
    }

    @Override
    public R logout() {
        //获取SecurityContextHolder中的用户id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userid = loginUser.getUser().getId();
        //删除redis中的值
        redisCache.del("login:"+userid);
        return  R.success("注销成功");
    }

    @Override
    public R getInfo() {
        UsernamePasswordAuthenticationToken authentication =
            (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userid = loginUser.getUser().getId();

        UserDto userDto = new UserDto();

        userDto.setName(loginUser.getUser().getUserName());

        //BeanUtils.copyProperties(loginUser.getUser(),userDto);

        List<String> routes = menuMapper.selectPermsByUserIdAndTypeMenu(userid);

        List<String> buttons = menuMapper.selectPermsByUserIdAndTypeBt(userid);

        userDto.setButtons(buttons);

        userDto.setRoutes(routes);


        return R.success(userDto);
    }
}
