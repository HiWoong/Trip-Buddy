package com.ssafy.enjoytrip.controller;

import com.ssafy.enjoytrip.dto.PlanDto;
import com.ssafy.enjoytrip.model.service.PlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/enjoytripapi/planapi")
@CrossOrigin("*")
@RequiredArgsConstructor
public class PlanRestController {

    private final PlanService planService;

    @PostMapping("/create")
    private ResponseEntity<?> createPlan(@RequestBody PlanDto planDto) throws Exception{

//        System.out.println("planDto = " + planDto);
        int result = planService.createPlan(planDto);

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @GetMapping("/view/{userId}")
    private ResponseEntity<?> viewTotalPlan(@PathVariable("userId") String userId) throws Exception {
        List<PlanDto> list = planService.viewTotalPlan(userId);
        
//        for (PlanDto p : list){
//            System.out.println("p = " + p);
//        }

        if (list != null){
            return new ResponseEntity<List<PlanDto>>(list, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/delete/{planId}")
    private ResponseEntity<?> deletePlan(@PathVariable("planId") String planId) throws Exception {

        int result = planService.deletePlan(planId);
        if (result == 1){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
