//package com.aurionpro.lms.dto;
//
//import lombok.Data;
//
//import java.util.List;
//
//@Data
//public class AdminResponseDTO {
//	private int id;
//	private String username;
//	private String email;
//	private List<Integer> loanOfficerIds; // List of loan officer IDs managed by this admin
//	private List<Integer> loanSchemeIds; // List of loan scheme IDs created by this admin
//}

package com.aurionpro.lms.dto;

import lombok.Data;

import java.util.List;

@Data
public class AdminResponseDTO {
	private int id;
	private String username;
	private String email;
	private List<Integer> loanOfficerIds;
	private List<Integer> loanSchemeIds;
}