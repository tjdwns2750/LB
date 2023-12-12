package com.team2.lb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookBoard {
	private int bno;
	private String title;
	private String author;
	private String publisher;
	private String inputDate;
	private String tbCntUrl;
	private String bookSummaryUrl;
	private String page;
	private String title_url;
	private String isbn;
}
