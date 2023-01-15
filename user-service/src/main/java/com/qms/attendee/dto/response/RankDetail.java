package com.qms.attendee.dto.response;

public interface RankDetail {
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