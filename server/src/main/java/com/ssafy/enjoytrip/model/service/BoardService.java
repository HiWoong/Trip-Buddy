package com.ssafy.enjoytrip.model.service;

import com.ssafy.enjoytrip.dto.BoardDto;
import com.ssafy.enjoytrip.util.PageNavigation;

import java.util.List;
import java.util.Map;

public interface BoardService {

	int writeArticle(BoardDto boardDto) throws Exception;
	List<BoardDto> listArticle(Map<String, String> map) throws Exception;

	int getArticleCount(Map<String, String> map) throws Exception;
	BoardDto getArticle(int articleNo) throws Exception;
	int updateHit(int articleNo) throws Exception;
	
	int modifyArticle(BoardDto boardDto) throws Exception;
	int deleteArticle(int articleNo) throws Exception;
	
}
