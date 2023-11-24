package com.ssafy.enjoytrip.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionRestController {

	@ExceptionHandler(SQLException.class)
	public ResponseEntity<?> sqlError(Exception e){
		Map<String, Object> map = new HashMap<>();
		map.put("message", e.getMessage());
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> error(Exception e){
		Map<String, Object> map = new HashMap<>();
		map.put("message", e.getMessage());
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
