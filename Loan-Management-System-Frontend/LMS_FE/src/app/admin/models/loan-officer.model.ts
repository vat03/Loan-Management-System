export interface LoanOfficerRequest {
  username: string;
  email: string;
  password: string;
}

export interface LoanOfficerResponse {
  id: number;
  username: string;
  email: string;
  adminId: number;
  customerIds: number[];
}