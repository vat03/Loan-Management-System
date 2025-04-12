export interface LoanScheme {
  id?: number;
  schemeName: string;
  interestRate: number;
  tenureMonths: number;
  requiredDocumentTypeIds: number[];
}

export interface DocumentType {
  id: number;
  name: string;
}