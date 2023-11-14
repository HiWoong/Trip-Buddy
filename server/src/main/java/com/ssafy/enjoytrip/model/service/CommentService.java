package com.ssafy.enjoytrip.model.service;


import com.ssafy.enjoytrip.dto.CommentDto;

import java.util.List;

public interface CommentService {

	List<CommentDto> list(int articleNo) throws Exception;
	
}
