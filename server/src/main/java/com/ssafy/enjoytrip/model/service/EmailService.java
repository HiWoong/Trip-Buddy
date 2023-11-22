package com.ssafy.enjoytrip.model.service;

import com.ssafy.enjoytrip.dto.UserDto;

public interface EmailService {
    void sendMessage(UserDto userDto) throws Exception;
}
