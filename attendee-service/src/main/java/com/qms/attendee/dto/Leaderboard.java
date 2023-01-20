package com.qms.attendee.dto;

import java.io.Serializable;
import java.util.List;

import com.qms.common.dto.RankDetail;

import lombok.Data;

@Data
public class Leaderboard implements Serializable {

	private static final long serialVersionUID = -4632301648328694946L;

	private RankDetail yourRank;
	private List<RankDetail> rankList;
}
