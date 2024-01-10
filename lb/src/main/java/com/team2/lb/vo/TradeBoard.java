package com.team2.lb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradeBoard {
	private int bbno;
	private String id;
	private String name;
	private String address;
	private String title;
	private String content;
	private int hits;
	private String category;
	private String originalfile;
	private String savedfile;
	private int recommend;
	private String created_day;
	private String isbn;
	private String thumbnail;	
	private String bookTitle;
	private String author;
	private String publisher;
	private int price;
	private int amount;
}
