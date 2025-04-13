export interface LoanSchemeRequest {
    schemeName: string;
    interestRate: number;
    tenureMonths: number;
    requiredDocumentTypeIds: number[];
  }
  
  export interface LoanSchemeResponse {
    id: number;
    schemeName: string;
    interestRate: number;
    tenureMonths: number;
    adminId: number;
    requiredDocumentTypeNames: string[];
    isDeleted: boolean;
  }
  
  export interface LoanSchemeUpdate {
    interestRate: number;
    tenureMonths: number;
    requiredDocumentTypeIds: number[];
  }
  
  export interface LoanSchemeSoftDelete {
    adminId: number;
  }