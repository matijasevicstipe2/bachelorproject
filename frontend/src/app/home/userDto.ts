export interface UserDto {
  profilePicture: ArrayBuffer | string | null;
  firstName: string;
  lastName: string;
  qrCode: ArrayBuffer | string | null;
  daysLeft: number;
  membership: string;
}
