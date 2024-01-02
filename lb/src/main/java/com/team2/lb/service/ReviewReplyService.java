package com.team2.lb.service;

import java.util.ArrayList;

import com.team2.lb.vo.ReviewReply;

public interface ReviewReplyService {

	int writeReply(ReviewReply reply);

	int deleteReply(ReviewReply reply);

	ArrayList<ReviewReply> replyList(int bno);

}
