export interface ProfileResponseDTO {
    id: number;
    username: string;
    email: string;
    roleName: string;
    firstName: string | null;
    lastName: string | null;
    dateOfBirth: string | null;
    mobileNumber: string | null;
    gender: string | null;
}

export interface ProfileUpdateRequestDTO {
    password: string;
    firstName?: string;
    lastName?: string;
    dateOfBirth?: string | null;
    mobileNumber?: string;
    gender?: string;
}