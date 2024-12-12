package com.example.demo_register_user_api.service;

import com.example.demo_register_user_api.model.dto.request.UserForm;
import com.example.demo_register_user_api.model.entity.Users;

public interface UserService {
    Users insertUser(UserForm userForm);
}
