package com.ssafy.enjoytrip.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginDto {

	private String userId;
	private String userPwd;
	private String saveId;
}
