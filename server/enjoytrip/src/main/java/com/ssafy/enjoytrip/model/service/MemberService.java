package com.ssafy.enjoytrip.model.service;


import com.ssafy.enjoytrip.dto.LoginDto;
import com.ssafy.enjoytrip.dto.MemberDto;

import java.sql.SQLException;

public interface MemberService {

	int idCheck(String userId) throws Exception;
	int joinMember(MemberDto memberDto) throws Exception;
	int updateMember(MemberDto memberDto) throws SQLException;
	int deleteMember(String userId) throws SQLException;
	MemberDto loginMember(String userId) throws Exception;
	
}
