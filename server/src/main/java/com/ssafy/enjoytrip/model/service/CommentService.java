package com.ssafy.enjoytrip.model.service;


import com.ssafy.enjoytrip.dto.CommentDto;

import java.util.List;

public interface CommentService {
	List<CommentDto> list(int articleNo) throws Exception;
	int writeComment(String content, String userId, int articleNo) throws Exception;

	int modifyComment(String content, int commentId) throws Exception;

	int deleteComment(int ci) throws Exception;
}
