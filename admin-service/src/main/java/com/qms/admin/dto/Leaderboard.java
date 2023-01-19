package com.qms.admin.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.qms.common.dto.RankDetail;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonInclude(Include.NON_NULL)
public class Leaderboard implements Serializable {

	private static final long serialVersionUID = 3636103812379884376L;

//	private RankDetail yourRank;
	private List<RankDetail> rankList;
}
