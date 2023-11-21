package com.ssafy.enjoytrip.model.service;

import com.ssafy.enjoytrip.dto.HotPlaceDto;
import com.ssafy.enjoytrip.model.repo.HotPlaceRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class HotPlaceServiceImpl implements HotPlaceService {

	private final HotPlaceRepo repo;

	@Override
	public List<HotPlaceDto> listHotPlace(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();

		int pgno = Integer.parseInt(map.get("pgno"));

		param.put("start", (pgno-1)*12);
		param.put("listsize", 12);
		param.put("sort", map.get("sort"));
		System.out.println(param);
		return repo.listHotPlace(param);
	}

	@Override
	public int createHotPlace(HotPlaceDto hotPlaceDto) throws Exception {
		return repo.createHotPlace(hotPlaceDto);
	}

	@Override
	public HotPlaceDto getFavHotPlace(int hotPlaceId) throws Exception {
		return repo.getFavHotPlace(hotPlaceId);
	}

	@Override
	public List<HotPlaceDto> getMyHotPlace(String userId) throws Exception {
		return repo.getMyHotPlace(userId);
	}

}
