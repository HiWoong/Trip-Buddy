package com.ssafy.enjoytrip.controller;

import com.ssafy.enjoytrip.dto.UserDto;
import com.ssafy.enjoytrip.model.service.EmailService;
import com.ssafy.enjoytrip.model.service.UserService;
import com.ssafy.enjoytrip.util.RandomStringGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enjoytripapi/emailapi")
@CrossOrigin("*")
@RequiredArgsConstructor
public class EmailRestController {

    private final UserService userService;
    private final EmailService emailService;
    private final RandomStringGenerator randomStringGenerator;

    @GetMapping("/{userId}")
    @Transactional
    private ResponseEntity<?> createNewPassword(@PathVariable("userId") String userId) throws Exception{

        UserDto userDto = userService.getUserEmail(userId);

        if (userDto == null){
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        String newPassword = randomStringGenerator.generate();
        System.out.println("newPassword = " + newPassword);
        userDto.setUserPwd(newPassword);
        userService.changePassword(userDto);
        emailService.sendMessage(userDto);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
