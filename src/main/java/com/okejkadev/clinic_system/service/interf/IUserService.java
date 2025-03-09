package com.okejkadev.clinic_system.service.interf;

import com.okejkadev.clinic_system.dto.request.LoginRequest;
import com.okejkadev.clinic_system.dto.response.Response;
import com.okejkadev.clinic_system.entity.User;

public interface IUserService {
    Response addUser(User user);

    Response login(LoginRequest loginRequest);

    Response getAllUsers(String name);

    Response deleteUser(String userId);

    Response getUserById(String userId);

    Response getMyInfo(String email);
}
