//package com.aurionpro.lms.service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.aurionpro.lms.dto.AdminResponseDTO;
//import com.aurionpro.lms.entity.Admin;
//import com.aurionpro.lms.entity.LoanOfficer;
//import com.aurionpro.lms.entity.LoanScheme;
//import com.aurionpro.lms.repository.AdminRepository;
//
//@Service
//public class AdminServiceImpl implements AdminService {
//
//	@Autowired
//	private AdminRepository adminRepository;
//
//	private ModelMapper mapper;
//
//	private AdminServiceImpl() {
//		this.mapper = new ModelMapper();
//		mapper.typeMap(Admin.class, AdminResponseDTO.class)
//				.addMapping(src -> src.getUser().getEmail(), AdminResponseDTO::setEmail)
//				.addMapping(src -> src.getUser().getUsername(), AdminResponseDTO::setUsername)
//				.addMapping(src -> src.getLoanOfficers() != null
//						? src.getLoanOfficers().stream().map(LoanOfficer::getId).collect(Collectors.toList())
//						: new ArrayList<>(), AdminResponseDTO::setLoanOfficerIds)
//				.addMapping(src -> src.getLoanSchemes() != null
//						? src.getLoanSchemes().stream().map(LoanScheme::getId).collect(Collectors.toList())
//						: new ArrayList<>(), AdminResponseDTO::setLoanSchemeIds);
//	}
//
//	@Override
//	public AdminResponseDTO getAdminById(int id) {
//		Optional<Admin> adminOpt = adminRepository.findById(id);
//		if (adminOpt.isEmpty()) {
//			throw new RuntimeException("Admin not found with ID: " + id);
//		}
//		Admin admin = adminOpt.get();
//		return mapper.map(admin, AdminResponseDTO.class);
//	}
//
//	@Override
//	public List<AdminResponseDTO> getAllAdmins() {
//		List<Admin> admins = adminRepository.findAll();
//		return admins.stream().map(admin -> mapper.map(admin, AdminResponseDTO.class)).collect(Collectors.toList());
//	}
//}

//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.dto.AdminResponseDTO;
//import com.aurionpro.lms.entity.Admin;
//import com.aurionpro.lms.entity.LoanOfficer;
//import com.aurionpro.lms.entity.LoanScheme;
//import com.aurionpro.lms.repository.AdminRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class AdminServiceImpl implements AdminService {
//
//	@Autowired
//	private AdminRepository adminRepository;
//
//	@Override
//	public AdminResponseDTO getAdminById(int id) {
//		Optional<Admin> adminOpt = adminRepository.findById(id);
//		if (adminOpt.isEmpty()) {
//			throw new RuntimeException("Admin not found with ID: " + id);
//		}
//		Admin admin = adminOpt.get();
//
//		AdminResponseDTO dto = new AdminResponseDTO();
//		dto.setId(admin.getId());
//		dto.setEmail(admin.getUser() != null ? admin.getUser().getEmail() : null);
//		dto.setUsername(admin.getUser() != null ? admin.getUser().getUsername() : null);
//		dto.setLoanOfficerIds(admin.getLoanOfficers() != null
//				? admin.getLoanOfficers().stream().map(LoanOfficer::getId).collect(Collectors.toList())
//				: new ArrayList<>());
//		dto.setLoanSchemeIds(admin.getLoanSchemes() != null
//				? admin.getLoanSchemes().stream().map(LoanScheme::getId).collect(Collectors.toList())
//				: new ArrayList<>());
//		return dto;
//	}
//
//	@Override
//	public List<AdminResponseDTO> getAllAdmins() {
//		List<Admin> admins = adminRepository.findAll();
//		return admins.stream().map(admin -> {
//			AdminResponseDTO dto = new AdminResponseDTO();
//			dto.setId(admin.getId());
//			dto.setEmail(admin.getUser() != null ? admin.getUser().getEmail() : null);
//			dto.setUsername(admin.getUser() != null ? admin.getUser().getUsername() : null);
//			dto.setLoanOfficerIds(admin.getLoanOfficers() != null
//					? admin.getLoanOfficers().stream().map(LoanOfficer::getId).collect(Collectors.toList())
//					: new ArrayList<>());
//			dto.setLoanSchemeIds(admin.getLoanSchemes() != null
//					? admin.getLoanSchemes().stream().map(LoanScheme::getId).collect(Collectors.toList())
//					: new ArrayList<>());
//			return dto;
//		}).collect(Collectors.toList());
//	}
//}

package com.aurionpro.lms.service;

import com.aurionpro.lms.dto.AdminResponseDTO;
import com.aurionpro.lms.entity.Admin;
import com.aurionpro.lms.entity.LoanOfficer;
import com.aurionpro.lms.entity.LoanScheme;
import com.aurionpro.lms.exception.ResourceNotFoundException;
import com.aurionpro.lms.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepository adminRepository;

	@Override
	public AdminResponseDTO getAdminById(int id) {
		Optional<Admin> adminOpt = adminRepository.findById(id);
		if (adminOpt.isEmpty()) {
			throw new ResourceNotFoundException("Admin not found with ID: " + id);
		}
		Admin admin = adminOpt.get();

		AdminResponseDTO dto = new AdminResponseDTO();
		dto.setId(admin.getId());
		dto.setEmail(admin.getUser() != null ? admin.getUser().getEmail() : null);
		dto.setUsername(admin.getUser() != null ? admin.getUser().getUsername() : null);
		dto.setLoanOfficerIds(admin.getLoanOfficers() != null
				? admin.getLoanOfficers().stream().map(LoanOfficer::getId).collect(Collectors.toList())
				: new ArrayList<>());
		dto.setLoanSchemeIds(admin.getLoanSchemes() != null
				? admin.getLoanSchemes().stream().map(LoanScheme::getId).collect(Collectors.toList())
				: new ArrayList<>());
		return dto;
	}

	@Override
	public List<AdminResponseDTO> getAllAdmins() {
		List<Admin> admins = adminRepository.findAll();
		if (admins.isEmpty()) {
			throw new ResourceNotFoundException("No admins found in the system");
		}
		return admins.stream().map(admin -> {
			AdminResponseDTO dto = new AdminResponseDTO();
			dto.setId(admin.getId());
			dto.setEmail(admin.getUser() != null ? admin.getUser().getEmail() : null);
			dto.setUsername(admin.getUser() != null ? admin.getUser().getUsername() : null);
			dto.setLoanOfficerIds(admin.getLoanOfficers() != null
					? admin.getLoanOfficers().stream().map(LoanOfficer::getId).collect(Collectors.toList())
					: new ArrayList<>());
			dto.setLoanSchemeIds(admin.getLoanSchemes() != null
					? admin.getLoanSchemes().stream().map(LoanScheme::getId).collect(Collectors.toList())
					: new ArrayList<>());
			return dto;
		}).collect(Collectors.toList());
	}
}