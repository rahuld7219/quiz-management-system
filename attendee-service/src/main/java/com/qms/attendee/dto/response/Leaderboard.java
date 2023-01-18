package com.qms.attendee.dto.response;

import java.util.List;

import com.qms.common.dto.RankDetail;

import lombok.Data;

@Data
public class Leaderboard {

//	private RankDetail yourRank;
	private List<RankDetail> rankList;
}
