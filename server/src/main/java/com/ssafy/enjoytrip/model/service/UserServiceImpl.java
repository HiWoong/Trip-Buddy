package com.ssafy.enjoytrip.model.service;


import com.ssafy.enjoytrip.dto.UserDto;
import com.ssafy.enjoytrip.model.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepo repo;

	@Override
	public UserDto getUserInfo(String userId) throws Exception {
		return repo.getUserInfo(userId);
	}

	@Override
	public UserDto loginUser(UserDto UserDto) throws Exception {
		return repo.loginUser(UserDto);
	}


	@Override
	public int joinUser(UserDto UserDto) throws Exception {
		return repo.joinUser(UserDto);
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
	public int checkDuplId(String userId) throws Exception {
		return repo.checkDuplId(userId);
	}

	@Override
	public String getFavorites(String userId) throws Exception {
		return repo.getFavorites(userId);
	}

	@Override
	public int setFavorites(UserDto UserDto) throws Exception {
		return repo.setFavorites(UserDto);
	}

	@Override
	public int updateUser(UserDto UserDto) throws SQLException {
		return repo.updateUser(UserDto);
	}

	@Override
	public int deleteUser(String userId) throws SQLException {
		return repo.deleteUser(userId);
	}

}
