//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.dto.DocumentRequestDTO;
//import com.aurionpro.lms.dto.DocumentResponseDTO;
//import com.aurionpro.lms.entity.Customer;
//import com.aurionpro.lms.entity.Document;
//import com.aurionpro.lms.entity.DocumentType;
//import com.aurionpro.lms.entity.User;
//import com.aurionpro.lms.repository.DocumentRepository;
//import com.aurionpro.lms.repository.DocumentTypeRepository;
//import com.aurionpro.lms.repository.UserRepository;
//import com.aurionpro.lms.repository.CustomerRepository;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class DocumentServiceImpl implements DocumentService {
//
//	@Autowired
//	private DocumentRepository documentRepository;
//
//	@Autowired
//	private UserRepository userRepository;
//
//	@Autowired
//	private DocumentTypeRepository documentTypeRepository;
//
//	@Autowired
//	private CustomerRepository customerRepository;
//
//	private ModelMapper mapper;
//
//	private DocumentServiceImpl() {
//		this.mapper = new ModelMapper();
//	}
//
//	@Override
//	public DocumentResponseDTO uploadDocument(DocumentRequestDTO requestDTO) {
//		Optional<User> customerOpt = userRepository.findById(requestDTO.getCustomerId());
//		if (customerOpt.isEmpty()) {
//			throw new RuntimeException("User not found with ID: " + requestDTO.getCustomerId());
//		}
//		Optional<Customer> customerEntityOpt = customerRepository.findByUserId(requestDTO.getCustomerId());
//		if (customerEntityOpt.isEmpty()) {
//			throw new RuntimeException("Customer not found with ID: " + requestDTO.getCustomerId());
//		}
//		Customer customer = customerEntityOpt.get();
//
//		Optional<DocumentType> docTypeOpt = documentTypeRepository.findById(requestDTO.getDocumentTypeId());
//		if (docTypeOpt.isEmpty()) {
//			throw new RuntimeException("Document type not found with ID: " + requestDTO.getDocumentTypeId());
//		}
//		DocumentType documentType = docTypeOpt.get();
//
//		Document document = mapper.map(requestDTO, Document.class);
//		document.setCustomer(customer);
//		document.setDocumentType(documentType);
//
//		document = documentRepository.save(document);
//
//		mapper.typeMap(Document.class, DocumentResponseDTO.class).addMapping(src -> src.getDocumentType().getTypeName(),
//				DocumentResponseDTO::setDocumentTypeName);
//		return mapper.map(document, DocumentResponseDTO.class);
//	}
//
//	@Override
//	public DocumentResponseDTO getDocumentById(int id) {
//		Optional<Document> docOpt = documentRepository.findById(id);
//		if (docOpt.isEmpty()) {
//			throw new RuntimeException("Document not found with ID: " + id);
//		}
//		Document document = docOpt.get();
//
//		mapper.typeMap(Document.class, DocumentResponseDTO.class).addMapping(src -> src.getDocumentType().getTypeName(),
//				DocumentResponseDTO::setDocumentTypeName);
//		return mapper.map(document, DocumentResponseDTO.class);
//	}
//
//	@Override
//	public List<DocumentResponseDTO> getDocumentsByCustomerId(int customerId) {
//		List<Document> documents = documentRepository.findByCustomerId(customerId);
//		mapper.typeMap(Document.class, DocumentResponseDTO.class).addMapping(src -> src.getDocumentType().getTypeName(),
//				DocumentResponseDTO::setDocumentTypeName);
//		return documents.stream().map(doc -> mapper.map(doc, DocumentResponseDTO.class)).collect(Collectors.toList());
//	}
//}

//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.dto.DocumentRequestDTO;
//import com.aurionpro.lms.dto.DocumentResponseDTO;
//import com.aurionpro.lms.entity.Customer;
//import com.aurionpro.lms.entity.Document;
//import com.aurionpro.lms.entity.DocumentType;
//import com.aurionpro.lms.entity.User;
//import com.aurionpro.lms.repository.CustomerRepository;
//import com.aurionpro.lms.repository.DocumentRepository;
//import com.aurionpro.lms.repository.DocumentTypeRepository;
//import com.aurionpro.lms.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class DocumentServiceImpl implements DocumentService {
//
//	@Autowired
//	private DocumentRepository documentRepository;
//
//	@Autowired
//	private UserRepository userRepository;
//
//	@Autowired
//	private DocumentTypeRepository documentTypeRepository;
//
//	@Autowired
//	private CustomerRepository customerRepository;
//
//	@Override
//	public DocumentResponseDTO uploadDocument(DocumentRequestDTO requestDTO) {
//		Optional<User> customerOpt = userRepository.findById(requestDTO.getCustomerId());
//		if (customerOpt.isEmpty()) {
//			throw new RuntimeException("User not found with ID: " + requestDTO.getCustomerId());
//		}
//		Optional<Customer> customerEntityOpt = customerRepository.findByUserId(requestDTO.getCustomerId());
//		if (customerEntityOpt.isEmpty()) {
//			throw new RuntimeException("Customer not found with ID: " + requestDTO.getCustomerId());
//		}
//		Customer customer = customerEntityOpt.get();
//
//		Optional<DocumentType> docTypeOpt = documentTypeRepository.findById(requestDTO.getDocumentTypeId());
//		if (docTypeOpt.isEmpty()) {
//			throw new RuntimeException("Document type not found with ID: " + requestDTO.getDocumentTypeId());
//		}
//		DocumentType documentType = docTypeOpt.get();
//
//		Document document = new Document();
//		document.setDocumentName(requestDTO.getDocumentName());
//		document.setDocumentUrl(requestDTO.getDocumentUrl());
//		document.setCustomer(customer);
//		document.setDocumentType(documentType);
//
//		document = documentRepository.save(document);
//
//		DocumentResponseDTO dto = new DocumentResponseDTO();
//		dto.setDocumentId(document.getDocumentId());
//		dto.setDocumentName(document.getDocumentName());
//		dto.setDocumentUrl(document.getDocumentUrl());
//		dto.setCustomerId(document.getCustomer() != null ? document.getCustomer().getId() : 0);
//		dto.setDocumentTypeName(document.getDocumentType() != null ? document.getDocumentType().getTypeName() : null);
//		return dto;
//	}
//
//	@Override
//	public DocumentResponseDTO getDocumentById(int id) {
//		Optional<Document> docOpt = documentRepository.findById(id);
//		if (docOpt.isEmpty()) {
//			throw new RuntimeException("Document not found with ID: " + id);
//		}
//		Document document = docOpt.get();
//
//		DocumentResponseDTO dto = new DocumentResponseDTO();
//		dto.setDocumentId(document.getDocumentId());
//		dto.setDocumentName(document.getDocumentName());
//		dto.setDocumentUrl(document.getDocumentUrl());
//		dto.setCustomerId(document.getCustomer() != null ? document.getCustomer().getId() : 0);
//		dto.setDocumentTypeName(document.getDocumentType() != null ? document.getDocumentType().getTypeName() : null);
//		return dto;
//	}
//
//	@Override
//	public List<DocumentResponseDTO> getDocumentsByCustomerId(int customerId) {
//		List<Document> documents = documentRepository.findByCustomerId(customerId);
//		return documents.stream().map(doc -> {
//			DocumentResponseDTO dto = new DocumentResponseDTO();
//			dto.setDocumentId(doc.getDocumentId());
//			dto.setDocumentName(doc.getDocumentName());
//			dto.setDocumentUrl(doc.getDocumentUrl());
//			dto.setCustomerId(doc.getCustomer() != null ? doc.getCustomer().getId() : 0);
//			dto.setDocumentTypeName(doc.getDocumentType() != null ? doc.getDocumentType().getTypeName() : null);
//			return dto;
//		}).collect(Collectors.toList());
//	}
//}

//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.dto.DocumentRequestDTO;
//import com.aurionpro.lms.dto.DocumentResponseDTO;
//import com.aurionpro.lms.entity.Customer;
//import com.aurionpro.lms.entity.Document;
//import com.aurionpro.lms.entity.DocumentType;
//import com.aurionpro.lms.entity.Loan;
//import com.aurionpro.lms.entity.User;
//import com.aurionpro.lms.repository.CustomerRepository;
//import com.aurionpro.lms.repository.DocumentRepository;
//import com.aurionpro.lms.repository.DocumentTypeRepository;
//import com.aurionpro.lms.repository.LoanRepository;
//import com.aurionpro.lms.repository.UserRepository;
//import com.cloudinary.Cloudinary;
//import com.cloudinary.utils.ObjectUtils;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class DocumentServiceImpl implements DocumentService {
//
//	@Autowired
//	private DocumentRepository documentRepository;
//
//	@Autowired
//	private UserRepository userRepository;
//
//	@Autowired
//	private DocumentTypeRepository documentTypeRepository;
//
//	@Autowired
//	private CustomerRepository customerRepository;
//
//	@Autowired
//	private LoanRepository loanRepository;
//
//	@Autowired
//	private Cloudinary cloudinary;
//
//	@Override
//	public DocumentResponseDTO uploadDocument(DocumentRequestDTO requestDTO) {
//		// Validate customer
//		Optional<User> customerOpt = userRepository.findById(requestDTO.getCustomerId());
//		if (customerOpt.isEmpty()) {
//			throw new RuntimeException("User not found with ID: " + requestDTO.getCustomerId());
//		}
//		Optional<Customer> customerEntityOpt = customerRepository.findByUserId(requestDTO.getCustomerId());
//		if (customerEntityOpt.isEmpty()) {
//			throw new RuntimeException("Customer not found with ID: " + requestDTO.getCustomerId());
//		}
//		Customer customer = customerEntityOpt.get();
//
//		// Validate document type
//		Optional<DocumentType> docTypeOpt = documentTypeRepository.findById(requestDTO.getDocumentTypeId());
//		if (docTypeOpt.isEmpty()) {
//			throw new RuntimeException("Document type not found with ID: " + requestDTO.getDocumentTypeId());
//		}
//		DocumentType documentType = docTypeOpt.get();
//
//		// Validate loan (if provided)
//		Loan loan = null;
//		if (requestDTO.getLoanId() != null) {
//			Optional<Loan> loanOpt = loanRepository.findById(requestDTO.getLoanId());
//			if (loanOpt.isEmpty()) {
//				throw new RuntimeException("Loan not found with ID: " + requestDTO.getLoanId());
//			}
//			loan = loanOpt.get();
//			if (loan.getCustomer().getId() != customer.getId()) {
//				throw new RuntimeException("Loan does not belong to this customer");
//			}
//		}
//
//		// Upload to Cloudinary
//		Map uploadResult;
//		try {
//			uploadResult = cloudinary.uploader().upload(requestDTO.getDocumentFile().getBytes(),
//					ObjectUtils.asMap("resource_type", "auto", "folder", "lms_documents"));
//		} catch (IOException e) {
//			throw new RuntimeException("Failed to upload document to Cloudinary: " + e.getMessage());
//		}
//		String documentUrl = (String) uploadResult.get("secure_url");
//		String publicId = (String) uploadResult.get("public_id");
//
//		// Save document metadata
//		Document document = new Document();
//		document.setDocumentName(requestDTO.getDocumentName());
//		document.setDocumentUrl(documentUrl);
//		document.setPublicId(publicId);
//		document.setCustomer(customer);
//		document.setDocumentType(documentType);
//		document.setLoan(loan);
//
//		document = documentRepository.save(document);
//
//		// Prepare response
//		DocumentResponseDTO dto = new DocumentResponseDTO();
//		dto.setDocumentId(document.getDocumentId());
//		dto.setDocumentName(document.getDocumentName());
//		dto.setDocumentUrl(document.getDocumentUrl());
//		dto.setCustomerId(document.getCustomer().getId());
//		dto.setDocumentTypeName(document.getDocumentType().getTypeName());
//		return dto;
//	}
//
//	@Override
//	public DocumentResponseDTO getDocumentById(int id) {
//		Optional<Document> docOpt = documentRepository.findById(id);
//		if (docOpt.isEmpty()) {
//			throw new RuntimeException("Document not found with ID: " + id);
//		}
//		Document document = docOpt.get();
//
//		DocumentResponseDTO dto = new DocumentResponseDTO();
//		dto.setDocumentId(document.getDocumentId());
//		dto.setDocumentName(document.getDocumentName());
//		dto.setDocumentUrl(document.getDocumentUrl());
//		dto.setCustomerId(document.getCustomer() != null ? document.getCustomer().getId() : 0);
//		dto.setDocumentTypeName(document.getDocumentType() != null ? document.getDocumentType().getTypeName() : null);
//		return dto;
//	}
//
//	@Override
//	public List<DocumentResponseDTO> getDocumentsByCustomerId(int customerId) {
//		List<Document> documents = documentRepository.findByCustomerId(customerId);
//		return documents.stream().map(doc -> {
//			DocumentResponseDTO dto = new DocumentResponseDTO();
//			dto.setDocumentId(doc.getDocumentId());
//			dto.setDocumentName(doc.getDocumentName());
//			dto.setDocumentUrl(doc.getDocumentUrl());
//			dto.setCustomerId(doc.getCustomer() != null ? doc.getCustomer().getId() : 0);
//			dto.setDocumentTypeName(doc.getDocumentType() != null ? doc.getDocumentType().getTypeName() : null);
//			return dto;
//		}).collect(Collectors.toList());
//	}
//
//	@Override
//	public List<DocumentResponseDTO> getDocumentsByLoanId(int loanId) {
//		List<Document> documents = documentRepository.findByLoanLoanId(loanId);
//		if (documents.isEmpty()) {
//			throw new RuntimeException("No documents found for Loan ID: " + loanId);
//		}
//		return documents.stream().map(doc -> {
//			DocumentResponseDTO dto = new DocumentResponseDTO();
//			dto.setDocumentId(doc.getDocumentId());
//			dto.setDocumentName(doc.getDocumentName());
//			dto.setDocumentUrl(doc.getDocumentUrl());
//			dto.setCustomerId(doc.getCustomer() != null ? doc.getCustomer().getId() : 0);
//			dto.setDocumentTypeName(doc.getDocumentType() != null ? doc.getDocumentType().getTypeName() : null);
//			return dto;
//		}).collect(Collectors.toList());
//	}
//}

//package com.aurionpro.lms.service;
//
//import com.aurionpro.lms.dto.DocumentRequestDTO;
//import com.aurionpro.lms.dto.DocumentResponseDTO;
//import com.aurionpro.lms.entity.Customer;
//import com.aurionpro.lms.entity.Document;
//import com.aurionpro.lms.entity.DocumentType;
//import com.aurionpro.lms.entity.Loan;
//import com.aurionpro.lms.repository.CustomerRepository;
//import com.aurionpro.lms.repository.DocumentRepository;
//import com.aurionpro.lms.repository.DocumentTypeRepository;
//import com.aurionpro.lms.repository.LoanRepository;
//import com.aurionpro.lms.repository.UserRepository;
//import com.cloudinary.Cloudinary;
//import com.cloudinary.utils.ObjectUtils;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class DocumentServiceImpl implements DocumentService {
//
//	@Autowired
//	private DocumentRepository documentRepository;
//
//	@Autowired
//	private UserRepository userRepository;
//
//	@Autowired
//	private DocumentTypeRepository documentTypeRepository;
//
//	@Autowired
//	private CustomerRepository customerRepository;
//
//	@Autowired
//	private LoanRepository loanRepository;
//
//	@Autowired
//	private Cloudinary cloudinary;
//
//	@Override
//	public DocumentResponseDTO uploadDocument(DocumentRequestDTO requestDTO) {
//		// Validate customer by Customer ID
//		Optional<Customer> customerOpt = customerRepository.findById(requestDTO.getCustomerId());
//		if (customerOpt.isEmpty()) {
//			throw new RuntimeException("Customer not found with ID: " + requestDTO.getCustomerId());
//		}
//		Customer customer = customerOpt.get();
//
//		// Validate document type
//		Optional<DocumentType> docTypeOpt = documentTypeRepository.findById(requestDTO.getDocumentTypeId());
//		if (docTypeOpt.isEmpty()) {
//			throw new RuntimeException("Document type not found with ID: " + requestDTO.getDocumentTypeId());
//		}
//		DocumentType documentType = docTypeOpt.get();
//
//		// Validate loan (if provided)
//		Loan loan = null;
//		if (requestDTO.getLoanId() != null) {
//			Optional<Loan> loanOpt = loanRepository.findById(requestDTO.getLoanId());
//			if (loanOpt.isEmpty()) {
//				throw new RuntimeException("Loan not found with ID: " + requestDTO.getLoanId());
//			}
//			loan = loanOpt.get();
//			if (loan.getCustomer().getId() != customer.getId()) {
//				throw new RuntimeException("Loan does not belong to this customer");
//			}
//		}
//
//		// Upload to Cloudinary
//		Map uploadResult;
//		try {
//			uploadResult = cloudinary.uploader().upload(requestDTO.getDocumentFile().getBytes(),
//					ObjectUtils.asMap("resource_type", "auto", "folder", "lms_documents"));
//		} catch (IOException e) {
//			throw new RuntimeException("Failed to upload document to Cloudinary: " + e.getMessage());
//		}
//		String documentUrl = (String) uploadResult.get("secure_url");
//		String publicId = (String) uploadResult.get("public_id");
//
//		// Save document metadata
//		Document document = new Document();
//		document.setDocumentName(requestDTO.getDocumentName());
//		document.setDocumentUrl(documentUrl);
//		document.setPublicId(publicId);
//		document.setCustomer(customer);
//		document.setDocumentType(documentType);
//		document.setLoan(loan);
//
//		document = documentRepository.save(document);
//
//		// Prepare response
//		DocumentResponseDTO dto = new DocumentResponseDTO();
//		dto.setDocumentId(document.getDocumentId());
//		dto.setDocumentName(document.getDocumentName());
//		dto.setDocumentUrl(document.getDocumentUrl());
//		dto.setCustomerId(document.getCustomer().getId());
//		dto.setDocumentTypeName(document.getDocumentType().getTypeName());
//		return dto;
//	}
//
//	@Override
//	public DocumentResponseDTO getDocumentById(int id) {
//		Optional<Document> docOpt = documentRepository.findById(id);
//		if (docOpt.isEmpty()) {
//			throw new RuntimeException("Document not found with ID: " + id);
//		}
//		Document document = docOpt.get();
//
//		DocumentResponseDTO dto = new DocumentResponseDTO();
//		dto.setDocumentId(document.getDocumentId());
//		dto.setDocumentName(document.getDocumentName());
//		dto.setDocumentUrl(document.getDocumentUrl());
//		dto.setCustomerId(document.getCustomer() != null ? document.getCustomer().getId() : 0);
//		dto.setDocumentTypeName(document.getDocumentType() != null ? document.getDocumentType().getTypeName() : null);
//		return dto;
//	}
//
//	@Override
//	public List<DocumentResponseDTO> getDocumentsByCustomerId(int customerId) {
//		List<Document> documents = documentRepository.findByCustomerId(customerId);
//		return documents.stream().map(doc -> {
//			DocumentResponseDTO dto = new DocumentResponseDTO();
//			dto.setDocumentId(doc.getDocumentId());
//			dto.setDocumentName(doc.getDocumentName());
//			dto.setDocumentUrl(doc.getDocumentUrl());
//			dto.setCustomerId(doc.getCustomer() != null ? doc.getCustomer().getId() : 0);
//			dto.setDocumentTypeName(doc.getDocumentType() != null ? doc.getDocumentType().getTypeName() : null);
//			return dto;
//		}).collect(Collectors.toList());
//	}
//
//	@Override
//	public List<DocumentResponseDTO> getDocumentsByLoanId(int loanId) {
//		List<Document> documents = documentRepository.findByLoanLoanId(loanId);
//		if (documents.isEmpty()) {
//			throw new RuntimeException("No documents found for Loan ID: " + loanId);
//		}
//		return documents.stream().map(doc -> {
//			DocumentResponseDTO dto = new DocumentResponseDTO();
//			dto.setDocumentId(doc.getDocumentId());
//			dto.setDocumentName(doc.getDocumentName());
//			dto.setDocumentUrl(doc.getDocumentUrl());
//			dto.setCustomerId(doc.getCustomer() != null ? doc.getCustomer().getId() : 0);
//			dto.setDocumentTypeName(doc.getDocumentType() != null ? doc.getDocumentType().getTypeName() : null);
//			return dto;
//		}).collect(Collectors.toList());
//	}
//}

package com.aurionpro.lms.service;

import com.aurionpro.lms.dto.DocumentRequestDTO;
import com.aurionpro.lms.dto.DocumentResponseDTO;
import com.aurionpro.lms.dto.DocumentVerificationDTO;
import com.aurionpro.lms.dto.LoanUpdateDTO;
import com.aurionpro.lms.entity.Customer;
import com.aurionpro.lms.entity.Document;
import com.aurionpro.lms.entity.DocumentType;
import com.aurionpro.lms.entity.Loan;
import com.aurionpro.lms.repository.CustomerRepository;
import com.aurionpro.lms.repository.DocumentRepository;
import com.aurionpro.lms.repository.DocumentTypeRepository;
import com.aurionpro.lms.repository.LoanRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl implements DocumentService {

	@Autowired
	private DocumentRepository documentRepository;

	@Autowired
	private DocumentTypeRepository documentTypeRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private LoanRepository loanRepository;

	@Autowired
	private LoanService loanService;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private Cloudinary cloudinary;

	@Override
	public DocumentResponseDTO uploadDocument(DocumentRequestDTO requestDTO) {
		Optional<Customer> customerOpt = customerRepository.findById(requestDTO.getCustomerId());
		if (customerOpt.isEmpty()) {
			throw new RuntimeException("Customer not found with ID: " + requestDTO.getCustomerId());
		}
		Customer customer = customerOpt.get();

		Optional<DocumentType> docTypeOpt = documentTypeRepository.findById(requestDTO.getDocumentTypeId());
		if (docTypeOpt.isEmpty()) {
			throw new RuntimeException("Document type not found with ID: " + requestDTO.getDocumentTypeId());
		}
		DocumentType documentType = docTypeOpt.get();

		Loan loan = null;
		if (requestDTO.getLoanId() != null) {
			Optional<Loan> loanOpt = loanRepository.findById(requestDTO.getLoanId());
			if (loanOpt.isEmpty()) {
				throw new RuntimeException("Loan not found with ID: " + requestDTO.getLoanId());
			}
			loan = loanOpt.get();
			if (loan.getCustomer().getId() != customer.getId()) {
				throw new RuntimeException("Loan does not belong to this customer");
			}
			if (!loan.getLoanScheme().getRequiredDocumentTypes().contains(documentType)) {
				throw new RuntimeException("Document type not required for this loan scheme");
			}
		}

		Map uploadResult;
		try {
			uploadResult = cloudinary.uploader().upload(requestDTO.getDocumentFile().getBytes(),
					ObjectUtils.asMap("resource_type", "auto", "folder", "lms_documents"));
		} catch (IOException e) {
			throw new RuntimeException("Failed to upload document to Cloudinary: " + e.getMessage());
		}
		String documentUrl = (String) uploadResult.get("secure_url");
		String publicId = (String) uploadResult.get("public_id");

		Document document = new Document();
		document.setDocumentName(requestDTO.getDocumentName());
		document.setDocumentUrl(documentUrl);
		document.setPublicId(publicId);
		document.setCustomer(customer);
		document.setDocumentType(documentType);
		document.setLoan(loan);
		document.setStatus(Document.DocumentStatus.PENDING_VERIFICATION);

		document = documentRepository.save(document);
		return toResponseDTO(document);
	}

	@Override
	public DocumentResponseDTO getDocumentById(int id) {
		Optional<Document> docOpt = documentRepository.findById(id);
		if (docOpt.isEmpty()) {
			throw new RuntimeException("Document not found with ID: " + id);
		}
		return toResponseDTO(docOpt.get());
	}

	@Override
	public List<DocumentResponseDTO> getDocumentsByCustomerId(int customerId) {
		List<Document> documents = documentRepository.findByCustomerId(customerId);
		return documents.stream().map(this::toResponseDTO).collect(Collectors.toList());
	}

	@Override
	public List<DocumentResponseDTO> getDocumentsByLoanId(int loanId) {
		List<Document> documents = documentRepository.findByLoanLoanId(loanId);
		if (documents.isEmpty()) {
			throw new RuntimeException("No documents found for Loan ID: " + loanId);
		}
		return documents.stream().map(this::toResponseDTO).collect(Collectors.toList());
	}

	@Override
	public DocumentResponseDTO verifyDocument(int documentId, DocumentVerificationDTO verificationDTO) {
		Optional<Document> docOpt = documentRepository.findById(documentId);
		if (docOpt.isEmpty()) {
			throw new RuntimeException("Document not found with ID: " + documentId);
		}
		Document document = docOpt.get();

		if (document.getLoan() == null) {
			throw new RuntimeException("Document not associated with a loan");
		}

		Document.DocumentStatus newStatus = Document.DocumentStatus.valueOf(verificationDTO.getStatus());
		document.setStatus(newStatus);
		document = documentRepository.save(document);

		updateLoanStatus(document.getLoan());

		if (newStatus == Document.DocumentStatus.REJECTED) {
			notificationService.sendDocumentRejectionEmail(document.getLoan().getLoanId(), document);
		}

		return toResponseDTO(document);
	}

	private void updateLoanStatus(Loan loan) {
		List<Document> documents = documentRepository.findByLoanLoanId(loan.getLoanId());
		boolean allApproved = documents.stream().allMatch(d -> d.getStatus() == Document.DocumentStatus.APPROVED);
		boolean anyRejected = documents.stream().anyMatch(d -> d.getStatus() == Document.DocumentStatus.REJECTED);

		String newStatus = "PENDING";
		if (allApproved) {
			newStatus = "APPROVED";
		} else if (anyRejected) {
			newStatus = "REJECTED";
		}

		// Correct instantiation of LoanUpdateDTO
		LoanUpdateDTO loanUpdateDTO = new LoanUpdateDTO();
		loanUpdateDTO.setStatusName(newStatus);
		loanService.updateLoanStatus(loan.getLoanId(), loanUpdateDTO);
	}

	private DocumentResponseDTO toResponseDTO(Document document) {
		DocumentResponseDTO dto = new DocumentResponseDTO();
		dto.setDocumentId(document.getDocumentId());
		dto.setDocumentName(document.getDocumentName());
		dto.setDocumentUrl(document.getDocumentUrl());
		dto.setCustomerId(document.getCustomer() != null ? document.getCustomer().getId() : 0);
		dto.setDocumentTypeName(document.getDocumentType() != null ? document.getDocumentType().getTypeName() : null);
		dto.setStatus(document.getStatus().name());
		return dto;
	}
}