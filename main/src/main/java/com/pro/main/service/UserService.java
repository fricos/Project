package com.pro.main.service;

import com.pro.main.dto.UserDto;
import com.pro.main.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}
