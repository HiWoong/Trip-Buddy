package com.ssafy.enjoytrip.model.repo;

import com.ssafy.enjoytrip.dto.PlanDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PlanRepo {
    int createPlan(PlanDto planDto) throws Exception;
    List<PlanDto> viewTotalPlan(String userId) throws Exception;
    int deletePlan(String planId) throws Exception;
}
