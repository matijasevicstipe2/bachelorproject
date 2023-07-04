export interface UserDto {
  profilePicture: ArrayBuffer | string | null;
  firstName: string;
  lastName: string;
  qrCode: ArrayBuffer | string | null;
  qrCodebase64: string;
  daysLeft: number;
  membership: string;
  active: boolean;
}
