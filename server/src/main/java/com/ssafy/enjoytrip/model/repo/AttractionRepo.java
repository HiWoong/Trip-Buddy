package com.ssafy.enjoytrip.model.repo;


import com.ssafy.enjoytrip.dto.AttractionInfoDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
public interface AttractionRepo {
	List<AttractionInfoDto> search(@Param("area") int area, @Param("type")int type, @Param("word")String word);

}
