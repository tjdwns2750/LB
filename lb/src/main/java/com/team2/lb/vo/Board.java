package com.team2.lb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Board {
	
	private int bno;
	private String id;
	private String title;
	private String content;
	private int hits;
	private String address;
	private String category;
	private String originalfile;
	private String savedfile;
	private int recommend;
	private String created_day;

}
