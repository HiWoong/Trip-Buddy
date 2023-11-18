package com.ssafy.enjoytrip.controller;

import com.ssafy.enjoytrip.dto.LoginDto;
import com.ssafy.enjoytrip.dto.MemberDto;
import com.ssafy.enjoytrip.model.service.MemberService;
import com.ssafy.enjoytrip.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Member;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/enjoytripapi/userapi")
@CrossOrigin("*")
@RequiredArgsConstructor
public class MemberRestController {

	private final MemberService memberService;
	private final JWTUtil jwtUtil;

	@PostMapping("/join")
	private ResponseEntity<?> join(@RequestBody MemberDto memberDto) throws Exception {

		int result = memberService.joinMember(memberDto);
		if(result == 1)
			return new ResponseEntity<Integer>(result, HttpStatus.CREATED);
		else
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("/login")
	private ResponseEntity<?> login(@RequestBody MemberDto memberDto) throws Exception {
		MemberDto loginMember = memberService.loginMember(memberDto);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;

		System.out.println("memberDto : " + memberDto);
		System.out.println("loginMember = " + loginMember);

		if(loginMember != null) {
			String accessToken = jwtUtil.createAccessToken(loginMember.getUserId());
			String refreshToken = jwtUtil.createAccessToken(loginMember.getUserId());

			memberService.saveRefreshToken(loginMember.getUserId(), refreshToken);

			resultMap.put("accessToken", accessToken);
			resultMap.put("refreshToken", refreshToken);
			System.out.println("resultMap = " + resultMap);
			status = HttpStatus.CREATED;
		} else {
			resultMap.put("message", "아이디 혹은 패스워드가 잘못되었습니다.");
			status = HttpStatus.UNAUTHORIZED;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@GetMapping("/logout/{userId}")
	private ResponseEntity<?> logout(@PathVariable("userId") String userId) throws Exception {
//		Map<String, Object> resultMap = new HashMap<String, Object>();
		memberService.deleteRefreshToken(userId);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@PostMapping("/update")
	private ResponseEntity<?> update(@RequestBody MemberDto memberDto, HttpServletRequest request) throws Exception {

		if (jwtUtil.checkToken(request.getHeader("Authorization"))){

//			System.out.println("memberDto.getProfileImage() = " + memberDto.getProfileImage());
			
			int result = memberService.updateMember(memberDto);
			if(result == 1)
				return new ResponseEntity<Integer>(result, HttpStatus.CREATED);
			else
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/delete/{userId}")
	private ResponseEntity<?> delete(@PathVariable("userId") String userId, HttpServletRequest request) throws Exception {

		if (jwtUtil.checkToken(request.getHeader("Authorization"))){
			int result = memberService.deleteMember(userId);
			if(result == 1){
				return new ResponseEntity<Integer>(result, HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("info/{userId}")
	private ResponseEntity<?> info(@PathVariable("userId") String userId, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
//		System.out.println("userId = " + userId);
//		System.out.println(request.getHeader("Authorization"));
		if (jwtUtil.checkToken(request.getHeader("Authorization"))) {
//			log.info("사용 가능한 토큰!!!");
//          로그인 사용자 정보.
			MemberDto memberDto = memberService.getUserInfo(userId);
			resultMap.put("userInfo", memberDto);
//			System.out.println("memberDto.getProfileImage() = " + memberDto.getProfileImage());
			status = HttpStatus.OK;
		} else {
			log.error("사용 불가능 토큰!!!");
//			System.out.println("fdifsjlnfseifesflseifseifksfsfjskfsjhjfksfhjeskfesf");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody String userId, HttpServletRequest request)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String token = request.getHeader("refreshToken");
		log.debug("token : {}, userId : {}", token, userId);
		if (jwtUtil.checkToken(token)) {
			if (token.equals(memberService.getRefreshToken(userId))) {
				String accessToken = jwtUtil.createAccessToken(userId);
				log.debug("token : {}", accessToken);
				log.debug("정상적으로 액세스토큰 재발급!!!");
				resultMap.put("access-token", accessToken);
				status = HttpStatus.CREATED;
			}
		} else {
			log.debug("리프레쉬토큰도 사용불가!!!!!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
}


