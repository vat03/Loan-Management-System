export interface Loan {
  loanId: number;
  customerId: number;
  schemeId: number;
  amount: number;
  status: string;
  loanSchemeName: string;
}

export interface LoanPayment {
  id: number;
  loanId: number;
  amount: number;
  dueDate: string;
  status: string;
  penaltyAmount: number;
}

export interface LoanScheme {
  id: number;
  name: string;
  interestRate: number;
  tenure: number;
  isDeleted: boolean;
}

export interface Document {
  // documentId: number;
  // loanId: number;
  // documentTypeId: number;
  // documentUrl: string;
  documentId: number;
  loanId: number | null;
  documentTypeId: number;
  documentUrl: string;
  documentName?: string;
  status?: string;
}

export interface DocumentType {
  id: number;
  typeName: string;
}

export interface Profile {
  customerId: number;
  firstName: string;
  lastName: string;
  email: string;
  mobileNumber: string;
}