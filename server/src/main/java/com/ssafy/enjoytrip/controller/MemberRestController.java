package com.ssafy.enjoytrip.controller;

import com.ssafy.enjoytrip.dto.LoginDto;
import com.ssafy.enjoytrip.dto.MemberDto;
import com.ssafy.enjoytrip.model.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@RestController
@RequestMapping("/enjoytripapi/userapi")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class MemberRestController {

	private final MemberService memberService;

	@GetMapping("/idcheck")
	private ResponseEntity<?> idcheck(@RequestParam String checkid) throws Exception {
		// TODO : 입력한 아이디의 사용여부 체크 (0 : 사용 X, 1 : 사용 O)
		int result = memberService.idCheck(checkid);
		if(result == 1) return new ResponseEntity<Integer>(result, HttpStatus.OK);
		else return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("/join")
	private ResponseEntity<?> join(@RequestBody MemberDto memberDto) throws Exception {

		int result = memberService.joinMember(memberDto);
		if(result == 1)
			return new ResponseEntity<Integer>(result, HttpStatus.CREATED);
		else
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}


	@PostMapping("/login")
	private ResponseEntity<?> login(@RequestBody LoginDto loginDto, HttpSession session) throws Exception {
		MemberDto newLoginDto = memberService.loginMember(loginDto.getUserId());

		if(newLoginDto != null) {
			session.setAttribute("userinfo", newLoginDto);
			return new ResponseEntity<MemberDto>(newLoginDto, HttpStatus.CREATED);
		}
		else return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);

	}

	@GetMapping("/logout")
	private ResponseEntity<?> logout(HttpSession session) {
		session.invalidate();
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@PostMapping("/update")
	private ResponseEntity<?> update(@RequestBody MemberDto memberDto) throws SQLException {

		int result = memberService.updateMember(memberDto);
		if(result == 1)
			return new ResponseEntity<Integer>(result, HttpStatus.CREATED);
		else
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/delete")
	private ResponseEntity<?> delete(@RequestParam String userId) throws SQLException {
		int result = memberService.deleteMember(userId);
		if(result == 1)
			return new ResponseEntity<Integer>(result, HttpStatus.CREATED);
		else
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}


