package com.ssafy.enjoytrip.controller;

import com.ssafy.enjoytrip.dto.UserDto;
import com.ssafy.enjoytrip.model.service.UserService;
import com.ssafy.enjoytrip.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/enjoytripapi/userapi")
@CrossOrigin("*")
@RequiredArgsConstructor
public class UserRestController {

	private final UserService UserService;
	private final JWTUtil jwtUtil;

	@PostMapping("/join")
	private ResponseEntity<?> join(@RequestBody UserDto UserDto) throws Exception {

		int result = UserService.joinUser(UserDto);
		if(result == 1)
			return new ResponseEntity<Integer>(result, HttpStatus.CREATED);
		else
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("/login")
	private ResponseEntity<?> login(@RequestBody UserDto UserDto) throws Exception {
		UserDto loginUser = UserService.loginUser(UserDto);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;

		if(loginUser != null) {
			String accessToken = jwtUtil.createAccessToken(loginUser.getUserId());
			String refreshToken = jwtUtil.createAccessToken(loginUser.getUserId());

			UserService.saveRefreshToken(loginUser.getUserId(), refreshToken);

			resultMap.put("accessToken", accessToken);
			resultMap.put("refreshToken", refreshToken);
			status = HttpStatus.CREATED;
		} else {
			resultMap.put("message", "아이디 혹은 패스워드가 잘못되었습니다.");
			status = HttpStatus.UNAUTHORIZED;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/logout")
	private ResponseEntity<?> logout(@RequestBody UserDto userDto) throws Exception {

		UserService.deleteRefreshToken(userDto.getUserId());

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@PostMapping("/update")
	private ResponseEntity<?> update(@RequestBody UserDto UserDto, HttpServletRequest request) throws Exception {

		if (jwtUtil.checkToken(request.getHeader("Authorization"))){
			
			int result = UserService.updateUser(UserDto);
			if(result == 1)
				return new ResponseEntity<Integer>(result, HttpStatus.OK);
			else
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/delete/{userId}")
	private ResponseEntity<?> delete(@PathVariable("userId") String userId, HttpServletRequest request) throws Exception {

		if (jwtUtil.checkToken(request.getHeader("Authorization"))){
			int result = UserService.deleteUser(userId);
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
		if (jwtUtil.checkToken(request.getHeader("Authorization"))) {
			UserDto UserDto = UserService.getUserInfo(userId);
			resultMap.put("userInfo", UserDto);
			status = HttpStatus.OK;
		} else {
			log.error("사용 불가능 토큰!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@GetMapping("getImage/{userId}")
	private ResponseEntity<?> getImage(@PathVariable("userId") String userId, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		UserDto UserDto = UserService.getUserInfo(userId);
		if(UserDto != null) {
			resultMap.put("profileImage", UserDto.getProfileImage());
			status = HttpStatus.OK;
		} else {
			status = HttpStatus.NO_CONTENT;
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
			if (token.equals(UserService.getRefreshToken(userId))) {
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

	@GetMapping("check/{userId}")
	public ResponseEntity<?> checkDuplId(@PathVariable("userId") String userId) throws Exception {
		int isDupl = UserService.checkDuplId(userId);
		if(isDupl == 0) return new ResponseEntity<Integer>(1, HttpStatus.OK);
		else return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("favorite/{userId}")
	public ResponseEntity<?> getFavorites(@PathVariable("userId") String userId) throws Exception {
		String favorites = UserService.getFavorites(userId);
		if(favorites != null) return new ResponseEntity<String>(favorites, HttpStatus.OK);
		else return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("favorite")
	public ResponseEntity<?> setFavorites(@RequestBody UserDto UserDto) throws Exception {
		int result = UserService.setFavorites(UserDto);
		if(result == 1) return new ResponseEntity<Integer>(1, HttpStatus.OK);
		else return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}


