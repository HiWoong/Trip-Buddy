package com.ssafy.enjoytrip.model.service;


import com.ssafy.enjoytrip.dto.LoginDto;
import com.ssafy.enjoytrip.dto.MemberDto;
import com.ssafy.enjoytrip.model.repo.MemberRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberRepo repo;

	@Override
	public MemberDto getUserInfo(String userId) throws Exception {
		return repo.getUserInfo(userId);
	}

	@Override
	public MemberDto loginMember(MemberDto memberDto) throws Exception {
		return repo.loginMember(memberDto);
	}


	@Override
	public int joinMember(MemberDto memberDto) throws Exception {
		return repo.joinMember(memberDto);
	}

	@Override
	public void saveRefreshToken(String userId, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", refreshToken);
		repo.saveRefreshToken(map);
	}

	@Override
	public Object getRefreshToken(String userId) throws Exception {
		return repo.getRefreshToken(userId);
	}

	@Override
	public void deleteRefreshToken(String userId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", null);
		repo.deleteRefreshToken(map);
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
