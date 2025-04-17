//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.dto.LoanSchemeRequestDTO;
//import com.aurionpro.lms.dto.LoanSchemeResponseDTO;
//import com.aurionpro.lms.entity.Admin;
//import com.aurionpro.lms.entity.LoanScheme;
//import com.aurionpro.lms.entity.User;
//import com.aurionpro.lms.repository.AdminRepository;
//import com.aurionpro.lms.repository.LoanSchemeRepository;
//import com.aurionpro.lms.repository.UserRepository;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class LoanSchemeServiceImpl implements LoanSchemeService {
//
//	@Autowired
//	private LoanSchemeRepository loanSchemeRepository;
//
//	@Autowired
//	private UserRepository userRepository;
//
//	@Autowired
//	private AdminRepository adminRepository;
//
//	private ModelMapper mapper;
//
//	private LoanSchemeServiceImpl() {
//		this.mapper = new ModelMapper();
//	}
//
//	@Override
//	public LoanSchemeResponseDTO createLoanScheme(int adminId, LoanSchemeRequestDTO requestDTO) {
//		Optional<User> adminOpt = userRepository.findById(adminId);
//		if (adminOpt.isEmpty()) {
//			throw new RuntimeException("User not found with ID: " + adminId);
//		}
//		Optional<Admin> adminEntityOpt = adminRepository.findById(adminId);
//		if (adminEntityOpt.isEmpty()) {
//			throw new RuntimeException("Admin not found with ID: " + adminId);
//		}
//		Admin admin = adminEntityOpt.get();
//
//		LoanScheme loanScheme = mapper.map(requestDTO, LoanScheme.class);
//		loanScheme.setAdmin(admin);
//
//		loanScheme = loanSchemeRepository.save(loanScheme);
//
//		mapper.typeMap(LoanScheme.class, LoanSchemeResponseDTO.class).addMapping(src -> src.getAdmin().getId(),
//				LoanSchemeResponseDTO::setAdminId);
//		return mapper.map(loanScheme, LoanSchemeResponseDTO.class);
//	}
//
//	@Override
//	public LoanSchemeResponseDTO getLoanSchemeById(int id) {
//		Optional<LoanScheme> schemeOpt = loanSchemeRepository.findById(id);
//		if (schemeOpt.isEmpty()) {
//			throw new RuntimeException("Loan scheme not found with ID: " + id);
//		}
//		LoanScheme loanScheme = schemeOpt.get();
//
//		mapper.typeMap(LoanScheme.class, LoanSchemeResponseDTO.class).addMapping(src -> src.getAdmin().getId(),
//				LoanSchemeResponseDTO::setAdminId);
//		return mapper.map(loanScheme, LoanSchemeResponseDTO.class);
//	}
//
//	@Override
//	public List<LoanSchemeResponseDTO> getLoanSchemesByAdminId(int adminId) {
//		List<LoanScheme> schemes = loanSchemeRepository.findByAdminId(adminId);
//		mapper.typeMap(LoanScheme.class, LoanSchemeResponseDTO.class).addMapping(src -> src.getAdmin().getId(),
//				LoanSchemeResponseDTO::setAdminId);
//		return schemes.stream().map(scheme -> mapper.map(scheme, LoanSchemeResponseDTO.class))
//				.collect(Collectors.toList());
//	}
//}

//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.dto.LoanSchemeRequestDTO;
//import com.aurionpro.lms.dto.LoanSchemeResponseDTO;
//import com.aurionpro.lms.entity.Admin;
//import com.aurionpro.lms.entity.LoanScheme;
//import com.aurionpro.lms.entity.User;
//import com.aurionpro.lms.repository.AdminRepository;
//import com.aurionpro.lms.repository.LoanSchemeRepository;
//import com.aurionpro.lms.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class LoanSchemeServiceImpl implements LoanSchemeService {
//
//	@Autowired
//	private LoanSchemeRepository loanSchemeRepository;
//
//	@Autowired
//	private UserRepository userRepository;
//
//	@Autowired
//	private AdminRepository adminRepository;
//
//	@Override
//	public LoanSchemeResponseDTO createLoanScheme(int adminId, LoanSchemeRequestDTO requestDTO) {
//		Optional<User> adminOpt = userRepository.findById(adminId);
//		if (adminOpt.isEmpty()) {
//			throw new RuntimeException("User not found with ID: " + adminId);
//		}
//		Optional<Admin> adminEntityOpt = adminRepository.findById(adminId);
//		if (adminEntityOpt.isEmpty()) {
//			throw new RuntimeException("Admin not found with ID: " + adminId);
//		}
//		Admin admin = adminEntityOpt.get();
//
//		LoanScheme loanScheme = new LoanScheme();
//		loanScheme.setSchemeName(requestDTO.getSchemeName());
//		loanScheme.setInterestRate(requestDTO.getInterestRate());
//		loanScheme.setTenureMonths(requestDTO.getTenureMonths());
//		loanScheme.setAdmin(admin);
//
//		loanScheme = loanSchemeRepository.save(loanScheme);
//
//		LoanSchemeResponseDTO dto = new LoanSchemeResponseDTO();
//		dto.setId(loanScheme.getId());
//		dto.setSchemeName(loanScheme.getSchemeName());
//		dto.setInterestRate(loanScheme.getInterestRate());
//		dto.setTenureMonths(loanScheme.getTenureMonths());
//		dto.setAdminId(loanScheme.getAdmin() != null ? loanScheme.getAdmin().getId() : 0);
//		return dto;
//	}
//
//	@Override
//	public LoanSchemeResponseDTO getLoanSchemeById(int id) {
//		Optional<LoanScheme> schemeOpt = loanSchemeRepository.findById(id);
//		if (schemeOpt.isEmpty()) {
//			throw new RuntimeException("Loan scheme not found with ID: " + id);
//		}
//		LoanScheme loanScheme = schemeOpt.get();
//
//		LoanSchemeResponseDTO dto = new LoanSchemeResponseDTO();
//		dto.setId(loanScheme.getId());
//		dto.setSchemeName(loanScheme.getSchemeName());
//		dto.setInterestRate(loanScheme.getInterestRate());
//		dto.setTenureMonths(loanScheme.getTenureMonths());
//		dto.setAdminId(loanScheme.getAdmin() != null ? loanScheme.getAdmin().getId() : 0);
//		return dto;
//	}
//
//	@Override
//	public List<LoanSchemeResponseDTO> getLoanSchemesByAdminId(int adminId) {
//		List<LoanScheme> schemes = loanSchemeRepository.findByAdminId(adminId);
//		return schemes.stream().map(scheme -> {
//			LoanSchemeResponseDTO dto = new LoanSchemeResponseDTO();
//			dto.setId(scheme.getId());
//			dto.setSchemeName(scheme.getSchemeName());
//			dto.setInterestRate(scheme.getInterestRate());
//			dto.setTenureMonths(scheme.getTenureMonths());
//			dto.setAdminId(scheme.getAdmin() != null ? scheme.getAdmin().getId() : 0);
//			return dto;
//		}).collect(Collectors.toList());
//	}
//}

//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.dto.LoanSchemeRequestDTO;
//import com.aurionpro.lms.dto.LoanSchemeResponseDTO;
//import com.aurionpro.lms.entity.Admin;
//import com.aurionpro.lms.entity.DocumentType;
//import com.aurionpro.lms.entity.LoanScheme;
//import com.aurionpro.lms.repository.AdminRepository;
//import com.aurionpro.lms.repository.DocumentTypeRepository;
//import com.aurionpro.lms.repository.LoanSchemeRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class LoanSchemeServiceImpl implements LoanSchemeService {
//
//	@Autowired
//	private LoanSchemeRepository loanSchemeRepository;
//
//	@Autowired
//	private AdminRepository adminRepository;
//
//	@Autowired
//	private DocumentTypeRepository documentTypeRepository;
//
//	@Override
//	public LoanSchemeResponseDTO createLoanScheme(int adminId, LoanSchemeRequestDTO requestDTO) {
//		Optional<Admin> adminOpt = adminRepository.findById(adminId);
//		if (adminOpt.isEmpty()) {
//			throw new RuntimeException("Admin not found with ID: " + adminId);
//		}
//		Admin admin = adminOpt.get();
//
//		List<DocumentType> requiredDocs = requestDTO.getRequiredDocumentTypeIds().stream()
//				.map(id -> documentTypeRepository.findById(id)
//						.orElseThrow(() -> new RuntimeException("Document type not found with ID: " + id)))
//				.collect(Collectors.toList());
//
//		LoanScheme loanScheme = new LoanScheme();
//		loanScheme.setSchemeName(requestDTO.getSchemeName());
//		loanScheme.setInterestRate(requestDTO.getInterestRate());
//		loanScheme.setTenureMonths(requestDTO.getTenureMonths());
//		loanScheme.setAdmin(admin);
//		loanScheme.setRequiredDocumentTypes(requiredDocs);
//
//		loanScheme = loanSchemeRepository.save(loanScheme);
//
//		return toResponseDTO(loanScheme);
//	}
//
//	@Override
//	public LoanSchemeResponseDTO getLoanSchemeById(int id) {
//		Optional<LoanScheme> schemeOpt = loanSchemeRepository.findById(id);
//		if (schemeOpt.isEmpty()) {
//			throw new RuntimeException("Loan scheme not found with ID: " + id);
//		}
//		return toResponseDTO(schemeOpt.get());
//	}
//
//	@Override
//	public List<LoanSchemeResponseDTO> getLoanSchemesByAdminId(int adminId) {
//		List<LoanScheme> schemes = loanSchemeRepository.findByAdminId(adminId);
//		return schemes.stream().map(this::toResponseDTO).collect(Collectors.toList());
//	}
//
//	private LoanSchemeResponseDTO toResponseDTO(LoanScheme loanScheme) {
//		LoanSchemeResponseDTO dto = new LoanSchemeResponseDTO();
//		dto.setId(loanScheme.getId());
//		dto.setSchemeName(loanScheme.getSchemeName());
//		dto.setInterestRate(loanScheme.getInterestRate());
//		dto.setTenureMonths(loanScheme.getTenureMonths());
//		dto.setAdminId(loanScheme.getAdmin() != null ? loanScheme.getAdmin().getId() : 0);
//		dto.setRequiredDocumentTypeNames(loanScheme.getRequiredDocumentTypes().stream().map(DocumentType::getTypeName)
//				.collect(Collectors.toList()));
//		return dto;
//	}
//}

//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.dto.LoanSchemeRequestDTO;
//import com.aurionpro.lms.dto.LoanSchemeResponseDTO;
//import com.aurionpro.lms.entity.Admin;
//import com.aurionpro.lms.entity.DocumentType;
//import com.aurionpro.lms.entity.LoanScheme;
//import com.aurionpro.lms.exception.ResourceNotFoundException;
//import com.aurionpro.lms.repository.AdminRepository;
//import com.aurionpro.lms.repository.DocumentTypeRepository;
//import com.aurionpro.lms.repository.LoanSchemeRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class LoanSchemeServiceImpl implements LoanSchemeService {
//
//	@Autowired
//	private LoanSchemeRepository loanSchemeRepository;
//
//	@Autowired
//	private AdminRepository adminRepository;
//
//	@Autowired
//	private DocumentTypeRepository documentTypeRepository;
//
//	@Override
//	public LoanSchemeResponseDTO createLoanScheme(int adminId, LoanSchemeRequestDTO requestDTO) {
//		Optional<Admin> adminOpt = adminRepository.findById(adminId);
//		if (adminOpt.isEmpty()) {
//			throw new ResourceNotFoundException("Admin not found with ID: " + adminId);
//		}
//		Admin admin = adminOpt.get();
//
//		List<DocumentType> requiredDocs = requestDTO.getRequiredDocumentTypeIds().stream()
//				.map(id -> documentTypeRepository.findById(id)
//						.orElseThrow(() -> new ResourceNotFoundException("Document type not found with ID: " + id)))
//				.collect(Collectors.toList());
//
//		LoanScheme loanScheme = new LoanScheme();
//		loanScheme.setSchemeName(requestDTO.getSchemeName());
//		loanScheme.setInterestRate(requestDTO.getInterestRate());
//		loanScheme.setTenureMonths(requestDTO.getTenureMonths());
//		loanScheme.setAdmin(admin);
//		loanScheme.setRequiredDocumentTypes(requiredDocs);
//
//		loanScheme = loanSchemeRepository.save(loanScheme);
//
//		return toResponseDTO(loanScheme);
//	}
//
//	@Override
//	public LoanSchemeResponseDTO getLoanSchemeById(int id) {
//		Optional<LoanScheme> schemeOpt = loanSchemeRepository.findById(id);
//		if (schemeOpt.isEmpty()) {
//			throw new ResourceNotFoundException("Loan scheme not found with ID: " + id);
//		}
//		return toResponseDTO(schemeOpt.get());
//	}
//
//	@Override
//	public List<LoanSchemeResponseDTO> getLoanSchemesByAdminId(int adminId) {
//		List<LoanScheme> schemes = loanSchemeRepository.findByAdminId(adminId);
//		if (schemes.isEmpty()) {
//			throw new ResourceNotFoundException("No loan schemes found for Admin ID: " + adminId);
//		}
//		return schemes.stream().map(this::toResponseDTO).collect(Collectors.toList());
//	}
//
//	private LoanSchemeResponseDTO toResponseDTO(LoanScheme loanScheme) {
//		LoanSchemeResponseDTO dto = new LoanSchemeResponseDTO();
//		dto.setId(loanScheme.getId());
//		dto.setSchemeName(loanScheme.getSchemeName());
//		dto.setInterestRate(loanScheme.getInterestRate());
//		dto.setTenureMonths(loanScheme.getTenureMonths());
//		dto.setAdminId(loanScheme.getAdmin() != null ? loanScheme.getAdmin().getId() : 0);
//		dto.setRequiredDocumentTypeNames(loanScheme.getRequiredDocumentTypes().stream().map(DocumentType::getTypeName)
//				.collect(Collectors.toList()));
//		return dto;
//	}
//}

//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.dto.LoanSchemeRequestDTO;
//import com.aurionpro.lms.dto.LoanSchemeResponseDTO;
//import com.aurionpro.lms.entity.Admin;
//import com.aurionpro.lms.entity.DocumentType;
//import com.aurionpro.lms.entity.LoanScheme;
//import com.aurionpro.lms.exception.ResourceNotFoundException;
//import com.aurionpro.lms.repository.AdminRepository;
//import com.aurionpro.lms.repository.DocumentTypeRepository;
//import com.aurionpro.lms.repository.LoanSchemeRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class LoanSchemeServiceImpl implements LoanSchemeService {
//
//	@Autowired
//	private LoanSchemeRepository loanSchemeRepository;
//
//	@Autowired
//	private AdminRepository adminRepository;
//
//	@Autowired
//	private DocumentTypeRepository documentTypeRepository;
//
//	@Override
//	public LoanSchemeResponseDTO createLoanScheme(int adminId, LoanSchemeRequestDTO requestDTO) {
//		Admin admin = adminRepository.findById(adminId)
//				.orElseThrow(() -> new ResourceNotFoundException("Admin not found with ID: " + adminId));
//
//		List<DocumentType> requiredDocs = requestDTO.getRequiredDocumentTypeIds().stream()
//				.map(id -> documentTypeRepository.findById(id)
//						.orElseThrow(() -> new ResourceNotFoundException("Document type not found with ID: " + id)))
//				.collect(Collectors.toList());
//
//		LoanScheme loanScheme = new LoanScheme();
//		loanScheme.setSchemeName(requestDTO.getSchemeName());
//		loanScheme.setInterestRate(requestDTO.getInterestRate());
//		loanScheme.setTenureMonths(requestDTO.getTenureMonths());
//		loanScheme.setAdmin(admin);
//		loanScheme.setRequiredDocumentTypes(requiredDocs);
//
//		loanScheme = loanSchemeRepository.save(loanScheme);
//
//		return toResponseDTO(loanScheme);
//	}
//
//	@Override
//	public LoanSchemeResponseDTO getLoanSchemeById(int id) {
//		LoanScheme loanScheme = loanSchemeRepository.findByIdAndIsDeletedFalse(id)
//				.orElseThrow(() -> new ResourceNotFoundException("Loan scheme not found with ID: " + id));
//		return toResponseDTO(loanScheme);
//	}
//
//	@Override
//	public List<LoanSchemeResponseDTO> getLoanSchemesByAdminId(int adminId) {
//		List<LoanScheme> schemes = loanSchemeRepository.findByAdminIdAndIsDeletedFalse(adminId);
//		if (schemes.isEmpty()) {
//			throw new ResourceNotFoundException("No loan schemes found for Admin ID: " + adminId);
//		}
//		return schemes.stream().map(this::toResponseDTO).collect(Collectors.toList());
//	}
//
//	@Override
//	public List<LoanSchemeResponseDTO> getAllLoanSchemes() {
//		List<LoanScheme> schemes = loanSchemeRepository.findAllByIsDeletedFalse();
//		return schemes.stream().map(this::toResponseDTO).collect(Collectors.toList());
//	}
//
//	@Override
//	@Transactional
//	public void softDeleteLoanScheme(Integer schemeId, Integer adminId) {
//		LoanScheme loanScheme = loanSchemeRepository.findByIdAndIsDeletedFalse(schemeId)
//				.orElseThrow(() -> new ResourceNotFoundException("Loan scheme not found with ID: " + schemeId));
//
//		if (loanScheme.getAdmin().getId() != adminId) {
//			throw new IllegalStateException("Only the admin who created the scheme can soft-delete it");
//		}
//
//		loanSchemeRepository.updateIsDeletedById(schemeId, true);
//	}
//
//	@Override
//	public List<LoanSchemeResponseDTO> getAllLoanSchemesForAdmin(boolean includeDeleted) {
//		List<LoanScheme> schemes = loanSchemeRepository.findAllLoanSchemes(includeDeleted);
//		return schemes.stream().map(this::toResponseDTO).collect(Collectors.toList());
//	}
//
//	private LoanSchemeResponseDTO toResponseDTO(LoanScheme loanScheme) {
//		LoanSchemeResponseDTO dto = new LoanSchemeResponseDTO();
//		dto.setId(loanScheme.getId());
//		dto.setSchemeName(loanScheme.getSchemeName());
//		dto.setInterestRate(loanScheme.getInterestRate());
//		dto.setTenureMonths(loanScheme.getTenureMonths());
//		dto.setAdminId(loanScheme.getAdmin() != null ? loanScheme.getAdmin().getId() : 0);
//		dto.setRequiredDocumentTypeNames(loanScheme.getRequiredDocumentTypes().stream().map(DocumentType::getTypeName)
//				.collect(Collectors.toList()));
//		dto.setDeleted(loanScheme.isDeleted());
//		return dto;
//	}
//}

package com.aurionpro.lms.service;

import com.aurionpro.lms.dto.LoanSchemeRequestDTO;
import com.aurionpro.lms.dto.LoanSchemeResponseDTO;
import com.aurionpro.lms.dto.LoanSchemeUpdateDTO;
import com.aurionpro.lms.entity.Admin;
import com.aurionpro.lms.entity.DocumentType;
import com.aurionpro.lms.entity.LoanScheme;
import com.aurionpro.lms.exception.ResourceNotFoundException;
import com.aurionpro.lms.repository.AdminRepository;
import com.aurionpro.lms.repository.DocumentTypeRepository;
import com.aurionpro.lms.repository.LoanSchemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanSchemeServiceImpl implements LoanSchemeService {

	@Autowired
	private LoanSchemeRepository loanSchemeRepository;

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private DocumentTypeRepository documentTypeRepository;

	@Override
	public LoanSchemeResponseDTO createLoanScheme(int adminId, LoanSchemeRequestDTO requestDTO) {
		Admin admin = adminRepository.findById(adminId)
				.orElseThrow(() -> new ResourceNotFoundException("Admin not found with ID: " + adminId));

		List<DocumentType> requiredDocs = requestDTO.getRequiredDocumentTypeIds().stream()
				.map(id -> documentTypeRepository.findById(id)
						.orElseThrow(() -> new ResourceNotFoundException("Document type not found with ID: " + id)))
				.collect(Collectors.toList());

		LoanScheme loanScheme = new LoanScheme();
		loanScheme.setSchemeName(requestDTO.getSchemeName());
		loanScheme.setInterestRate(requestDTO.getInterestRate());
		loanScheme.setTenureMonths(requestDTO.getTenureMonths());
		loanScheme.setAdmin(admin);
		loanScheme.setRequiredDocumentTypes(requiredDocs);

		loanScheme = loanSchemeRepository.save(loanScheme);

		return toResponseDTO(loanScheme);
	}

	@Override
	public LoanSchemeResponseDTO getLoanSchemeById(int id) {
		LoanScheme loanScheme = loanSchemeRepository.findByIdAndIsDeletedFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Loan scheme not found with ID: " + id));
		return toResponseDTO(loanScheme);
	}

	@Override
	public List<LoanSchemeResponseDTO> getLoanSchemesByAdminId(int adminId) {
		List<LoanScheme> schemes = loanSchemeRepository.findByAdminIdAndIsDeletedFalse(adminId);
		if (schemes.isEmpty()) {
			throw new ResourceNotFoundException("No loan schemes found for Admin ID: " + adminId);
		}
		return schemes.stream().map(this::toResponseDTO).collect(Collectors.toList());
	}

	@Override
	public List<LoanSchemeResponseDTO> getAllLoanSchemes() {
		List<LoanScheme> schemes = loanSchemeRepository.findAllByIsDeletedFalse();
		return schemes.stream().map(this::toResponseDTO).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void softDeleteLoanScheme(Integer schemeId, Integer adminId) {
		LoanScheme loanScheme = loanSchemeRepository.findByIdAndIsDeletedFalse(schemeId)
				.orElseThrow(() -> new ResourceNotFoundException("Loan scheme not found with ID: " + schemeId));

		if (loanScheme.getAdmin().getId() != adminId) {
			throw new IllegalStateException("Only the admin who created the scheme can soft-delete it");
		}

		loanSchemeRepository.updateIsDeletedById(schemeId, true);
	}

	@Override
	public List<LoanSchemeResponseDTO> getAllLoanSchemesForAdmin(boolean includeDeleted) {
		List<LoanScheme> schemes = loanSchemeRepository.findAllLoanSchemes(includeDeleted);
		return schemes.stream().map(this::toResponseDTO).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public LoanSchemeResponseDTO updateLoanScheme(Integer schemeId, Integer adminId, LoanSchemeUpdateDTO updateDTO) {
		LoanScheme loanScheme = loanSchemeRepository.findByIdAndIsDeletedFalse(schemeId)
				.orElseThrow(() -> new ResourceNotFoundException("Loan scheme not found with ID: " + schemeId));

		if (loanScheme.getAdmin().getId() != adminId) {
			throw new IllegalStateException("Only the admin who created the scheme can update it");
		}

		List<DocumentType> requiredDocs = updateDTO.getRequiredDocumentTypeIds().stream()
				.map(id -> documentTypeRepository.findById(id)
						.orElseThrow(() -> new ResourceNotFoundException("Document type not found with ID: " + id)))
				.collect(Collectors.toList());

		loanScheme.setInterestRate(updateDTO.getInterestRate());
		loanScheme.setTenureMonths(updateDTO.getTenureMonths());
		loanScheme.setRequiredDocumentTypes(requiredDocs);

		loanScheme = loanSchemeRepository.save(loanScheme);

		return toResponseDTO(loanScheme);
	}

	@Override
	public List<DocumentType> getRequiredDocumentTypes(int schemeId) {
        LoanScheme loanScheme = loanSchemeRepository.findById(schemeId)
                .orElseThrow(() -> new RuntimeException("Loan scheme not found"));
        return loanScheme.getRequiredDocumentTypes();
    }
	
	private LoanSchemeResponseDTO toResponseDTO(LoanScheme loanScheme) {
		LoanSchemeResponseDTO dto = new LoanSchemeResponseDTO();
		dto.setId(loanScheme.getId());
		dto.setSchemeName(loanScheme.getSchemeName());
		dto.setInterestRate(loanScheme.getInterestRate());
		dto.setTenureMonths(loanScheme.getTenureMonths());
		dto.setAdminId(loanScheme.getAdmin() != null ? loanScheme.getAdmin().getId() : 0);
		dto.setRequiredDocumentTypeNames(loanScheme.getRequiredDocumentTypes().stream().map(DocumentType::getTypeName)
				.collect(Collectors.toList()));
		dto.setDeleted(loanScheme.isDeleted());
		return dto;
	}
}