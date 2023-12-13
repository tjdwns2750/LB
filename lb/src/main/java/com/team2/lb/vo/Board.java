package com.team2.lb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Board {
	
	private int bno;
	private int uno;
	private String title;
	private String content;
	private int hits;
	private String categori;
	private String orininfile;
	private String savedfile;
	private int recommend;
	private String createdDay;

}
