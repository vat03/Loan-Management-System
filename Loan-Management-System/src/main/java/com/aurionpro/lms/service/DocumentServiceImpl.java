//package com.aurionpro.lms.service;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.aurionpro.lms.dto.DocumentRequestDTO;
//import com.aurionpro.lms.dto.DocumentResponseDTO;
//import com.aurionpro.lms.dto.DocumentVerificationDTO;
//import com.aurionpro.lms.dto.LoanUpdateDTO;
//import com.aurionpro.lms.entity.Customer;
//import com.aurionpro.lms.entity.Document;
//import com.aurionpro.lms.entity.DocumentType;
//import com.aurionpro.lms.entity.Loan;
//import com.aurionpro.lms.exception.BusinessRuleViolationException;
//import com.aurionpro.lms.exception.DocumentUploadException;
//import com.aurionpro.lms.exception.InvalidInputException;
//import com.aurionpro.lms.exception.ResourceNotFoundException;
//import com.aurionpro.lms.repository.CustomerRepository;
//import com.aurionpro.lms.repository.DocumentRepository;
//import com.aurionpro.lms.repository.DocumentTypeRepository;
//import com.aurionpro.lms.repository.LoanRepository;
//import com.cloudinary.Cloudinary;
//import com.cloudinary.utils.ObjectUtils;
//
//@Service
//public class DocumentServiceImpl implements DocumentService {
//
//    @Autowired
//    private DocumentRepository documentRepository;
//
//    @Autowired
//    private DocumentTypeRepository documentTypeRepository;
//
//    @Autowired
//    private CustomerRepository customerRepository;
//
//    @Autowired
//    private LoanRepository loanRepository;
//
//    @Autowired
//    private LoanService loanService;
//
//    @Autowired
//    private NotificationService notificationService;
//
//    @Autowired
//    private Cloudinary cloudinary;
//
//    @Override
//    public DocumentResponseDTO uploadDocument(DocumentRequestDTO requestDTO) {
//        Optional<Customer> customerOpt = customerRepository.findById(requestDTO.getCustomerId());
//        if (customerOpt.isEmpty()) {
//            throw new ResourceNotFoundException("Customer not found with ID: " + requestDTO.getCustomerId());
//        }
//        Customer customer = customerOpt.get();
//
//        Optional<DocumentType> docTypeOpt = documentTypeRepository.findById(requestDTO.getDocumentTypeId());
//        if (docTypeOpt.isEmpty()) {
//            throw new ResourceNotFoundException("Document type not found with ID: " + requestDTO.getDocumentTypeId());
//        }
//        DocumentType documentType = docTypeOpt.get();
//
//        Loan loan = null;
//        if (requestDTO.getLoanId() != null) {
//            Optional<Loan> loanOpt = loanRepository.findById(requestDTO.getLoanId());
//            if (loanOpt.isEmpty()) {
//                throw new ResourceNotFoundException("Loan not found with ID: " + requestDTO.getLoanId());
//            }
//            loan = loanOpt.get();
//            if (loan.getCustomer().getId() != customer.getId()) {
//                throw new BusinessRuleViolationException("Loan does not belong to this customer");
//            }
//            if (!loan.getLoanScheme().getRequiredDocumentTypes().contains(documentType)) {
//                throw new BusinessRuleViolationException("Document type not required for this loan scheme");
//            }
//        }
//
//        Map uploadResult;
//        try {
//            uploadResult = cloudinary.uploader().upload(requestDTO.getDocumentFile().getBytes(),
//                    ObjectUtils.asMap("resource_type", "auto", "folder", "lms_documents"));
//        } catch (IOException e) {
//            throw new DocumentUploadException("Failed to upload document to Cloudinary: " + e.getMessage());
//        }
//        String documentUrl = (String) uploadResult.get("secure_url");
//        String publicId = (String) uploadResult.get("public_id");
//
//        Document document = new Document();
//        document.setDocumentName(requestDTO.getDocumentName());
//        document.setDocumentUrl(documentUrl);
//        document.setPublicId(publicId);
//        document.setCustomer(customer);
//        document.setDocumentType(documentType);
//        document.setLoan(loan);
//        document.setStatus(Document.DocumentStatus.PENDING_VERIFICATION);
//
//        document = documentRepository.save(document);
//        return toResponseDTO(document);
//    }
//
//    @Override
//    public DocumentResponseDTO getDocumentById(int id) {
//        Optional<Document> docOpt = documentRepository.findById(id);
//        if (docOpt.isEmpty()) {
//            throw new ResourceNotFoundException("Document not found with ID: " + id);
//        }
//        return toResponseDTO(docOpt.get());
//    }
//
//    @Override
//    public List<DocumentResponseDTO> getDocumentsByCustomerId(int customerId) {
//        List<Document> documents = documentRepository.findByCustomerId(customerId);
//        return documents.stream().map(this::toResponseDTO).collect(Collectors.toList());
//    }
//
//    @Override
//    public List<DocumentResponseDTO> getDocumentsByLoanId(int loanId) {
//        List<Document> documents = documentRepository.findByLoanLoanId(loanId);
//        return documents.stream().map(this::toResponseDTO).collect(Collectors.toList());
//    }
//
//    @Override
//    public List<DocumentResponseDTO> getDocumentsByLoanIdAndStatus(int loanId, String status) {
//        try {
//            Document.DocumentStatus documentStatus = Document.DocumentStatus.valueOf(status);
//            List<Document> documents = documentRepository.findByLoanLoanIdAndStatus(loanId, documentStatus);
//            return documents.stream().map(this::toResponseDTO).collect(Collectors.toList());
//        } catch (IllegalArgumentException e) {
//            throw new InvalidInputException("Invalid document status: " + status);
//        }
//    }
//    
//    @Override
//    public List<DocumentResponseDTO> getDocumentsByLoanOfficerId(int loanOfficerId, String status) {
//        try {
//            Document.DocumentStatus documentStatus = Document.DocumentStatus.valueOf(status);
//            List<Loan> loans = loanRepository.findByLoanOfficerId(loanOfficerId);
//            List<Document> documents = new ArrayList<>();
//            for (Loan loan : loans) {
//                documents.addAll(documentRepository.findByLoanLoanIdAndStatus(loan.getLoanId(), documentStatus));
//            }
//            return documents.stream().map(this::toResponseDTO).collect(Collectors.toList());
//        } catch (IllegalArgumentException e) {
//            throw new InvalidInputException("Invalid document status: " + status);
//        }
//    }
//
//    @Override
//    public DocumentResponseDTO verifyDocument(int documentId, DocumentVerificationDTO verificationDTO) {
//        Optional<Document> docOpt = documentRepository.findById(documentId);
//        if (docOpt.isEmpty()) {
//            throw new ResourceNotFoundException("Document not found with ID: " + documentId);
//        }
//        Document document = docOpt.get();
//
//        if (document.getLoan() == null) {
//            throw new BusinessRuleViolationException("Document not associated with a loan");
//        }
//
//        try {
//            Document.DocumentStatus newStatus = Document.DocumentStatus.valueOf(verificationDTO.getStatus());
//            document.setStatus(newStatus);
//            document = documentRepository.save(document);
//
//            updateLoanStatus(document.getLoan());
//
//            if (newStatus == Document.DocumentStatus.REJECTED) {
//                notificationService.sendDocumentRejectionEmail(document.getLoan().getLoanId(), document);
//            }
//
//            return toResponseDTO(document);
//        } catch (IllegalArgumentException e) {
//            throw new InvalidInputException("Invalid document status: " + verificationDTO.getStatus());
//        }
//    }
//
//    private void updateLoanStatus(Loan loan) {
//        List<Document> documents = documentRepository.findByLoanLoanId(loan.getLoanId());
//        boolean allApproved = documents.stream().allMatch(d -> d.getStatus() == Document.DocumentStatus.APPROVED);
//        boolean anyRejected = documents.stream().anyMatch(d -> d.getStatus() == Document.DocumentStatus.REJECTED);
//
//        String newStatus = "PENDING";
//        if (allApproved) {
//            newStatus = "APPROVED";
//        } else if (anyRejected) {
//            newStatus = "REJECTED";
//        }
//
//        LoanUpdateDTO loanUpdateDTO = new LoanUpdateDTO();
//        loanUpdateDTO.setStatusName(newStatus);
//        loanService.updateLoanStatus(loan.getLoanId(), loanUpdateDTO);
//    }
//
//    private DocumentResponseDTO toResponseDTO(Document document) {
//        DocumentResponseDTO dto = new DocumentResponseDTO();
//        dto.setDocumentId(document.getDocumentId());
//        dto.setDocumentName(document.getDocumentName());
//        dto.setDocumentUrl(document.getDocumentUrl());
//        dto.setCustomerId(document.getCustomer() != null ? document.getCustomer().getId() : 0);
//        dto.setDocumentTypeName(document.getDocumentType() != null ? document.getDocumentType().getTypeName() : null);
//        dto.setStatus(document.getStatus().name());
//        return dto;
//    }
//}


//upar wala is perfect



package com.aurionpro.lms.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aurionpro.lms.dto.DocumentRequestDTO;
import com.aurionpro.lms.dto.DocumentResponseDTO;
import com.aurionpro.lms.dto.DocumentVerificationDTO;
import com.aurionpro.lms.dto.LoanUpdateDTO;
import com.aurionpro.lms.entity.Customer;
import com.aurionpro.lms.entity.Document;
import com.aurionpro.lms.entity.DocumentType;
import com.aurionpro.lms.entity.Loan;
import com.aurionpro.lms.exception.BusinessRuleViolationException;
import com.aurionpro.lms.exception.DocumentUploadException;
import com.aurionpro.lms.exception.InvalidInputException;
import com.aurionpro.lms.exception.ResourceNotFoundException;
import com.aurionpro.lms.repository.CustomerRepository;
import com.aurionpro.lms.repository.DocumentRepository;
import com.aurionpro.lms.repository.DocumentTypeRepository;
import com.aurionpro.lms.repository.LoanRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

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
            throw new ResourceNotFoundException("Customer not found with ID: " + requestDTO.getCustomerId());
        }
        Customer customer = customerOpt.get();

        Optional<DocumentType> docTypeOpt = documentTypeRepository.findById(requestDTO.getDocumentTypeId());
        if (docTypeOpt.isEmpty()) {
            throw new ResourceNotFoundException("Document type not found with ID: " + requestDTO.getDocumentTypeId());
        }
        DocumentType documentType = docTypeOpt.get();

        Loan loan = null;
        if (requestDTO.getLoanId() != null) {
            Optional<Loan> loanOpt = loanRepository.findById(requestDTO.getLoanId());
            if (loanOpt.isEmpty()) {
                throw new ResourceNotFoundException("Loan not found with ID: " + requestDTO.getLoanId());
            }
            loan = loanOpt.get();
            if (loan.getCustomer().getId() != customer.getId()) {
                throw new BusinessRuleViolationException("Loan does not belong to this customer");
            }
            if (!loan.getLoanScheme().getRequiredDocumentTypes().contains(documentType)) {
                throw new BusinessRuleViolationException("Document type not required for this loan scheme");
            }
        }

        Map uploadResult;
        try {
            uploadResult = cloudinary.uploader().upload(requestDTO.getDocumentFile().getBytes(),
                    ObjectUtils.asMap("resource_type", "auto", "folder", "lms_documents"));
        } catch (IOException e) {
            throw new DocumentUploadException("Failed to upload document to Cloudinary: " + e.getMessage());
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
    public void updateDocumentLoanIds(List<Integer> documentIds, Integer loanId) {
        if (documentIds == null || documentIds.isEmpty()) {
            throw new InvalidInputException("At least one document ID must be provided");
        }
        if (loanId == null) {
            throw new InvalidInputException("Loan ID is required");
        }

        Optional<Loan> loanOpt = loanRepository.findById(loanId);
        if (loanOpt.isEmpty()) {
            throw new ResourceNotFoundException("Loan not found with ID: " + loanId);
        }
        Loan loan = loanOpt.get();

        for (Integer documentId : documentIds) {
            Optional<Document> docOpt = documentRepository.findById(documentId);
            if (docOpt.isEmpty()) {
                throw new ResourceNotFoundException("Document not found with ID: " + documentId);
            }
            Document document = docOpt.get();

            if (document.getCustomer().getId() != loan.getCustomer().getId()) {
                throw new BusinessRuleViolationException("Document does not belong to the loan's customer");
            }
            if (!loan.getLoanScheme().getRequiredDocumentTypes().contains(document.getDocumentType())) {
                throw new BusinessRuleViolationException("Document type not required for this loan scheme");
            }

            document.setLoan(loan);
            documentRepository.save(document);
        }
    }

    @Override
    public DocumentResponseDTO getDocumentById(int id) {
        Optional<Document> docOpt = documentRepository.findById(id);
        if (docOpt.isEmpty()) {
            throw new ResourceNotFoundException("Document not found with ID: " + id);
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
        return documents.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Override
    public List<DocumentResponseDTO> getDocumentsByLoanIdAndStatus(int loanId, String status) {
        try {
            Document.DocumentStatus documentStatus = Document.DocumentStatus.valueOf(status);
            List<Document> documents = documentRepository.findByLoanLoanIdAndStatus(loanId, documentStatus);
            return documents.stream().map(this::toResponseDTO).collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException("Invalid document status: " + status);
        }
    }
    
    @Override
    public List<DocumentResponseDTO> getDocumentsByLoanOfficerId(int loanOfficerId, String status) {
        try {
            Document.DocumentStatus documentStatus = Document.DocumentStatus.valueOf(status);
            List<Loan> loans = loanRepository.findByLoanOfficerId(loanOfficerId);
            List<Document> documents = new ArrayList<>();
            for (Loan loan : loans) {
                documents.addAll(documentRepository.findByLoanLoanIdAndStatus(loan.getLoanId(), documentStatus));
            }
            return documents.stream().map(this::toResponseDTO).collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException("Invalid document status: " + status);
        }
    }

    @Override
    public DocumentResponseDTO verifyDocument(int documentId, DocumentVerificationDTO verificationDTO) {
        Optional<Document> docOpt = documentRepository.findById(documentId);
        if (docOpt.isEmpty()) {
            throw new ResourceNotFoundException("Document not found with ID: " + documentId);
        }
        Document document = docOpt.get();

        if (document.getLoan() == null) {
            throw new BusinessRuleViolationException("Document not associated with a loan");
        }

        try {
            Document.DocumentStatus newStatus = Document.DocumentStatus.valueOf(verificationDTO.getStatus());
            document.setStatus(newStatus);
            document = documentRepository.save(document);

            updateLoanStatus(document.getLoan());

            if (newStatus == Document.DocumentStatus.REJECTED) {
                notificationService.sendDocumentRejectionEmail(document.getLoan().getLoanId(), document);
            }

            return toResponseDTO(document);
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException("Invalid document status: " + verificationDTO.getStatus());
        }
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