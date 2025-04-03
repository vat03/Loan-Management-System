package com.aurionpro.lms.service;

import com.aurionpro.lms.dto.DocumentRequestDTO;
import com.aurionpro.lms.dto.DocumentResponseDTO;
import com.aurionpro.lms.entity.Customer;
import com.aurionpro.lms.entity.Document;
import com.aurionpro.lms.entity.DocumentType;
import com.aurionpro.lms.entity.User;
import com.aurionpro.lms.repository.DocumentRepository;
import com.aurionpro.lms.repository.DocumentTypeRepository;
import com.aurionpro.lms.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl implements DocumentService {

	@Autowired
	private DocumentRepository documentRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DocumentTypeRepository documentTypeRepository;

	private ModelMapper mapper;
	
	private DocumentServiceImpl() {
		this.mapper = new ModelMapper();
	}

	@Override
	public DocumentResponseDTO uploadDocument(DocumentRequestDTO requestDTO) {
		Optional<User> customerOpt = userRepository.findById(requestDTO.getCustomerId());
		if (customerOpt.isEmpty() || !(customerOpt.get().getUserType() instanceof Customer)) {
			throw new RuntimeException("Customer not found with ID: " + requestDTO.getCustomerId());
		}
		Customer customer = (Customer) customerOpt.get().getUserType();

		Optional<DocumentType> docTypeOpt = documentTypeRepository.findById(requestDTO.getDocumentTypeId());
		if (docTypeOpt.isEmpty()) {
			throw new RuntimeException("Document type not found with ID: " + requestDTO.getDocumentTypeId());
		}
		DocumentType documentType = docTypeOpt.get();

		Document document = mapper.map(requestDTO, Document.class);
		document.setCustomer(customer);
		document.setDocumentType(documentType);

		document = documentRepository.save(document);

		//ModelMapper mapper = new ModelMapper();
		mapper.typeMap(Document.class, DocumentResponseDTO.class).addMapping(src -> src.getDocumentType().getTypeName(),
				DocumentResponseDTO::setDocumentTypeName);
		return mapper.map(document, DocumentResponseDTO.class);
	}

	@Override
	public DocumentResponseDTO getDocumentById(int id) {
		Optional<Document> docOpt = documentRepository.findById(id);
		if (docOpt.isEmpty()) {
			throw new RuntimeException("Document not found with ID: " + id);
		}
		Document document = docOpt.get();

		//ModelMapper mapper = new ModelMapper();
		mapper.typeMap(Document.class, DocumentResponseDTO.class).addMapping(src -> src.getDocumentType().getTypeName(),
				DocumentResponseDTO::setDocumentTypeName);
		return mapper.map(document, DocumentResponseDTO.class);
	}

	@Override
	public List<DocumentResponseDTO> getDocumentsByCustomerId(int customerId) {
		List<Document> documents = documentRepository.findByCustomerId(customerId);
		//ModelMapper mapper = new ModelMapper();
		mapper.typeMap(Document.class, DocumentResponseDTO.class).addMapping(src -> src.getDocumentType().getTypeName(),
				DocumentResponseDTO::setDocumentTypeName);
		return documents.stream().map(doc -> mapper.map(doc, DocumentResponseDTO.class)).collect(Collectors.toList());
	}
}