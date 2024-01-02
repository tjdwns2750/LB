package com.team2.lb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Alarm {
	private long ano;
	private String member_id;
	private String code;
	private String checked;
	private String created_day;
	private String prefix;
	private long bbno;
	private long bno;
	private long room_id;
}
