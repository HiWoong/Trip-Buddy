package com.ssafy.enjoytrip.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter	@Setter
@ToString
public class AttractionInfoDto implements Comparable<AttractionInfoDto>{

	private int contentId;
	private int contentTypeId;
	private String title;
	private String addr1;
	private String addr2;
	private String zipcode;
	private String tel;
	private String firstImage;
	private String firstImage2;
	private int readcount;
	private int sidoCode;
	private int gugunCode;
	private double latitude;
	private double longitude;
	private String mlevel;
	private double distance;
	
	@Override
	public int compareTo(AttractionInfoDto o) {
		// TODO Auto-generated method stub
		
		return Double.compare(this.getDistance(), o.getDistance());
	}
}
