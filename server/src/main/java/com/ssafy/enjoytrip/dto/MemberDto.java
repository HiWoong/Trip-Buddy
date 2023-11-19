package com.ssafy.enjoytrip.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

	private String userId;
	private String userName;
	private String userPwd;
	private String emailId;
	private String emailDomain;
	private String joinDate;
	private String refreshToken;
	private String profileImage;
}
