package com.ssafy.enjoytrip.model.service;

import com.ssafy.enjoytrip.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender mailSender;

    @Override
    public void sendMessage(UserDto userDto) throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userDto.getEmailId() + "@" + userDto.getEmailDomain());
        message.setSubject(userDto.getUserName() +"님의 새로운 비밀번호 입니다.");
        message.setText(userDto.getUserPwd());

        mailSender.send(message);
    }
}
