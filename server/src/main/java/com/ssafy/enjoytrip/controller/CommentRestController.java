package com.ssafy.enjoytrip.controller;

import com.ssafy.enjoytrip.dto.CommentDto;
import com.ssafy.enjoytrip.model.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enjoytripapi/commentapi")
@CrossOrigin("*")
@RequiredArgsConstructor
public class CommentRestController {

    private final CommentService commentService;

    @GetMapping("/list")
    private ResponseEntity<?> list(@RequestParam("articleNo") int articleNo) throws Exception {
        System.out.println(articleNo);
        List<CommentDto> comments = commentService.list(articleNo);
        System.out.println(comments);
        if(comments != null)
            return new ResponseEntity<List<CommentDto>>(comments, HttpStatus.OK);
        else return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/write")
    private ResponseEntity<?> write(@RequestBody CommentDto commentDto) throws Exception {
        System.out.println(commentDto);
        String content = commentDto.getContent();
        String userId = commentDto.getUserId();
        int articleNo = commentDto.getArticleNo();
        int result = commentService.writeComment(content, userId, articleNo);
        if(result == 1) return new ResponseEntity<Integer>(result, HttpStatus.CREATED);
        else return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
