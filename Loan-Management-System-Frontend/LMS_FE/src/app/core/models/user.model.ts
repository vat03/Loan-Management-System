export interface UserRequestDTO {
    username: string;
    email: string;
    password: string;
}

export interface UserResponseDTO {
    id: number;
    username: string;
    email: string;
    roleName: string;
    isDeleted: boolean;
}