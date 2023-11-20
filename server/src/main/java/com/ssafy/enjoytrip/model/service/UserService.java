package com.ssafy.enjoytrip.model.service;


import com.ssafy.enjoytrip.dto.UserDto;

import java.sql.SQLException;

public interface UserService {

	UserDto getUserInfo(String userId) throws Exception;
	UserDto loginUser(UserDto UserDto) throws Exception;
	int updateUser(UserDto UserDto) throws SQLException;
	int deleteUser(String userId) throws SQLException;
	int joinUser(UserDto UserDto) throws Exception;
	void saveRefreshToken(String userId, String refreshToken) throws Exception;
	Object getRefreshToken(String userId) throws Exception;
	void deleteRefreshToken(String userId) throws Exception;

	int checkDuplId(String userId) throws Exception;

	String getFavorites(String userId) throws Exception;

	int setFavorites(UserDto UserDto) throws Exception;
}
