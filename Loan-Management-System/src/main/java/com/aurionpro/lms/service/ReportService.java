//package com.aurionpro.lms.service;
//
//import java.util.Map;
//
//public interface ReportService {
//	Map<String, Object> generateLoanOfficerReport(int loanOfficerId);
//}


package com.aurionpro.lms.service;

public interface ReportService {
    byte[] generateLoanOfficerReport(int loanOfficerId);
}