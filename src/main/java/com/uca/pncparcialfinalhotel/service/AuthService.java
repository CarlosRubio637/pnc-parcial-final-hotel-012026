package com.uca.pncparcialfinalhotel.service;

import com.uca.pncparcialfinalhotel.dto.LoginRequest;

public interface AuthService {
    String login(LoginRequest loginRequest);
}

