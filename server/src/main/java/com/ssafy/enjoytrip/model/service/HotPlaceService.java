package com.ssafy.enjoytrip.model.service;

import com.ssafy.enjoytrip.dto.HotPlaceDto;

import java.util.List;
import java.util.Map;

public interface HotPlaceService {

	List<HotPlaceDto> listHotPlace(Map<String, String> map) throws Exception;
	int createHotPlace(HotPlaceDto hotPlaceDto) throws Exception;

	HotPlaceDto getFavHotPlace(int hotPlaceId) throws Exception;
	List<HotPlaceDto> getMyHotPlace(String userId) throws Exception;
	int updateVisitedCount(int hotPlaceId) throws Exception;

	int addHitCount(int hotPlaceId) throws Exception;

	int minHitCount(int hotPlaceId) throws Exception;
	int updateHotPlace(HotPlaceDto hotPlaceDto) throws Exception;
	int deleteHotPlace(int hotPlaceId) throws Exception;

	HotPlaceDto getOne(String sortType) throws Exception;
}
