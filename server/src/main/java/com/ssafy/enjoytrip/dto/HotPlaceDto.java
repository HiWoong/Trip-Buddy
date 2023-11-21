package com.ssafy.enjoytrip.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HotPlaceDto {

	private int hotplaceId;
	private String userId;
	private String subject;
	private String content;
	private String image;
	private int hitCount;
	private int visitedCount;
	private String createdDate;
	private String updatedDate;
}
