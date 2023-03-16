package com.yao.warehouse.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yao.warehouse.domain.UserRole;
import com.yao.warehouse.service.UserRoleService;
import com.yao.warehouse.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author Lenovo
* @description 针对表【user_role】的数据库操作Service实现
* @createDate 2022-05-31 10:22:57
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService{

}




