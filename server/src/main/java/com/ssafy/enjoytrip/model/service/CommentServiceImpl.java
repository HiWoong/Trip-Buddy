package com.ssafy.enjoytrip.model.service;

import com.ssafy.enjoytrip.dto.CommentDto;
import com.ssafy.enjoytrip.model.repo.CommentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepo repo;

    @Override
    public List<CommentDto> list(int articleNo) throws Exception {
        return repo.list(articleNo);
    }
}
