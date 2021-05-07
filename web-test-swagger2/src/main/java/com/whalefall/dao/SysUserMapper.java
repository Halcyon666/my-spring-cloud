package com.whalefall.dao;



import com.whalefall.model.SysUser;

import java.util.List;

public interface SysUserMapper {
    List<SysUser> findAll();
}