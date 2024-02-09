package com.onlinestore.service;

import com.onlinestore.model.User;

import java.util.List;

public interface UserService {

    List<User> getUserList();

    User getUserInfo(int id);

    User createUser(String name, String phone, String email) throws Exception;

    void deleteUser(int id);

    void updateUser(int id, String name, String phone, String email) throws Exception;
}
