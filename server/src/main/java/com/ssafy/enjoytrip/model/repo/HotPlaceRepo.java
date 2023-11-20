package com.ssafy.enjoytrip.model.repo;


import com.ssafy.enjoytrip.dto.BoardDto;
import com.ssafy.enjoytrip.dto.HotPlaceDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Mapper
public interface HotPlaceRepo {
	List<HotPlaceDto> listHotPlace(Map<String, Object> param) throws Exception;
	int createHotPlace(HotPlaceDto hotPlaceDto) throws Exception;
	HotPlaceDto getFavHotPlace(int hotPlaceId) throws Exception;
	List<HotPlaceDto> getMyHotPlace(String userId) throws Exception;
}
