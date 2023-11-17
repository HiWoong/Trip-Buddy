package com.ssafy.enjoytrip.model.service;


import com.ssafy.enjoytrip.dto.LoginDto;
import com.ssafy.enjoytrip.dto.MemberDto;

import java.lang.reflect.Member;
import java.sql.SQLException;

public interface MemberService {

	MemberDto getUserInfo(String userId) throws Exception;
	MemberDto loginMember(MemberDto memberDto) throws Exception;
	int updateMember(MemberDto memberDto) throws SQLException;
	int deleteMember(String userId) throws SQLException;
	int joinMember(MemberDto memberDto) throws Exception;
	void saveRefreshToken(String userId, String refreshToken) throws Exception;
	Object getRefreshToken(String userId) throws Exception;
	void deleteRefreshToken(String userId) throws Exception;
}
