package com.ssafy.enjoytrip.model.repo;


import com.ssafy.enjoytrip.dto.CommentDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface CommentRepo {
	List<CommentDto> list(int articleNo) throws SQLException;
	int writeComment(@Param("content") String content, @Param("userId") String userId, @Param("articleNo")int articleNo) throws Exception;
	int modifyComment(@Param("content") String content, @Param("commentId") int commentId) throws Exception;
	int deleteComment(int ci) throws Exception;
}
