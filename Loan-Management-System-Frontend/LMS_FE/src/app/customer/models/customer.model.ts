// export interface LoanScheme {
//     id: number;
//     schemeName: string;
//     interestRate: number;
//     tenureMonths: number;
//     adminId: number;
//     requiredDocumentTypeNames: string[];
//     isDeleted: boolean;
// }

// export interface Loan {
//     loanId: number;
//     amount: number;
//     loanSchemeName: string;
//     statusName: string;
//     applicationDate: string;
//     dueDate: string;
//     loanOfficerId: number;
//     customerId: number;
//     isNpa: boolean;
// }

// export interface Document {
//     documentId: number;
//     documentName: string;
//     documentUrl: string;
//     customerId: number;
//     documentTypeName: string;
//     status: string;
// }

// export interface LoanPayment {
//     id: number;
//     loanId: number;
//     amount: number;
//     dueDate: string;
//     status: string;
//     penaltyAmount: number;
// }

// export interface Profile {
//     id: number;
//     username: string;
//     email: string;
//     roleName: string;
//     firstName: string;
//     lastName: string;
//     dateOfBirth: string;
//     mobileNumber: string;
//     gender: string;
// }








// export interface LoanScheme {
//     id: number;
//     schemeName: string;
//     interestRate: number;
//     tenureMonths: number;
//     adminId: number;
//     requiredDocumentTypeNames: string[];
//     requiredDocumentTypes: DocumentType[];
//     isDeleted: boolean;
//   }

//   export interface Loan {
//     loanId: number;
//     amount: number;
//     loanSchemeName: string;
//     statusName: string;
//     applicationDate: string;
//     dueDate: string;
//     loanOfficerId: number;
//     customerId: number;
//     isNpa: boolean;
//   }

//   export interface Document {
//     documentId: number;
//     documentName: string;
//     documentUrl: string;
//     publicId: string;
//     customerId: number;
//     loanId?: number;
//     documentTypeId: number;
//     documentTypeName: string;
//     status: string;
//   }

//   export interface DocumentType {
//     id: number;
//     typeName: string;
//   }

//   export interface LoanPayment {
//     id: number;
//     loanId: number;
//     amount: number;
//     dueDate: string;
//     status: string;
//     penaltyAmount: number;
//   }

//   export interface Profile {
//     id: number;
//     username: string;
//     email: string;
//     roleName: string;
//     firstName: string;
//     lastName: string;
//     dateOfBirth: string;
//     mobileNumber: string;
//     gender: string;
//   }





// export interface Loan {
//   loanId: number;
//   customerId: number;
//   schemeId: number;
//   amount: number;
//   status: string;
//   loanSchemeName: string;
// }

// export interface LoanPayment {
//   id: number;
//   loanId: number;
//   amount: number;
//   dueDate: string;
//   status: string;
//   penaltyAmount: number;
// }

// export interface LoanScheme {
//   id: number;
//   name: string; // Added name property
//   interestRate: number;
//   isDeleted: boolean;
// }

// export interface Document {
//   documentId: number;
//   loanId: number;
//   documentTypeId: number;
//   documentUrl: string;
// }

// export interface DocumentType {
//   id: number;
//   name: string; // Added name property
// }

// export interface Profile {
//   customerId: number;
//   firstName: string;
//   lastName: string;
//   email: string;
//   mobileNumber: string;
// }


// export interface Loan {
//   loanId: number;
//   customerId: number;
//   schemeId: number;
//   amount: number;
//   status: string;
//   loanSchemeName: string;
// }

// export interface LoanPayment {
//   id: number;
//   loanId: number;
//   amount: number;
//   dueDate: string;
//   status: string;
//   penaltyAmount: number;
// }

// export interface LoanScheme {
//   id: number;
//   name: string;
//   interestRate: number;
//   tenure: number;
//   isDeleted: boolean;
// }

// export interface Document {
//   documentId: number;
//   loanId: number;
//   documentTypeId: number;
//   documentUrl: string;
// }

// export interface DocumentType {
//   id: number;
//   name: string;
// }

// export interface Profile {
//   customerId: number;
//   firstName: string;
//   lastName: string;
//   email: string;
//   mobileNumber: string;
// }




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
  documentId: number;
  loanId: number;
  documentTypeId: number;
  documentUrl: string;
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