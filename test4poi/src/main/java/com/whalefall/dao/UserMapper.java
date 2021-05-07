package com.whalefall.dao;

import com.whalefall.pojo.User;

import java.util.List;

public interface UserMapper {

    List<User> selectUsers();

    void updateUserByName(User user);

    void addUser(User user);

    int selectByName(String username);

}
