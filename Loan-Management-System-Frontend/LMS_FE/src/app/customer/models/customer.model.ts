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



export interface LoanScheme {
    id: number;
    schemeName: string;
    interestRate: number;
    tenureMonths: number;
    adminId: number;
    requiredDocumentTypeNames: string[];
    isDeleted: boolean;
}

export interface Loan {
    loanId: number;
    amount: number;
    loanSchemeName: string;
    statusName: string;
    applicationDate: string;
    dueDate: string;
    loanOfficerId: number;
    customerId: number;
    isNpa: boolean;
}

export interface Document {
    documentId: number;
    documentName: string;
    documentUrl: string;
    customerId: number;
    documentTypeName: string;
    status: string;
}

export interface LoanPayment {
    id: number;
    loanId: number;
    amount: number;
    dueDate: string;
    status: string;
    penaltyAmount: number;
}

export interface Profile {
    id: number;
    username: string;
    email: string;
    roleName: string;
    firstName: string;
    lastName: string;
    dateOfBirth: string;
    mobileNumber: string;
    gender: string;
}