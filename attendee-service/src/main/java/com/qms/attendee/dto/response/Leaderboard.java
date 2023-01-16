package com.qms.attendee.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
public class Leaderboard {

//	private RankDetail yourRank;
	private List<RankDetail> rankList;
}
