package com.ssafy.enjoytrip.model.service;


import com.ssafy.enjoytrip.dto.AttractionInfoDto;

import java.util.List;

public interface AttractionService {

	List<AttractionInfoDto> search(int area, int type, String word);
	
}
