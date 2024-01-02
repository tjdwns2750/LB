package com.team2.lb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

	/**
	 * 리플 정보
	 */
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
public class ReviewReply {

		int 	replynum;			//댓글번호
		int 	bno;			//본문 글번호
		String  id;			//작성자 ID
		String 	replytext;			//내용
		String 	inputdate;			//작성일
	}
