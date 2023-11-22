package com.ssafy.enjoytrip.model.repo;


import com.ssafy.enjoytrip.dto.UserDto;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.Map;

@Mapper
public interface UserRepo {

	UserDto getUserInfo(String userId) throws Exception;
	UserDto loginUser(UserDto UserDto) throws Exception;
	int updateUser(UserDto UserDto) throws SQLException;
	int deleteUser(String userId) throws SQLException;
	int joinUser(UserDto UserDto) throws SQLException;
	void saveRefreshToken(Map<String, String> map) throws Exception;
	Object getRefreshToken(String userId) throws Exception;
	void deleteRefreshToken(Map<String, String> map) throws Exception;
	int checkDuplId(String userId) throws Exception;
	String getFavorites(String userId) throws Exception;
	int setFavorites(UserDto UserDto) throws Exception;
	UserDto getUserEmail(String userId) throws Exception;
	int changePassword(UserDto userDto) throws Exception;


}
