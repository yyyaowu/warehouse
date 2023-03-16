package com.yao.warehouse.service;


import com.yao.warehouse.common.R;
import com.yao.warehouse.domain.SysUser;

public interface LoginServcie {
    R login(SysUser user);

    R logout();

    R getInfo();

}
