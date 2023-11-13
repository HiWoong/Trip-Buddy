package com.ssafy.enjoytrip.model.repo;


import com.ssafy.enjoytrip.dto.LoginDto;
import com.ssafy.enjoytrip.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Mapper
public interface MemberRepo {

	int idCheck(String userId) throws SQLException;
	int joinMember(MemberDto memberDto) throws SQLException;
	int updateMember(MemberDto memberDto) throws SQLException;
	int deleteMember(String userId) throws SQLException;
	MemberDto loginMember(String userId) throws SQLException;
	
}
