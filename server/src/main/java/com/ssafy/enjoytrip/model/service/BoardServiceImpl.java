package com.ssafy.enjoytrip.model.service;

import com.ssafy.enjoytrip.dto.BoardDto;
import com.ssafy.enjoytrip.model.repo.BoardRepo;
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

		param.put("key", map.get("key").isEmpty() ? "" : map.get("key"));
		param.put("word", map.get("word").isEmpty() ? "" : map.get("word"));
		int pgno = Integer.parseInt(map.get("pgno")) - 1;
		int spp = Integer.parseInt(map.get("spp"));
		int start = pgno * spp;
		param.put("start", start);
		param.put("listsize", spp);
		param.put("sort", map.get("sort"));
		System.out.println("param: " + param);
		return repo.listArticle(param);
	}

	@Override
	public int getArticleCount(Map<String, String> map) throws Exception {
		return repo.getArticleCount(map);
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
