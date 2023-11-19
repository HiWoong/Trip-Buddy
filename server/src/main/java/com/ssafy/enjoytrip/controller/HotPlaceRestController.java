package com.ssafy.enjoytrip.controller;

import com.ssafy.enjoytrip.dto.HotPlaceDto;
import com.ssafy.enjoytrip.model.service.HotPlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/enjoytripapi/hotplaceapi")
@RequiredArgsConstructor
public class HotPlaceRestController {

	private final HotPlaceService hotPlaceService;

	@GetMapping("/list")
	private ResponseEntity<?> list(@RequestParam String pgno, @RequestParam String sort) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("pgno", pgno + "");
		map.put("sort", sort);
		System.out.println(sort);
		List<HotPlaceDto> list = hotPlaceService.listHotPlace(map);
		System.out.println(list);
		System.out.println(list.size());
		return new ResponseEntity<List<HotPlaceDto>>(list, HttpStatus.OK);
	}

}
