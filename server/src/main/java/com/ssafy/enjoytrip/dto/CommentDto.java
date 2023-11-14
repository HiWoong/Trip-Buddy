package com.ssafy.enjoytrip.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class CommentDto {

	private int commentId;
	private String userId;
	private int articleNo;
	private String content;
	private String createdDate;

}
