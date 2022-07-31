package com.lagou.dao;

import com.lagou.pojo.User;

import java.util.List;

public interface IUserDao {

    //查询所有用户
    List<User> findAll() throws Exception;

    //根据条件进行用户查询
    User findByCondition(User user) throws Exception;

    //插入用户
    void saveUser(User user) throws Exception;

    //更新用户
    void updateUser(User user) throws Exception;

    //删除用户
    void deleteUser(User user) throws Exception;

}
