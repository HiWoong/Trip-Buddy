package com.ssafy.enjoytrip.model.repo;


import com.ssafy.enjoytrip.dto.LoginDto;
import com.ssafy.enjoytrip.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Map;

@Mapper
public interface MemberRepo {

	MemberDto getUserInfo(String userId) throws Exception;
	MemberDto loginMember(MemberDto memberDto) throws Exception;
	int updateMember(MemberDto memberDto) throws SQLException;
	int deleteMember(String userId) throws SQLException;
	int joinMember(MemberDto memberDto) throws SQLException;
	void saveRefreshToken(Map<String, String> map) throws Exception;
	Object getRefreshToken(String userId) throws Exception;
	void deleteRefreshToken(Map<String, String> map) throws Exception;
}
