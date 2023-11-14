package com.ssafy.enjoytrip.model.repo;


import com.ssafy.enjoytrip.dto.BoardDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
@Mapper
public interface BoardRepo {

	int writeArticle(BoardDto boardDto) throws SQLException;
	List<BoardDto> listArticle(Map<String, Object> param) throws SQLException;
	int getArticleCount(Map<String, String> param) throws SQLException;
	BoardDto getArticle(int articleNo) throws SQLException;
	void updateHit(int articleNo) throws SQLException;
	
	int modifyArticle(BoardDto boardDto) throws SQLException;
	int deleteArticle(int articleNo) throws SQLException;
}
