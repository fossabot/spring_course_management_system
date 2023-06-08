package com.gaogaoqwq.course_info_management_system_backend.service.interfaces.user;

import com.gaogaoqwq.course_info_management_system_backend.entity.user.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;


public interface UserService {
    Map<Object, Object> getUserInfo(@NotNull UserDetails userDetails);

    long getUserCount();

    User updateUserInfo(@NotNull Map<String, String> request);
}
