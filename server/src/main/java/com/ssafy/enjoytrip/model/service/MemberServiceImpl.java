package com.ssafy.enjoytrip.model.service;


import com.ssafy.enjoytrip.dto.MemberDto;
import com.ssafy.enjoytrip.model.repo.MemberRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberRepo repo;

	@Override
	public int idCheck(String userId) throws Exception {
		return repo.idCheck(userId);
	}

	@Override
	public int joinMember(MemberDto memberDto) throws Exception {
		return repo.joinMember(memberDto);
	}

	@Override
	public MemberDto loginMember(String userId) throws Exception {
		System.out.println(repo.loginMember(userId));
		return repo.loginMember(userId);
	}

	@Override
	public int updateMember(MemberDto memberDto) throws SQLException {
		return repo.updateMember(memberDto);
	}

	@Override
	public int deleteMember(String userId) throws SQLException {
		return repo.deleteMember(userId);
	}

}
