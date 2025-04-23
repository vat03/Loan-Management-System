export interface LoanResponseDTO {
    loanId: number;
    customerId: number;
    loanOfficerId: number;
    amount: number;
    loanSchemeName: string;
    statusName: string;
    applicationDate: string;
    dueDate: string;
    isNpa: boolean;
}

export interface DocumentResponseDTO {
    documentId: number;
    documentName: string;
    documentUrl: string;
    publicId: string;
    customerId: number;
    loanId: number;
    documentTypeName: string;
    status: string;
}

export interface CustomerResponseDTO {
    id: number;
    username: string;
    email: string;
    firstName: string;
    lastName: string;
}