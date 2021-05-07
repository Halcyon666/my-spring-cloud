package com.whalefall.service;


import com.whalefall.dao.SysUserMapper;
import com.whalefall.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    public List<SysUser> findAll() {
        return sysUserMapper.findAll();
    }
}