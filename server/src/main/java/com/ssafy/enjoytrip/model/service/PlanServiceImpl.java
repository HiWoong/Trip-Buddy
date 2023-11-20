package com.ssafy.enjoytrip.model.service;

import com.ssafy.enjoytrip.dto.PlanDto;
import com.ssafy.enjoytrip.model.repo.PlanRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService{

    private final PlanRepo repo;

    @Override
    public int createPlan(PlanDto planDto) throws Exception {
        return repo.createPlan(planDto);
    }

    @Override
    public List<PlanDto> viewTotalPlan(String userId) throws Exception {
        return repo.viewTotalPlan(userId);
    }

    @Override
    public int deletePlan(String planId) throws Exception {
        return repo.deletePlan(planId);
    }

}
