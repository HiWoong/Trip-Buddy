package com.ssafy.enjoytrip.model.service;

import com.ssafy.enjoytrip.dto.BoardDto;
import com.ssafy.enjoytrip.model.repo.BoardRepo;
import com.ssafy.enjoytrip.util.PageNavigation;
import com.ssafy.enjoytrip.util.SizeConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	private final BoardRepo repo;

	@Override
	public int writeArticle(BoardDto boardDto) throws Exception {
		return repo.writeArticle(boardDto);
    }

	@Override
	public List<BoardDto> listArticle(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
//		if("userid".equals(key))
//			key = "user_id";
		param.put("key", map.get("key").isEmpty() ? "" : map.get("key"));
		param.put("word", map.get("word").isEmpty() ? "" : map.get("word"));
		int pgno = Integer.parseInt(map.get("pgno"));
		int start = pgno * SizeConstant.LIST_SIZE - SizeConstant.LIST_SIZE;
		param.put("start", start);
		param.put("listsize", SizeConstant.LIST_SIZE);
		System.out.println(param);
		return repo.listArticle(param);
	}
	
	@Override
	public PageNavigation makePageNavigation(Map<String, String> map) throws Exception {
		PageNavigation pageNavigation = new PageNavigation();

		int naviSize = SizeConstant.NAVIGATION_SIZE;
		int sizePerPage = SizeConstant.LIST_SIZE;
		int currentPage = Integer.parseInt(map.get("pgno"));

		pageNavigation.setCurrentPage(currentPage);
		pageNavigation.setNaviSize(naviSize);
		Map<String, Object> param = new HashMap<String, Object>();
		String key = map.get("key");
//		if ("userid".equals(key))
//			key = "user_id";
		param.put("key", key.isEmpty() ? "" : key);
		param.put("word", map.get("word").isEmpty() ? "" : map.get("word"));
		int totalCount = repo.getTotalArticleCount(param);
		pageNavigation.setTotalCount(totalCount);
		int totalPageCount = (totalCount - 1) / sizePerPage + 1;
		pageNavigation.setTotalPageCount(totalPageCount);
		boolean startRange = currentPage <= naviSize;
		pageNavigation.setStartRange(startRange);
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < currentPage;
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();

		return pageNavigation;
	}

	@Override
	public BoardDto getArticle(int articleNo) throws Exception {
		return repo.getArticle(articleNo);
	}

	@Override
	public int updateHit(int articleNo) throws Exception {
		repo.updateHit(articleNo);
		return articleNo;
	}

	@Override
	public int modifyArticle(BoardDto boardDto) throws Exception {
		// TODO : BoardDaoImpl의 modifyArticle 호출
		return repo.modifyArticle(boardDto);
	}

	@Override
	public int deleteArticle(int articleNo) throws Exception {
		// TODO : BoardDaoImpl의 deleteArticle 호출
		return repo.deleteArticle(articleNo);
	}

}
