package com.qms.common.dto;

import java.io.Serializable;

public interface RankDetail extends Serializable {
	Long getRank();

	String getName();

	String getEmail();

	Long getTotalScore();
}

//
//interface PersonSummary {
//
//	  String getFirstname();
//	  String getLastname();
//	  AddressSummary getAddress();
//
//	  interface AddressSummary {
//	    String getCity();
//	  }
//}