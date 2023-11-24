package com.ssafy.enjoytrip.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

	private String userId;
	private String userPwd;
	private String saveId;
}
