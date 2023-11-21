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
//		System.out.println(sort);
		List<HotPlaceDto> list = hotPlaceService.listHotPlace(map);
//		System.out.println(list);
//		System.out.println(list.size());
		return new ResponseEntity<List<HotPlaceDto>>(list, HttpStatus.OK);
	}

	@PostMapping("/write")
	private ResponseEntity<?> createHotPlace(@RequestBody HotPlaceDto hotPlaceDto) throws Exception {
//		System.out.println(hotPlaceDto);
		int result = hotPlaceService.createHotPlace(hotPlaceDto);
//		System.out.println(result);
		if(result == 1) return new ResponseEntity<Integer>(1, HttpStatus.OK);
		else return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/readFavorite/{hotPlaceId}")
	private ResponseEntity<?> getFavHotPlace(@PathVariable int hotPlaceId) throws Exception {
//		System.out.println(hotPlaceId);
		HotPlaceDto favPlace = hotPlaceService.getFavHotPlace(hotPlaceId);
//		System.out.println(favPlace);
		if(favPlace != null) return new ResponseEntity<HotPlaceDto>(favPlace, HttpStatus.OK);
		else return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/myHotplace/{userId}")
	private ResponseEntity<?> getMyHotPlace(@PathVariable("userId") String userId) throws Exception {
		List<HotPlaceDto> list = hotPlaceService.getMyHotPlace(userId);

		if (list != null) return new ResponseEntity<List<HotPlaceDto>>(list, HttpStatus.OK);

		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
