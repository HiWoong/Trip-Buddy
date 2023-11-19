package com.ssafy.enjoytrip.model.service;

import com.ssafy.enjoytrip.dto.HotPlaceDto;

import java.util.List;
import java.util.Map;

public interface HotPlaceService {

	List<HotPlaceDto> listHotPlace(Map<String, String> map) throws Exception;

	
}
