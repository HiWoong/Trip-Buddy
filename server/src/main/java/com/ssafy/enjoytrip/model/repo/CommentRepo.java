package com.ssafy.enjoytrip.model.repo;


import com.ssafy.enjoytrip.dto.CommentDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface CommentRepo {
	List<CommentDto> list(int articleNo) throws SQLException;
	int writeComment(String content, String userId, int articleNo) throws Exception;
	int modifyComment(String content, int commentId) throws Exception;
	int deleteComment(int ci) throws Exception;
}
